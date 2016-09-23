package xyz.enhorse.mpref.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import xyz.enhorse.mpref.json.JsonString;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         17.12.2015
 */

public class CoverTest {

    @Test
    public void testSetValidLink() throws Exception {
        Cover cover = new Cover("http://a4.mzstatic.com/us/r30/Music1/v4/f2/98/60/f2986087-4d37-4b36-52df-407bd9f6e024/cover170x170.jpeg");
        assertTrue(cover.getURL() != null);
    }

    @Test
    public void testSetNull() throws Exception {
        String string = null;
        Cover cover = new Cover(string);
        assertTrue(cover.getURL() == null);
    }

    @Test
    public void testEqualsSameCovers() throws Exception {
        Cover cover1 = new Cover("http://a4.mzstatic.com/us/r30/Music1/v4/f2/98/60/f2986087-4d37-4b36-52df-407bd9f6e024/cover170x170.jpeg");
        Cover cover2 = new Cover("http://a4.mzstatic.com/us/r30/Music1/v4/f2/98/60/f2986087-4d37-4b36-52df-407bd9f6e024/cover170x170.jpeg");
        assertEquals(cover1, cover2);
    }

    @Test
    public void testEqualsDifferentCovers() throws Exception {
        Cover cover1 = new Cover("http://a4.mzstatic.com/us/r30/Music1/v4/f2/98/60/f2986087-4d37-4b36-52df-407bd9f6e024/cover170x170.jpeg");
        Cover cover2 = new Cover("http://a3.mzstatic.com/us/r30/Music7/v4/e3/17/dc/e317dca0-3ff7-a282-439d-913706b5981c/cover170x170.jpeg");
        assertNotEquals(cover1, cover2);
    }

    @Test
    public void testHashcodeSameCovers() throws Exception {
        Cover cover1 = new Cover("http://a4.mzstatic.com/us/r30/Music1/v4/f2/98/60/f2986087-4d37-4b36-52df-407bd9f6e024/cover170x170.jpeg");
        Cover cover2 = new Cover("http://a4.mzstatic.com/us/r30/Music1/v4/f2/98/60/f2986087-4d37-4b36-52df-407bd9f6e024/cover170x170.jpeg");
        assertEquals(cover1.hashCode(), cover2.hashCode());
    }

    @Test
    public void testHashcodeDifferentCovers() throws Exception {
        Cover cover1 = new Cover("http://a4.mzstatic.com/us/r30/Music1/v4/f2/98/60/f2986087-4d37-4b36-52df-407bd9f6e024/cover170x170.jpeg");
        Cover cover2 = new Cover("http://a3.mzstatic.com/us/r30/Music7/v4/e3/17/dc/e317dca0-3ff7-a282-439d-913706b5981c/cover170x170.jpeg");
        assertNotEquals(cover1.hashCode(), cover2.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        String link = "http://a4.mzstatic.com/us/r30/Music1/v4/f2/98/60/f2986087-4d37-4b36-52df-407bd9f6e024/cover170x170.jpeg";
        Cover cover = new Cover(link);
        assertEquals(link, cover.toString());
    }

    @Test
    public void testToStringFromNull() throws Exception {
        String string = null;
        Cover cover = new Cover(string);
        assertEquals("", cover.toString());
    }

    @Test
    public void testAsJsonString() throws Exception {
        String link = "http://a4.mzstatic.com/us/r30/Music1/v4/f2/98/60/f2986087-4d37-4b36-52df-407bd9f6e024/cover170x170.jpeg";
        Cover expected = new Cover(link);
        JsonString json = expected.toJsonString();
        Cover actual = new Cover(json);
        assertEquals(expected, actual);
    }


    @Test
    public void testAsJsonStringWithNullUrl() throws Exception {
        String link = null;
        Cover expected = new Cover(link);
        JsonString json = expected.toJsonString();
        Cover actual = new Cover(json);
        assertEquals(expected, actual);
    }


    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testCreateFromValidJson() throws Exception {
        Cover expected = new Cover("http://valid.url/cover.jpg");
        String json = "{\"url\" : \"http://valid.url/cover.jpg\"}";
        Cover actual = new Cover(new JsonString(json));
        assertEquals(expected, actual);
    }

    @Test
    public void testCreateFromNullJson() throws Exception {
        JsonString json = null;
        exception.expect(IllegalArgumentException.class);
        Cover actual = new Cover(json);
    }

    @Test
    public void testCreateFromInapplicableJson() throws Exception {
        String json = "{\"cover\" : \"Cover\"}";
        exception.expect(IllegalArgumentException.class);
        Cover actual = new Cover(new JsonString(json));
    }
}