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

import java.io.Serializable;

/**
 * Generic result class representing the outcome of an operation.
 *
 * @param <T> The type of the data contained in the result.
 */
public class MockitResult<T> implements Serializable {

    private static final long serialVersionUID = -5777465139846605810L;

    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 500;

    private int code;
    private String message;
    private T data;

    /**
     * Default constructor.
     */
    public MockitResult() {
    }

    /**
     * Constructor with code and message.
     *
     * @param code    The result code.
     * @param message The result message.
     */
    public MockitResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Constructor with data.
     *
     * @param data The result data.
     */
    public MockitResult(T data) {
        this.code = SUCCESS_CODE;
        this.data = data;
    }

    /**
     * Create a successful result with data.
     *
     * @param data The result data.
     * @param <T>  The type of the data.
     * @return The successful result.
     */
    public static <T> MockitResult<T> successful(T data) {
        return new MockitResult<T>().setCode(SUCCESS_CODE).setData(data);
    }

    /**
     * Create a successful result without data.
     *
     * @param <T> The type of the data.
     * @return The successful result.
     */
    public static <T> MockitResult<T> successful() {
        return new MockitResult<T>().setCode(SUCCESS_CODE);
    }

    /**
     * Create a failed result with message.
     *
     * @param message The result message.
     * @param <T>     The type of the data.
     * @return The failed result.
     */
    public static <T> MockitResult<T> fail(String message) {
        return new MockitResult<T>().setCode(FAIL_CODE).setMessage(message);
    }

    /**
     * Get the result code.
     *
     * @return The result code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Set the result code.
     *
     * @param code The result code to set.
     * @return This result object.
     */
    public MockitResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    /**
     * Get the result message.
     *
     * @return The result message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the result message.
     *
     * @param message The result message to set.
     * @return This result object.
     */
    public MockitResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Get the result data.
     *
     * @return The result data.
     */
    public T getData() {
        return data;
    }

    /**
     * Set the result data.
     *
     * @param data The result data to set.
     * @return This result object.
     */
    public MockitResult<T> setData(T data) {
        this.data = data;
        return this;
    }
}
