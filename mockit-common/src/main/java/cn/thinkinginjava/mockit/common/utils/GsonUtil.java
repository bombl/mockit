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

package cn.thinkinginjava.mockit.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * Utility class for Gson operations.
 */
public class GsonUtil {

    private static final Gson GSON = new GsonBuilder().create();

    /**
     * Converts an object to its JSON representation.
     *
     * @param object The object to convert.
     * @return The JSON representation of the object.
     */
    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    /**
     * Converts a JSON string to an object of the specified class.
     *
     * @param json   The JSON string to convert.
     * @param tClass The class of the object to convert to.
     * @param <T>    The type of the object to convert to.
     * @return The object converted from the JSON string.
     */
    public static <T> T fromJson(String json, final Class<T> tClass) {
        return GSON.fromJson(json, tClass);
    }

    /**
     * Converts a JSON string to a list of objects of the specified class.
     *
     * @param json  The JSON string to convert.
     * @param clazz The class of the objects in the list.
     * @param <T>   The type of the objects in the list.
     * @return The list of objects converted from the JSON string.
     */
    public static <T> List<T> fromList(final String json, final Class<T> clazz) {
        return GSON.fromJson(json, TypeToken.getParameterized(List.class, clazz).getType());
    }

    /**
     * Converts a JSON string to a map with string keys and objects of the specified class as values.
     *
     * @param json  The JSON string to convert.
     * @param clazz The class of the objects in the map.
     * @param <T>   The type of the objects in the map.
     * @return The map with string keys and objects converted from the JSON string.
     */
    public static <T> Map<String, T> toMap(final String json, final Class<T> clazz) {
        return GSON.fromJson(json, TypeToken.getParameterized(Map.class, String.class, clazz).getType());
    }

    /**
     * Converts a JSON string to a list of maps with string keys and object values.
     *
     * @param json The JSON string to convert.
     * @return The list of maps with string keys and object values converted from the JSON string.
     */
    public static List<Map<String, Object>> toListMap(final String json) {
        return GSON.fromJson(json, new TypeToken<List<Map<String, Object>>>() {
        }.getType());
    }

    /**
     * Retrieves the value of a specific field from a JSON string.
     *
     * @param json  The JSON string.
     * @param field The name of the field.
     * @return The value of the field as a string.
     */
    public static String getFieldValue(String json, String field) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        return jsonObject.get(field).getAsString();
    }
}
