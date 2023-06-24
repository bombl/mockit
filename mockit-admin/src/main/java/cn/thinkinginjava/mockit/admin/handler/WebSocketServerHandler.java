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

package cn.thinkinginjava.mockit.admin.handler;

import cn.thinkinginjava.mockit.admin.context.ResponseCallbackManager;
import cn.thinkinginjava.mockit.admin.session.SessionHolder;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import static cn.thinkinginjava.mockit.common.constant.MockConstants.PONG;

/**
 * Handles WebSocket frames received from clients.
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    /**
     * Handles the read event when a WebSocket frame is received from the client.
     *
     * @param ctx The channel handler context.
     * @param msg The WebSocket frame received from the client.
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) {
        if (msg instanceof PingWebSocketFrame) {
            WebSocketFrame webSocketFrame = new TextWebSocketFrame(PONG);
            ctx.writeAndFlush(new PongWebSocketFrame(webSocketFrame.content().retain()));
            return;
        }
        if (msg instanceof CloseWebSocketFrame) {
            ctx.writeAndFlush(msg.retainedDuplicate()).addListener(ChannelFutureListener.CLOSE);
        }
        if (msg instanceof TextWebSocketFrame) {
            String response = ((TextWebSocketFrame) msg).text();
            ResponseCallbackManager.notifyCallback(response);
        }
    }

    /**
     * Handles user events triggered in the channel.
     *
     * @param ctx The channel handler context.
     * @param evt The user event triggered.
     * @throws Exception If an error occurs while handling the user event.
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                ctx.channel().close();
            }
            return;
        }
        super.userEventTriggered(ctx, evt);
    }

    /**
     * Handles the channel becoming inactive.
     *
     * @param ctx The channel handler context.
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        SessionHolder.removeSession(ctx.channel());
    }

}
