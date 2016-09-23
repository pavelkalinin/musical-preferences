package xyz.enhorse.mpref.parser;

import xyz.enhorse.mpref.domain.Album;

import java.net.URL;

/**
 * Interface of parsing urls for gathering album details
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         17.12.2015
 */

public interface Parser {
    Album process();

    URL getSource();

    static boolean fitTo(URL url, String host, String mandatoryPathElement) {
        if (url == null) return false;

        String urlHost = url.getHost();
        String urlPath = url.getPath();
        if ((urlHost == null) || (urlPath == null)) return false;

        boolean result = (host == null) || (host.equalsIgnoreCase(urlHost));
        result = result && ((mandatoryPathElement == null) || (urlPath.contains(mandatoryPathElement)));

        return result;
    }

}