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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static cn.thinkinginjava.mockit.common.constant.MockConstants.REQUEST_ID;

/**
 * Manager class for MockitContext objects.
 */
public class MockitContextManager {

    private static final long EXPIRATION_TIME_MS = 100;
    private static final Map<String, MockitContext> mockitContextMap = new HashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    /**
     * Sets the MockitContext associated with the specified request ID.
     * The MockitContext will be automatically removed after a specified expiration time.
     *
     * @param requestId     The request ID.
     * @param mockitContext The MockitContext to set.
     */
    public static void setMockitContext(String requestId, MockitContext mockitContext) {
        mockitContextMap.put(requestId, mockitContext);
        scheduler.schedule(() -> removeMockitContext(requestId), EXPIRATION_TIME_MS, TimeUnit.MILLISECONDS);
    }

    /**
     * Retrieves the MockitContext associated with the specified request ID.
     *
     * @param requestId The request ID.
     * @return The MockitContext, or null if not found.
     */
    public static MockitContext getMockitContext(String requestId) {
        return mockitContextMap.get(requestId);
    }

    /**
     * Removes the MockitContext associated with the specified request ID.
     *
     * @param requestId The request ID.
     */
    public static void removeMockitContext(String requestId) {
        mockitContextMap.remove(requestId);
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
        MockitContext mockitContext = getMockitContext(requestId);
        if (mockitContext == null) {
            return;
        }
        ResponseCallback responseCallback = mockitContext.getResponseCallback();
        if (responseCallback == null) {
            return;
        }
        responseCallback.done(response);
    }
}
