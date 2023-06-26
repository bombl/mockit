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

package cn.thinkinginjava.mockit.admin.session;

import cn.thinkinginjava.mockit.admin.model.dto.Session;
import io.netty.channel.Channel;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Holds and manages WebSocket sessions.
 */
public class SessionHolder {

    /**
     * A thread-safe map that stores the mapping of aliases to lists of sessions.
     */
    private static final Map<String, List<Session>> sessionMap = new ConcurrentHashMap<>();

    /**
     * Adds a WebSocket session to the session holder.
     *
     * @param session The WebSocket session.
     */
    public synchronized static void addSession(Session session) {
        List<Session> sessionList = sessionMap.get(session.getAlias());
        if (CollectionUtils.isEmpty(sessionList)) {
            List<Session> newList = new ArrayList<>();
            newList.add(session);
            sessionMap.put(session.getAlias(), newList);
            return;
        }
        boolean isExist = sessionList.stream().map(Session::getIp)
                .anyMatch(value -> value.equals(session.getIp()));
        if (!isExist) {
            sessionList.add(session);
            sessionMap.put(session.getAlias(), sessionList);
        }
    }

    /**
     * Removes a WebSocket session from the session holder.
     *
     * @param channel The Channel object representing the WebSocket connection to be removed.
     */
    public synchronized static Session removeSession(Channel channel) {
        Session session = null;
        if (!CollectionUtils.isEmpty(sessionMap)) {
            for (String alias : sessionMap.keySet()) {
                List<Session> sessions = sessionMap.get(alias);
                if (sessions == null || CollectionUtils.isEmpty(sessions)) {
                    continue;
                }
                Iterator<Session> iterator = sessions.iterator();
                while (iterator.hasNext()) {
                    session = iterator.next();
                    if (session.getChannel().equals(channel)) {
                        iterator.remove();
                    }
                }
                sessionMap.put(alias, sessions);
            }
        }
        return session;
    }

    /**
     * Retrieves the session map from the session holder.
     *
     * @return The session map containing the mapping of aliases to lists of sessions.
     */
    public synchronized static Map<String, List<Session>> getSessionMap() {
        return sessionMap;
    }
}

