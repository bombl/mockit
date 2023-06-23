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

package cn.thinkinginjava.mockit.client.handler.websocket;

import cn.thinkinginjava.mockit.client.communication.MockitClient;
import cn.thinkinginjava.mockit.client.handler.message.MessageHandler;
import cn.thinkinginjava.mockit.client.handler.message.MessageHandlerManager;
import cn.thinkinginjava.mockit.client.utils.AddressUtil;
import cn.thinkinginjava.mockit.common.constant.MockConstants;
import cn.thinkinginjava.mockit.common.enums.MessageTypeEnum;
import cn.thinkinginjava.mockit.common.utils.GsonUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

import static cn.thinkinginjava.mockit.common.constant.MockConstants.PING;
import static cn.thinkinginjava.mockit.common.constant.MockConstants.STOMP;

/**
 * Handler for WebSocket client connections.
 */
public class WebSocketClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketClientHandler.class);

    private final String ip;

    private final Integer port;

    private final MockitClient mockitClient;

    private final WebSocketClientHandshaker webSocketClientHandshaker;

    /**
     * Constructs a WebSocketClientHandler instance.
     *
     * @param mockitClient the MockitClient instance associated with this handler
     * @param ip           the IP address of the server to connect to
     * @param port         the port of the server to connect to
     * @throws Exception if an error occurs during the construction
     */
    public WebSocketClientHandler(MockitClient mockitClient, String ip, Integer port) throws Exception {
        this.mockitClient = mockitClient;
        this.ip = ip;
        this.port = port;
        URI uri = AddressUtil.getUri(ip, port, mockitClient.getAlias());
        this.webSocketClientHandshaker = WebSocketClientHandshakerFactory.newHandshaker(
                uri,
                WebSocketVersion.V13,
                STOMP,
                Boolean.FALSE,
                new DefaultHttpHeaders(), Integer.MAX_VALUE
        );
    }

    /**
     * This method is called when a channel becomes active and is ready to send/receive data.
     *
     * @param ctx The channel handler context.
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        webSocketClientHandshaker.handshake(ctx.channel());
    }

    /**
     * Invoked when the channel becomes inactive (i.e., disconnected from the server).
     * This method is called when the channel's connection is closed or lost.
     *
     * @param ctx the channel handler context
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        mockitClient.start(ip, port);
    }

    /**
     * This method is called when a new message is received from the channel.
     *
     * @param ctx The channel handler context.
     * @param msg The received message object.
     * @throws Exception If any exception occurs during message handling.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpResponse) {
            handleHttpRequest(ctx, msg);
        } else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    /**
     * This method is called when a user event is triggered on the channel.
     *
     * @param ctx The channel handler context.
     * @param evt The triggered user event object.
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.WRITER_IDLE)) {
                WebSocketFrame webSocketFrame = new TextWebSocketFrame(PING);
                ctx.writeAndFlush(new PingWebSocketFrame(webSocketFrame.content().retain()));
            }
        }
    }

    /**
     * This method is called when an exception is caught during channel processing.
     *
     * @param ctx   The channel handler context.
     * @param cause The exception that was caught.
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("mockit webSocket error", cause);
        ctx.close();
    }

    /**
     * Handles the HTTP request received from the server.
     *
     * @param ctx The channel handler context.
     * @param msg The HTTP request message.
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, Object msg) {
        FullHttpResponse response = (FullHttpResponse) msg;
        if (!webSocketClientHandshaker.isHandshakeComplete()) {
            Channel channel = ctx.channel();
            webSocketClientHandshaker.finishHandshake(channel, response);
        }
    }

    /**
     * Handles the WebSocket frame received from the server.
     *
     * @param ctx   The channel handler context.
     * @param frame The WebSocket frame.
     * @throws Exception If an error occurs while handling the frame.
     */
    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        Channel channel = ctx.channel();
        if (frame instanceof CloseWebSocketFrame) {
            webSocketClientHandshaker.close(channel, (CloseWebSocketFrame) frame.retain());
            return;
        }
        if (frame instanceof PingWebSocketFrame) {
            channel.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        if (!(frame instanceof TextWebSocketFrame)) {
            return;
        }
        String message = ((TextWebSocketFrame) frame).text();
        String optionType = GsonUtil.getFieldValue(message, MockConstants.MESSAGE_TYPE);
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.getByType(optionType);
        if (messageTypeEnum == null) {
            logger.error("mockit unsupported operation type: {}", optionType);
            return;
        }
        MessageHandler messageHandler = MessageHandlerManager.getHandler(messageTypeEnum);
        if (messageHandler != null) {
            messageHandler.handle(ctx, message);
        }
    }
}
