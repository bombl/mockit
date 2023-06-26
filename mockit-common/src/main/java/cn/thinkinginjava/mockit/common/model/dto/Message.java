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

package cn.thinkinginjava.mockit.common.model.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * Base entity class representing common attributes for entities.
 */

/**
 * Generic message class.
 *
 * @param <T> The type of data contained in the message.
 */
public class Message<T> implements Serializable {
    private static final long serialVersionUID = -3775997204167496344L;

    private String requestId;
    private String messageType;
    private Map<String, Object> attachments;
    private T data;

    /**
     * Get the message type.
     *
     * @return The message type.
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * Set the message type.
     *
     * @param messageType The message type to set.
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * Get the request ID.
     *
     * @return The request ID.
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Set the request ID.
     *
     * @param requestId The request ID to set.
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * Get the attachments.
     *
     * @return The attachments.
     */
    public Map<String, Object> getAttachments() {
        return attachments;
    }

    /**
     * Set the attachments.
     *
     * @param attachments The attachments to set.
     */
    public void setAttachments(Map<String, Object> attachments) {
        this.attachments = attachments;
    }

    /**
     * Get the data.
     *
     * @return The data.
     */
    public T getData() {
        return data;
    }

    /**
     * Set the data.
     *
     * @param data The data to set.
     */
    public void setData(T data) {
        this.data = data;
    }
}

