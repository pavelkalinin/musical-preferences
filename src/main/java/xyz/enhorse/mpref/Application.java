package xyz.enhorse.mpref;

import xyz.enhorse.mpref.repo.Repository;
import xyz.enhorse.mpref.repo.TextFileRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Main Application Class
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 * @version 0.1.0, 17.12.2015
 */
public class Application {

    public static void main(String[] args) throws IOException {
        String musicURLs = Application.class.getClass().getResource("/music.urls").getFile();
        File in = new File(musicURLs);
        Repository<String> repository;
        repository = new TextFileRepository(in);

        String template = Application.class.getClass().getResource("/templates/music.ftl").getFile();
        File templateFile = new File(template);
        File result = new File("music.html");
        FileOutputStream out = new FileOutputStream(result);
        AlbumsPage.makeHTML(repository, new TemplateFile(templateFile), out);
    }

}