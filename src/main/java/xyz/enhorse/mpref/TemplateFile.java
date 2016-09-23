package xyz.enhorse.mpref;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Class represents a template file with detection of its encoding
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         28.12.2015
 */
public class TemplateFile {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateFile.class);
    private static final String DEFAULT_ENCODING = "utf-8";

    private String encoding;
    private File template;


    public TemplateFile(File template) throws IllegalArgumentException {
        setTemplate(template);
    }


    public String encoding() {
        return encoding;
    }


    public File file() {
        return template;
    }


    private void setEncoding() {
        encoding = detectEncoding();
    }


    private void setTemplate(File value) throws IllegalArgumentException {
        if (value == null) throw new IllegalArgumentException("Template file cannot be null!");
        if (!value.exists()) throw new IllegalArgumentException("Template file must exists!");

        template = value;
        setEncoding();
    }


    private String detectEncoding() {
        String result = null;
        try {
            Document doc = Jsoup.parse(template, DEFAULT_ENCODING);
            Element element = doc.select("meta[http-equiv=content-type]").first();
            if (element != null) {
                String content = element.attr("content");
                content = removeSpaces(content);
                String encodingMark = "charset=";
                result = content.substring(content.indexOf(encodingMark) + encodingMark.length());
            }
        } catch (IOException ex) {
            LOGGER.error("ERROR: Error detecting encoding for the template file \"" + template + "\" : " + ex.getMessage());
        }

        return (result != null)
                ? result
                : DEFAULT_ENCODING;
    }


    private static String removeSpaces(String line) {
        StringBuilder result = new StringBuilder();
        for (char ch : line.toCharArray()) {
            if (ch != ' ') {
                result.append(ch);
            }
        }
        return result.toString();
    }
}
