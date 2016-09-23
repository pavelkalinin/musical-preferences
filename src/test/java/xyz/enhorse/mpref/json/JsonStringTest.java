package xyz.enhorse.mpref.json;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         30.12.2015
 */
public class JsonStringTest {

    @Test
    public void testCreateSimpleValid() throws Exception {
        String expected = "{parameter : \"value\"}";
        JsonString actual = new JsonString(expected);
        assertEquals(expected, actual.value());
    }

    @Test
    public void testCreateComplexValid() throws Exception {
        String expected = "{parameter : \"value\",complex : \"\\{\"parameter1\" : 1, \"parameter2\" : \"1\"\\}\"}";
        JsonString actual = new JsonString(expected);
        assertEquals(expected, actual.value());
    }

    @Test
    public void testValueFromValid() throws Exception {
        String expected = "{parameter : \"value\"}";
        JsonString actual = new JsonString(expected);
        assertEquals(expected, actual.value());
    }

    @Test
    public void testGetSimpleParameterFromValid() throws Exception {
        String json = "{parameter : \"value\"}";
        String actual = new JsonString(json).convertTo(String.class);
        String expected = "value";
        assertEquals(expected, actual);
    }

    @Test
    public void testGetComplexParameterFromValid() throws Exception {
        String json = "{parameter : \"value\", complex : {parameter1 : 1, parameter2 : \"1\"}}";
        String actual = new JsonString(json).convertTo(String.class);
        String expected = "value";
        assertEquals(expected, actual);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testCreateFromNull() throws Exception {
        exception.expect(IllegalArgumentException.class);
        new JsonString(null);
    }

    @Test
    public void testCreateFromEmptyString() throws Exception {
        exception.expect(IllegalArgumentException.class);
        new JsonString("");
    }

    @Test
    public void testCreateFromStringWithSpacesOnly() throws Exception {
        exception.expect(IllegalArgumentException.class);
        new JsonString("      ");
    }

    @Test
    public void testCreateFromIncorrectJson() throws Exception {
        exception.expect(IllegalArgumentException.class);
        new JsonString("incorrect");
    }

    @Test
    public void testGenerate() throws Exception {
        List<String> expected = new ArrayList<>();
        expected.add("One");
        expected.add("Two");
        expected.add("Three");
        JsonString json = JsonString.generateFor(expected);
        assert json != null;
        List actual = new ArrayList<>();
        actual = json.convertTo(actual.getClass());
        assertEquals(expected, actual);
    }
}