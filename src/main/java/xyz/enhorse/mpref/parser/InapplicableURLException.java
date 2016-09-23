package xyz.enhorse.mpref.parser;

import java.net.URL;

/**
 * Class represents {@code Exception} occurred due an URL as argument has {@code Null} or inapplicable value
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         17.12.2015
 */
public class InapplicableURLException extends IllegalArgumentException {
    public InapplicableURLException(URL url) {
        super((url != null)
                ? url.toString()
                : "URL cannot be null!");
    }
}