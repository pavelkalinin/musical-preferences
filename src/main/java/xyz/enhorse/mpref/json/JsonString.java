package xyz.enhorse.mpref.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Class for manipulating with JSON
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         30.12.2015
 */
public class JsonString {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonString.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.registerModule(new Jdk8Module());
    }

    private String value;


    public JsonString(String json) throws IllegalArgumentException {
        setValue(json);
    }

    public String value() {
        return value;
    }


    private void setValue(String json) throws IllegalArgumentException {
        if (json == null) throw new IllegalArgumentException("String of JSON cannot be null!");
        if (json.trim().length() == 0) throw new IllegalArgumentException("String of JSON cannot be empty!");

        value = json;
    }


    public <T> T convertTo(Class<T> type) throws IllegalArgumentException {
        if (type == null) throw new IllegalArgumentException("Parameter type cannot be null!");

        T result;
        try {
            result = MAPPER.readValue(value(), type);
        } catch (Exception ex) {
            LOGGER.error("Cannot convert \"" + value() + "\"; " + ex.getMessage());
            result = null;
        }

        return result;
    }


    @Override
    public String toString() {
        return value();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JsonString that = (JsonString) o;

        return value.equals(that.value);

    }


    @Override
    public int hashCode() {
        return value.hashCode();
    }


    public static JsonString generateFor(Object object) throws IllegalArgumentException {
        if (object == null) throw new IllegalArgumentException("Object for generating JsonString cannot be null!");

        try {
            return new JsonString(MAPPER.writeValueAsString(object));
        } catch (IOException e) {
            LOGGER.error("Error converting \"" + object.getClass().getSimpleName() + "\" to JSON");
        }
        return null;
    }
}