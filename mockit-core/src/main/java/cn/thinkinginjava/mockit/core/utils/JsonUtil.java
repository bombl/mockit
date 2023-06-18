/**
 *
 * Mockit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Mockit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Mockit. If not, see <http://www.gnu.org/licenses/>.
 */

package cn.thinkinginjava.mockit.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A utility class for working with JSON data.
 * This class provides methods to determine whether a given string is valid JSON.
 * It is useful for performing JSON-related operations such as parsing, validation, and manipulation.
 * The methods in this class can be used to check if a string is a valid JSON format or not.
 */
public class JsonUtil {

    /**
     * Checks whether the given string is a valid JSON.
     * This method validates if the provided string conforms to the JSON syntax standards.
     * It examines the structure and formatting of the string to determine if it represents a valid JSON object or array.
     *
     * @param jsonString The string to be checked for JSON validity.
     * @return True if the string is a valid JSON, false otherwise.
     */
    public static boolean isJson(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readTree(jsonString);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }
}
