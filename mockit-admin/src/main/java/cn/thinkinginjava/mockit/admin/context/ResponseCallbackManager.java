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

package cn.thinkinginjava.mockit.admin.context;

import cn.thinkinginjava.mockit.common.utils.GsonUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static cn.thinkinginjava.mockit.common.constant.MockConstants.REQUEST_ID;

/**
 * Manager class for ResponseCallback objects.
 */
public class ResponseCallbackManager {

    private static final Map<String, ResponseCallback> callbackMap = new ConcurrentHashMap<>();

    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public ResponseCallbackManager() {
        scheduler.scheduleWithFixedDelay(this::cleanupExpiredKeys, ResponseCallback.EXPIRATION_TIME_MS, ResponseCallback.EXPIRATION_TIME_MS, TimeUnit.SECONDS);
    }

    /**
     * Sets the MockitContext associated with the specified request ID.
     * The MockitContext will be automatically removed after a specified expiration time.
     */
    public static ResponseCallback registerCallback(String requestId) {
        ResponseCallback responseCallback = new ResponseCallback(requestId);
        callbackMap.put(requestId, responseCallback);
        return responseCallback;
    }

    /**
     * Retrieves the ResponseCallback associated with the specified request ID.
     *
     * @param requestId The request ID.
     * @return The ResponseCallback, or null if not found.
     */
    public static ResponseCallback getCallback(String requestId) {
        return callbackMap.get(requestId);
    }

    /**
     * Removes the ResponseCallback associated with the specified request ID.
     *
     * @param requestId The request ID.
     */
    public static void removeCallback(String requestId) {
        callbackMap.remove(requestId);
    }

    /**
     * Notifies the corresponding ResponseCallback with the response string.
     *
     * @param response The response string.
     */
    public static void notifyCallback(String response) {
        String requestId = GsonUtil.getFieldValue(response, REQUEST_ID);
        if (StringUtils.isEmpty(requestId)) {
            return;
        }
        ResponseCallback responseCallback = getCallback(requestId);
        if (responseCallback == null) {
            return;
        }
        responseCallback.done(response);
        callbackMap.remove(requestId);
    }

    /**
     * Cleans up expired keys by removing them from the callbackMap.
     * Keys are considered expired if the time elapsed since their creation exceeds the expiration time.
     */
    private void cleanupExpiredKeys() {
        long currentTimestamp = System.currentTimeMillis();
        callbackMap.entrySet().removeIf(entry -> {
            ResponseCallback callback = entry.getValue();
            long keyTimestamp = callback.getTimestamp();
            return (currentTimestamp - keyTimestamp) > (ResponseCallback.EXPIRATION_TIME_MS * 1000);
        });
    }
}
