/**
 *
 * Mockit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Mockit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Mockit. If not, see <http://www.gnu.org/licenses/>.
 */

package cn.thinkinginjava.mockit.client.communication;

import cn.thinkinginjava.mockit.client.handler.WebSocketClientHandler;
import cn.thinkinginjava.mockit.client.utils.AddressUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * MockitClient is a class that implements the SmartInitializingSingleton interface,
 * used to start and manage the Mockit client.
 * It initializes and starts the client based on the specified addresses and alias,
 * and automatically executes the start operation after all singleton instances are instantiated in the Spring container.
 */
public class MockitClient implements SmartInitializingSingleton {

    private static final Logger logger = LoggerFactory.getLogger(MockitClient.class);

    private String addresses;

    private String alias;

    /**
     * Set the list of addresses that the Mockit client will connect to.
     * Multiple addresses should be separated by commas.
     * @param addresses the list of addresses
     */
    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    /**
     * Set the alias of the Mockit client.
     * @param alias the alias
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Execute the start operation after all singleton instances are instantiated.
     */
    @Override
    public void afterSingletonsInstantiated() {
        this.start();
    }

    /**
     * Start the Mockit client.
     */
    public void start() {
        if (StringUtils.isEmpty(addresses) || StringUtils.isEmpty(alias)) {
            logger.error("mockit addresses or appname is empty,please make sure to set it up correctly.");
            return;
        }
        List<String> addressList = AddressUtil.resolveAddress(addresses);
        for (String address : addressList) {
            if (!AddressUtil.isValidIpAndPort(address)) {
                logger.error("mockit invalid address:{},please make sure to set it up correctly.", address);
                continue;
            }
            try {
                String[] addressArray = address.split(":");
                initClient(addressArray[0], Integer.parseInt(addressArray[1]));
            } catch (Exception e) {
                logger.error("mockit client start error address:{}", address);
            }
        }
    }

    /**
     * Initialize the Mockit client and connect to the specified IP address and port.
     * @param ip the IP address
     * @param port the port number
     * @throws Exception initialization exception
     */
    private void initClient(String ip, Integer port) throws Exception {
        URI uri = AddressUtil.getUri(ip, port, alias);
        Thread thread = new Thread(() -> {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.AUTO_READ, true)
                    .group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG));
            bootstrap.handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) {
                    ch.pipeline().addLast(new IdleStateHandler(0, 10, 0, TimeUnit.SECONDS));
                    ch.pipeline().addLast(new HttpClientCodec());
                    ch.pipeline().addLast(new HttpObjectAggregator(5 * 1024 * 1024));
                    ch.pipeline().addLast(new WebSocketClientHandler(uri));
                }
            });

            ChannelFuture cf = bootstrap.connect(ip, port);
            cf.addListener((ChannelFutureListener) future -> {
                if (!future.isSuccess()) {
                    future.channel().eventLoop().schedule(() -> {
                        try {
                            initClient(ip, port);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, 50000, TimeUnit.MILLISECONDS);
                }
            });
        });
        thread.setDaemon(true);
        thread.start();
    }
}
