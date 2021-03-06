package xyz.enhorse.mpref.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

/**
 * {@code Repository} implementation as {@code File}
 *
 * @author <a href="mailto:go2amd@gmail.com">Pavel Kalinin</a>
 *         22.12.2015
 */
public class TextFileRepository implements Repository<String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextFileRepository.class);

    private File file;

    public TextFileRepository(File file) throws IllegalArgumentException {
        setFile(file);
    }


    private void setFile(File value) throws IllegalArgumentException {
        if (value == null) throw new IllegalArgumentException("Data file cannot be null!");
        if (!value.exists()) throw new IllegalArgumentException("Data file: \"" + value.toString() + "\" must exist!");
        file = value;
    }


    @Override
    public Set<String> getAll() {

        Set<String> result = new HashSet<>();

        try (BufferedReader buffer = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = buffer.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException ex) {
            LOGGER.error("Getting All from file: \"" + String.valueOf(file) + "\"; message = " + ex.getMessage());
        }

        return result;
    }


    @Override
    public boolean put(String value) {
        if (value == null) return false;

        boolean result = true;
        String toAdd = (file.length() > 0)
                ? String.format("%n%s", value)
                : value;
        try {
            Files.write(file.toPath(), toAdd.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException ex) {
            LOGGER.error("Putting \"" + value + "\" in file: \""
                    + String.valueOf(file) + "\"; message = " + ex.getMessage());
            result = false;
        }
        return result;
    }


    @Override
    public boolean remove(String value) {
        if (value == null) return false;

        boolean result = true;
        Set<String> content = getAll();

        if (content.contains(value)) {
            File tmp = createTempFile();
            if (tmp == null) {
                return false;
            }

            try (PrintWriter tmpWriter = new PrintWriter(new BufferedWriter(new FileWriter(tmp, true)))) {
                int size = content.size();
                int i = 0;
                for (String line : content) {
                    if (!value.equals(line)) {
                        i += 1;
                        if (i != size - 1) {
                            tmpWriter.println(line);
                        } else {
                            tmpWriter.print(line);
                        }
                    }
                }
            } catch (IOException ex) {
                LOGGER.error("Removing \"" + value + "\" from file: \""
                        + String.valueOf(file) + "\"; message = " + ex.getMessage());
                result = false;
            }

            result = result && file.delete() && tmp.renameTo(file);
        }

        return result;
    }


    private File createTempFile() {
        File result;

        try {
            result = File.createTempFile("tmp_", ".tmp");
        } catch (IOException ex) {
            LOGGER.error("Creating temp file for \": " + String.valueOf(file) + "\"; message = " + ex.getMessage());
            result = null;
        }

        return result;
    }
}
