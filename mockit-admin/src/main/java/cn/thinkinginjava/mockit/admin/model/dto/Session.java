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

package cn.thinkinginjava.mockit.admin.model.dto;

import io.netty.channel.Channel;

/**
 * Represents a session for a WebSocket connection.
 */
public class Session {

    private final Channel channel;

    private final String alias;

    private final String ip;


    public Session(Channel channel, String alias, String ip) {
        this.channel = channel;
        this.alias = alias;
        this.ip = ip;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getAlias() {
        return alias;
    }

    public String getIp() {
        return ip;
    }
}
