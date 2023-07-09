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

package cn.thinkinginjava.mockit.admin.model.enums;

/**
 * Enumeration representing different types of options.
 */
public enum OnlineStatusEnum {

    /**
     * online
     */
    ONLINE(1, "在线"),

    /**
     * offline
     */
    OFFLINE(0, "离线"),

    ;

    private Integer value;
    private String text;

    OnlineStatusEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * Retrieves the OnlineStatusEnum associated with the specified option type.
     *
     * @param optionType The option type.
     * @return The OnlineStatusEnum associated with the option type, or null if not found.
     */
    public static String getByValue(Integer optionType) {
        for (OnlineStatusEnum value : OnlineStatusEnum.values()) {
            if (value.getValue().equals(optionType)) {
                return value.text;
            }
        }
        return null;
    }

    /**
     * Gets the option value.
     *
     * @return The value.
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Gets the option text.
     *
     * @return The text.
     */
    public String getText() {
        return text;
    }
}
