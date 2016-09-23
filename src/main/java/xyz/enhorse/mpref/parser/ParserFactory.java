package xyz.enhorse.mpref.parser;

import org.atteo.classindex.ClassIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * It is a utility class producing {@code Parser} depending on URL
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         20.12.2015
 */
public enum ParserFactory {
    ;
    private static final Logger LOGGER = LoggerFactory.getLogger(ParserFactory.class);

    private static List<Class<?>> parsers = new ArrayList<Class<?>>() {{
        for (Class<?> klass : ClassIndex.getAnnotated(Parsers.class)) {
            Parsers parser = klass.getAnnotation(Parsers.class);
            if (parser != null) {
                add(klass);
            }
        }
    }};

    public static Parser getParser(URL url) {
        if (url == null) return null;

        Parser result;
        for (Class parser : parsers) {
            result = acquireParser(parser, url);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private static Parser acquireParser(Class<?> klass, URL url) throws InapplicableURLException {
        Parser result = null;
        Constructor constructor = null;
        try {
            constructor = klass.getDeclaredConstructor(URL.class);
        } catch (NoSuchMethodException e) {
            LOGGER.debug("ERROR: Error with getDeclaredConstructor: " + e.getMessage());
        }
        try {
            if (constructor != null) {
                result = (Parser) constructor.newInstance(url);
            }
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            LOGGER.debug("DEBUG: Error with creating an object of " + klass.getSimpleName()
                    + " from the url: " + url.toString());
        }
        return result;
    }
}
