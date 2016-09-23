package xyz.enhorse.mpref.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import xyz.enhorse.mpref.json.JsonString;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         18.12.2015
 */
@SuppressWarnings({"unused", "null"})
public class ReleaseDateTest {

    @Test
    public void testCreateValidPatternShortMonthDayYear() throws Exception {
        ReleaseDate expected = new ReleaseDate("25.12.2015");
        ReleaseDate actual = new ReleaseDate("Dec 25, 2015");
        assertEquals(expected, actual);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Test
    public void testCreateFromInvalidString() throws Exception {
        exception.expect(IllegalArgumentException.class);
        ReleaseDate actual = new ReleaseDate("6 December");
    }

    @Test
    public void testCreateFromNullString() throws Exception {
        String string = null;
        exception.expect(IllegalArgumentException.class);
        ReleaseDate actual = new ReleaseDate(string);
    }

    @Test
    public void testCreateFromDateEarly1900() throws Exception {
        exception.expectMessage("Release date cannot be early than 1990!");
        ReleaseDate actual = new ReleaseDate("7 December 1899");
    }

    @Test
    public void testEqualsSameDates() throws Exception {
        ReleaseDate date1 = new ReleaseDate("7 December 2000");
        ReleaseDate date2 = new ReleaseDate("7 December 2000");
        assertEquals(date1, date2);
    }


    @Test
    public void testEqualsDifferentDates() throws Exception {
        ReleaseDate date1 = new ReleaseDate("6 December 2000");
        ReleaseDate date2 = new ReleaseDate("7 December 2000");
        assertNotEquals(date1, date2);
    }


    @Test
    public void testHashCodeSameDates() throws Exception {
        ReleaseDate date1 = new ReleaseDate("6 December 2010");
        ReleaseDate date2 = new ReleaseDate("6 December 2010");
        assertEquals(date1.hashCode(), date2.hashCode());
    }

    @Test
    public void testHashCodeDifferentDates() throws Exception {
        ReleaseDate date1 = new ReleaseDate("6 December 2005");
        ReleaseDate date2 = new ReleaseDate("7 December 2005");
        assertNotEquals(date1.hashCode(), date2.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        ReleaseDate date = new ReleaseDate("7 December 2014");
        assertEquals("2014-12-07", date.toString());
    }

    @Test
    public void testAsJsonString() throws Exception {
        ReleaseDate expected = new ReleaseDate("7 December 2014");
        JsonString json = expected.toJsonString();
        ReleaseDate actual = new ReleaseDate(json);
        assertEquals(expected, actual);
    }
}