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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * MockitContext class represents the context for a mock operation.
 */
public class MockitContext {

    private static final Logger logger = LoggerFactory.getLogger(MockitContext.class);

    private static final ThreadLocal<MockitContext> LOCAL = ThreadLocal.withInitial(MockitContext::new);

    protected final Map<String, Object> attachments = new HashMap<>();

    /**
     * Creates a new MockitContext and sets it in the MockitContextManager.
     */
    public MockitContext() {

    }

    /**
     * Retrieves the current MockitContext associated with the current thread.
     *
     * @return The MockitContext.
     */
    public static MockitContext getContext() {
        return LOCAL.get();
    }

    /**
     * Sets an attachment in this MockitContext.
     *
     * @param key   The key of the attachment.
     * @param value The value of the attachment.
     * @return The MockitContext.
     */
    public MockitContext setAttachment(String key, Object value) {
        if (value == null) {
            attachments.remove(key);
        } else {
            attachments.put(key, value);
        }
        return this;
    }

    /**
     * Retrieves the attachments associated with this MockitContext.
     *
     * @return The attachments map.
     */
    public Map<String, Object> getAttachments() {
        return this.attachments;
    }
}
