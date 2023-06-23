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

package cn.thinkinginjava.mockit.client.handler.message;

import io.netty.channel.ChannelHandlerContext;

/**
 * Interface for message handlers.
 */
public interface MessageHandler {
    /**
     * Handles a message received by the channel.
     *
     * @param ctx     The channel handler context.
     * @param message The received message.
     * @throws Exception if an error occurs during message handling.
     */
    void handle(ChannelHandlerContext ctx, String message) throws Exception;
}
