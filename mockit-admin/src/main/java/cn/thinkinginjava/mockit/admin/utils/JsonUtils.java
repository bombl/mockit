package cn.thinkinginjava.mockit.admin.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JsonUtils {
    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private JsonUtils() {
    }

    public static TypeFactory getTypeFactory() {
        return mapper.getTypeFactory();
    }

    public static String toJson(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception var2) {
            log.error("Generate JSON String Error: " + value, var2);
            return null;
        }
    }

    public static <T> T toObject(String json, Class<T> valueType) {
        try {
            return mapper.readValue(json, valueType);
        } catch (Exception var3) {
            log.error("Parse JSON String Error: " + json, var3);
            return null;
        }
    }

    public static <T> T toObjectWithException(String json, Class<T> valueType) throws IOException {
        return mapper.readValue(json, valueType);
    }

    public static Object toObject(String json, TypeReference<?> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (Exception var3) {
            log.error("Parse JSON String Error: " + json, var3);
            return null;
        }
    }

    public static Object toObject(String json, JavaType javaType) {
        try {
            return mapper.readValue(json, javaType);
        } catch (Exception var3) {
            log.error("Parse JSON String Error: " + json, var3);
            return null;
        }
    }

    public static void writeValue(Writer writer, Object value) {
        try {
            mapper.writeValue(writer, value);
        } catch (IOException var3) {
            log.error("Generate JSON String Error: " + value, var3);
        }

    }

    public static <T> T toObject(String json, Type type) {
        try {
            JavaType javaType = mapper.getTypeFactory().constructType(type);
            return mapper.readValue(json, javaType);
        } catch (Exception var3) {
            log.error("Parse  JSON String Error: " + json, var3);
            return null;
        }
    }

    public static Map<String, String> parse(String json) {
        try {
            JavaType javaType = mapper.getTypeFactory().constructType(Map.class);
            return (Map) mapper.readValue(json, javaType);
        } catch (Exception var2) {
            log.error("Parse  JSON String Error: " + json, var2);
            return null;
        }
    }

    public static Map<String, String> toMap(Object object) {
        return parse(toJson(object));
    }

    static {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
