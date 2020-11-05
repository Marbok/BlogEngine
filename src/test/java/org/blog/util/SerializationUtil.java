package org.blog.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;

public class SerializationUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    }

    @SneakyThrows
    public static <T> String serialize(T data) {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(data);
    }

    @SneakyThrows
    public static <T> T deserialize(String data, Class<T> clazz) {
        return mapper.readValue(data, clazz);
    }
}
