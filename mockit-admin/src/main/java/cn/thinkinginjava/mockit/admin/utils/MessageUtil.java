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

package cn.thinkinginjava.mockit.admin.utils;

import cn.thinkinginjava.mockit.admin.context.MockitContext;
import cn.thinkinginjava.mockit.common.dto.Message;
import cn.thinkinginjava.mockit.common.utils.GsonUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * Utility class for sending messages over channels.
 */
public class MessageUtil {

    /**
     * Sends a text message over the specified channel.
     *
     * @param channel The channel to send the message on.
     * @param message The text message to send.
     */
    public static void sendMessage(Channel channel, String message) {
        if (channel.isActive()) {
            channel.writeAndFlush(new TextWebSocketFrame(message));
        }
    }

    /**
     * Sends a message object over the specified channel.
     *
     * @param channel The channel to send the message on.
     * @param object  The message object to send.
     */
    public static <T> void sendMessage(Channel channel, T object) {
        if (channel.isActive()) {
            if (object instanceof Message) {
                MockitContext context = MockitContext.getContext();
                Message<?> message = (Message<?>) object;
                if (context != null) {
                    message.setRequestId(context.getRequestId());
                    message.setAttachments(context.getAttachments());
                }
            }
            String messageJson = GsonUtil.toJson(object);
            channel.writeAndFlush(new TextWebSocketFrame(messageJson));
        }
    }
}