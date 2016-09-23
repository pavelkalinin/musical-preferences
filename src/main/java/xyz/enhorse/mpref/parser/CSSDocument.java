package xyz.enhorse.mpref.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

/**
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         18.12.2015
 */
class CSSDocument {
    private static final String USER_AGENT = "Mozilla";
    private static final String CSS_PATH_SEPARATOR = " > ";

    private Document base;

    CSSDocument(URL source) throws IOException {
        setBase(source);
    }

    String getSelectorText(String cssPath) {
        if (cssPath == null) return "";

        Elements selector = getTailSelector(cssPath);

        return ((selector != null) && (selector.size() > 0))
                ? selector.get(0).text()
                : "";
    }

    String getAttributeText(String cssPath, String attribute) {
        if ((cssPath == null) || (attribute == null)) return "";

        Elements selector = getTailSelector(cssPath);

        return (selector != null)
                ? selector.attr(attribute)
                : "";
    }

    private Elements getTailSelector(String cssPath) {
        String[] parts = cssPath.split(CSS_PATH_SEPARATOR);
        if (parts.length < 1) return null;

        Elements result = base.select(parts[0]);
        if (result == null) return null;

        for (int i = 1; i < parts.length; i++) {
            result = result.select(parts[i]);
        }

        return result;
    }

    private void setBase(URL source) throws IOException {
        base = Jsoup.connect(source.toString())
                .userAgent(USER_AGENT)
                .get();
    }
}
