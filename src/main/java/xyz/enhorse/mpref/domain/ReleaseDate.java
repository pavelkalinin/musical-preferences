package xyz.enhorse.mpref.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.enhorse.mpref.json.JsonCompatible;
import xyz.enhorse.mpref.json.JsonString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Entity class represents Release Date
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         18.12.2015
 */
public class ReleaseDate implements JsonCompatible {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReleaseDate.class);

    private static final String DEFAULT_DAY_MONTH = "31.12.";
    private static final String[] PATTERNS = {
            "MMM d, yyyy",  //Dec 25, 1925
            "d MMMM yyyy",  //10 June 2013
            "dd.MM.yyyy",   //31.12.2015
            "MMMM d, yyyy"  //June 8, 2015
    };

    @NotNull
    private LocalDate value;


    public ReleaseDate(LocalDate date) throws IllegalArgumentException {
        setValue(date);
    }


    public ReleaseDate(String date) throws IllegalArgumentException {
        setValue(parseForDate(date));
    }


    public ReleaseDate(JsonString json) throws IllegalArgumentException {
        setFromJsonString(json);
    }


    private void setFromJsonString(JsonString json) throws IllegalArgumentException {
        if (json == null) throw new IllegalArgumentException("JsonString of " + getClass().getSimpleName()
                + " cannot be null!");

        ReleaseDate value = json.convertTo(getClass());
        if (value == null) throw new IllegalArgumentException("JsonString \"" + json.value()
                + "\" cannot be applicable to " + getClass().getSimpleName());

        setValue(value.getValue());
    }


    private void setValue(LocalDate date) throws IllegalArgumentException {
        if (date.getYear() < 1900) {
            throw new IllegalArgumentException("Release date cannot be early than 1990!");
        }

        value = date;
    }


    private LocalDate parseForDate(String string) throws IllegalArgumentException {
        if (string == null) {
            throw new IllegalArgumentException("Release date cannot be null!");
        }

        string = extendToFullDate(string);

        LocalDate result = null;
        for (String pattern : PATTERNS) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
            try {
                result = LocalDate.parse(string, dateTimeFormatter);
                if (result != null) {
                    break;
                }
            } catch (Exception e) {
                LOGGER.debug("\"" + string + "\" is not a valid date for the pattern : \"" + pattern + "\"");
                result = null;
            }
        }

        if (result == null) {
            throw new IllegalArgumentException(
                    "Could not find an applicable date pattern for the string : \"" + string + "\"");
        }

        return result;
    }


    private String extendToFullDate(String string) {
        return (string.length() == 4)
                ? DEFAULT_DAY_MONTH + string
                : string;
    }


    public int year() {
        return value.getYear();
    }


    public int month() {
        return value.getMonthValue();
    }


    public int day() {
        return value.getDayOfMonth();
    }


    public LocalDate getValue() {
        return value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReleaseDate that = (ReleaseDate) o;

        return ((year() == that.year())
                && (month() == that.month())
                && (day() == that.day()));
    }


    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + day();
        hash = hash * 31 + month();
        hash = hash * 31 + year();
        return hash;
    }


    @Override
    public String toString() {
        return getValue().toString();
    }


    @Override
    public JsonString toJsonString() {
        return JsonString.generateFor(this);
    }


    private ReleaseDate() {} //for Jackson JSON engine
}
