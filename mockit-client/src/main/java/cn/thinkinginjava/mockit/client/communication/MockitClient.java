/**
 * Mockit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Mockit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with Mockit. If not, see <http://www.gnu.org/licenses/>.
 */

package cn.thinkinginjava.mockit.client.communication;

import cn.thinkinginjava.mockit.client.handler.websocket.WebSocketClientHandler;
import cn.thinkinginjava.mockit.client.utils.AddressUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
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

    private static final int MAX_RETRY = Integer.MAX_VALUE;

    /**
     * Set the list of addresses that the Mockit client will connect to.
     * Multiple addresses should be separated by commas.
     *
     * @param addresses the list of addresses
     */
    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    /**
     * Set the alias of the Mockit client.
     *
     * @param alias the alias
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * get the alias of the Mockit client.
     */
    public String getAlias() {
        return alias;
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
                start(addressArray[0], Integer.parseInt(addressArray[1]));
            } catch (Exception e) {
                logger.error("mockit client start error address:{}", address);
            }
        }
    }

    /**
     * Initialize the Mockit client and connect to the specified IP address and port.
     *
     * @param ip   the IP address
     * @param port the port number
     */
    public void start(String ip, Integer port) {
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
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(new IdleStateHandler(0, 10, 0, TimeUnit.SECONDS));
                    ch.pipeline().addLast(new HttpClientCodec());
                    ch.pipeline().addLast(new HttpObjectAggregator(5 * 1024 * 1024));
                    ch.pipeline().addLast(new WebSocketClientHandler(MockitClient.this, ip, port));
                }
            });

            connect(bootstrap, ip, port, MAX_RETRY);
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Connects to a server using the provided Bootstrap.
     *
     * @param bootstrap the Bootstrap instance to use for the connection
     * @param host      the host of the server to connect to
     * @param port      the port of the server to connect to
     * @param retry     the number of connection retry attempts remaining
     */
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                logger.info("mockit client connected to server!");
            } else {
                logger.error("mockit clinet failed to connect to server: " + future.cause());
                if (retry == 0) {
                    logger.info("mockit connection attempts exceeded. Giving up.");
                    return;
                }
                int delay = 1 << (MAX_RETRY - retry);
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }
}
