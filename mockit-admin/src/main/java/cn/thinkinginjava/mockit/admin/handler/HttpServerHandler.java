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

import cn.thinkinginjava.mockit.admin.session.SessionHolder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static cn.thinkinginjava.mockit.common.constant.MockConstants.*;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Represents the HTTP server handler that extends {@link SimpleChannelInboundHandler} for handling full HTTP requests.
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    /**
     * Handles the received full HTTP request.
     *
     * @param ctx     the channel handler context
     * @param request the received full HTTP request
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!request.uri().startsWith(REQ_PATH)) {
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST);
            sendHttpResponse(ctx, request, response);
            return;
        }
        Channel channel = ctx.channel();
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(request), "stomp", true, 5 * 1024 * 1024);
        WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(request);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channel);
            return;
        }
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.remove(ctx.name());
        pipeline.addLast(new WebSocketFrameAggregator(Integer.MAX_VALUE));
        pipeline.addLast(new WebSocketServerHandler());
        handshaker.handshake(channel, request).addListener(future -> {
            if (!future.isSuccess()) {
                handshaker.close(channel, new CloseWebSocketFrame());
                return;
            }
            Map<String, String> paraMap = getParaMap(ctx, request);
            SessionHolder.addSession(Objects.requireNonNull(paraMap).get(ALIAS), channel, paraMap.get(IP));
        });
    }

    /**
     * Sends an HTTP response.
     *
     * @param ctx the channel handler context
     * @param req the received HTTP request
     * @param res the HTTP response to send
     */
    private static void sendHttpResponse(
            ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        int statusCode = res.status().code();
        if (statusCode != OK.code() && res.content().readableBytes() == 0) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        HttpUtil.setContentLength(res, res.content().readableBytes());

        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpUtil.isKeepAlive(req) || statusCode != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * Retrieves the WebSocket location from the specified HTTP request.
     *
     * @param req the full HTTP request
     * @return the WebSocket location
     */
    private static String getWebSocketLocation(FullHttpRequest req) {
        String location = req.headers().get(HttpHeaderNames.HOST) + req.uri();
        return PROTOCOL_PREFIX + location;
    }

    /**
     * Retrieves the parameter map from the specified HTTP request.
     *
     * @param ctx the channel handler context
     * @param req the full HTTP request
     * @return the parameter map
     */
    private Map<String, String> getParaMap(ChannelHandlerContext ctx, FullHttpRequest req) {
        FullHttpResponse res;
        String uri = req.uri();
        String[] uriArray = uri.split("\\?");
        if (uriArray.length < 2) {
            res = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.FORBIDDEN);
            sendHttpResponse(ctx, req, res);
            return null;
        }
        String[] paramsArray = uriArray[1].split("&");
        Map<String, String> paraMap = new HashMap<>();
        for (String params : paramsArray) {
            String[] paramsArr = params.split("=");
            paraMap.put(paramsArr[0], paramsArr[1]);
        }
        return paraMap;
    }
}
