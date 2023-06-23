package cn.thinkinginjava.mockit.common.utils;

import cn.thinkinginjava.mockit.common.dto.Message;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.reflect.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class GsonUtil {

    private static final Gson GSON = new GsonBuilder().create();

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T fromJson(String json, final Class<T> tClass) {
        return GSON.fromJson(json, tClass);
    }

    public static <T> T fromJson(String json, TypeToken<T> typeToken) {
        return GSON.fromJson(json, typeToken.getType());
    }

    public static <T> Message<T> fromJsonToMessage(String json, Class<T> clazz) {
        Type messageType = TypeToken.getParameterized(Message.class, clazz).getType();
        return GSON.fromJson(json, messageType);
    }

    public static <T> Message<List<T>> fromJsonToMessageList(String json, Class<T> clazz) {
        Type listType = TypeToken.getParameterized(List.class, clazz).getType();
        Type messageTypeWithList = TypeToken.getParameterized(Message.class, listType).getType();
        return GSON.fromJson(json, messageTypeWithList);
    }

    public static <T> List<T> fromList(final String json, final Class<T> clazz) {
        return GSON.fromJson(json, TypeToken.getParameterized(List.class, clazz).getType());
    }

    public static <T> Map<String, T> toMap(final String json, final Class<T> clazz) {
        return GSON.fromJson(json, TypeToken.getParameterized(Map.class, String.class, clazz).getType());
    }

    public static List<Map<String, Object>> toListMap(final String json) {
        return GSON.fromJson(json, new TypeToken<List<Map<String, Object>>>() {
        }.getType());
    }

    public static String getFieldValue(String json, String field) {
        JsonElement jsonElement = JsonParser.parseString(json);
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.get(field) == null) {
                return null;
            }
            return jsonObject.get(field).getAsString();
        }
        return null;
    }

}
