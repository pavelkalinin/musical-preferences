package xyz.enhorse.mpref;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import xyz.enhorse.mpref.repo.Repository;

/**
 * Class produces a HTML file from a repository of albums
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         24.12.2015
 */
public class AlbumsPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumsPage.class);

    public static void makeHTML(final Repository<String> repository, final TemplateFile templateFile, final OutputStream out)
            throws IOException, IllegalArgumentException {

        if (repository == null) throw new IllegalArgumentException("Repository cannot be null!");
        if (templateFile == null) throw new IllegalArgumentException("Template file cannot be null!");
        if (out == null) throw new IllegalArgumentException("Output stream cannot be null!");

        String templateFolder = templateFile.file().getParent();
        String templateFilename = templateFile.file().getName();

        List<String> albumUrls = new ArrayList<>(repository.getAll());
        List<WebAlbum> albums = collectAlbums(albumUrls);

        Map<String, Object> data = new HashMap<>();
        data.put("albums", albums);

        try {
            Configuration cfg = new Configuration(new Version(2, 3, 23));
            cfg.setDirectoryForTemplateLoading(new File(templateFolder));
            cfg.setDefaultEncoding(templateFile.encoding());
            Template template = cfg.getTemplate(templateFilename);

            OutputStreamWriter writer = new OutputStreamWriter(out, templateFile.encoding());
            template.process(data, writer);
        } catch (TemplateException ex) {
            String message = "Error processing the template \"" + templateFile + "\" : " + ex.getMessage();
            LOGGER.error(message);
            throw new IOException(message);
        }
    }

    private static List<WebAlbum> collectAlbums(List<String> urls) {
        if (urls == null) return new ArrayList<>();

        List<WebAlbum> result = new ArrayList<>(urls.size());
        result.addAll(urls.stream().map(WebAlbum::new).collect(Collectors.toList()));

        return result;
    }
}
