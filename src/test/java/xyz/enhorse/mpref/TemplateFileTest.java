package xyz.enhorse.mpref;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         28.12.2015
 */

@SuppressWarnings("unused")
public class TemplateFileTest {

    @Test
         public void testFileWithEncoding() throws Exception {
        File file = new File(getClass().getResource("/template.html").getFile());
        TemplateFile templateFile = new TemplateFile(file);
        assertTrue(templateFile.encoding().equals("windows-1251"));
    }


    @Test
    public void testFileWithoutEncoding() throws Exception {
        File file = new File(getClass().getResource("/templateWithoutEncoding.html").getFile());
        TemplateFile templateFile = new TemplateFile(file);
        assertTrue(templateFile.encoding().equals("utf-8"));
    }


    @Test
    public void testCorrectFile() throws Exception {
        File file = new File(getClass().getResource("/template.html").getFile());
        TemplateFile templateFile = new TemplateFile(file);
        assertTrue(templateFile.file().equals(file));
    }


    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testNullFile() throws Exception {
        exception.expect(IllegalArgumentException.class);
        TemplateFile templateFile = new TemplateFile(null);
    }


    @Test
    public void testNotExistFile() throws Exception {
        exception.expect(IllegalArgumentException.class);
        TemplateFile templateFile = new TemplateFile(new File("dummy"));
    }


    @Test
    public void testEmptyFile() throws Exception {
        File file = new File(getClass().getResource("/empty.txt").getFile());
        TemplateFile templateFile = new TemplateFile(file);
        assertTrue(templateFile.file().equals(file));
    }
}