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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Callback class for handling responses.
 */
public class ResponseCallback {

    private static final Logger logger = LoggerFactory.getLogger(ResponseCallback.class);

    public static final long EXPIRATION_TIME_MS = 30;

    private final CompletableFuture<String> future;

    private final String requestId;

    private final long timestamp;

    /**
     * Constructs a new ResponseCallback with the specified request ID.
     *
     * @param requestId The ID of the request.
     */
    public ResponseCallback(String requestId) {
        this.requestId = requestId;
        this.future = new CompletableFuture<>();
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Marks the response as completed with the provided response string.
     *
     * @param response The response string.
     */
    public void done(String response) {
        this.future.complete(response);
    }

    /**
     * Marks the response as completed exceptionally with the provided exception.
     *
     * @param exception The exception that occurred.
     */
    public void caught(Throwable exception) {
        this.future.completeExceptionally(exception);
    }

    /**
     * Retrieves the response from the future within a specified expiration time.
     * This method blocks until the response is available or the expiration time is reached.
     *
     * @return The response string.
     */
    public String getResponse() {
        try {
            return future.get(EXPIRATION_TIME_MS, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Error occurred while getting response", e);
        } finally {
            ResponseCallbackManager.removeCallback(this.requestId);
        }
        return null;
    }

    /**
     * Gets the request ID associated with this callback.
     *
     * @return The request ID.
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Gets the CompletableFuture associated with this callback.
     *
     * @return The CompletableFuture.
     */
    public CompletableFuture<String> getFuture() {
        return future;
    }

    public long getTimestamp() {
        return timestamp;
    }
}