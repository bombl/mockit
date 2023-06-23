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

package cn.thinkinginjava.mockit.common.enums;

/**
 * Enumeration representing different types of options.
 */
public enum MessageTypeEnum {

    /**
     * mock
     */
    MOCK("1001"),

    /**
     * cancel mock
     */
    CANCEL_MOCK("1002"),

    /**
     * get methods
     */
    GET_METHODS("1003"),

    ;

    private String type;

    /**
     * Retrieves the OptionTypeEnum associated with the specified option type.
     *
     * @param optionType The option type.
     * @return The OptionTypeEnum associated with the option type, or null if not found.
     */
    public static MessageTypeEnum getByType(String optionType) {
        for (MessageTypeEnum value : MessageTypeEnum.values()) {
            if (value.getType().equals(optionType)) {
                return value;
            }
        }
        return null;
    }

    /**
     * Gets the option type.
     *
     * @return The option type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the option type.
     *
     * @param type The option type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Constructs a new OptionTypeEnum with the specified option type.
     *
     * @param type The option type.
     */
    MessageTypeEnum(String type) {
        this.type = type;
    }
}
