package com.epam.esm.task1.scheduling.helper;

import com.epam.esm.task1.dto.validator.exception.DtoValidationException;
import com.epam.esm.task1.scheduling.NewsLoader;
import com.epam.esm.task1.service.exception.ServiceValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Works with files in shared directory.
 *
 * @author Uladzislau Kastsevich
 */
@Component
public class NewsLoaderDirectoryHelper {
    private static final Logger log = LogManager.getLogger(NewsLoader.class.getName());

    private static final String TIMESTAMP_PATTERN = "yyyy_MM_dd HH_mm_ss_";

    @Value("${newsLoader.sharedDirectoryPath}")
    private String sharedDirectoryPath;
    @Value("${newsLoader.invalidFilesDirectoryName}")
    private String invalidFilesDirectoryName;
    @Value("${newsLoader.unreadableFilesDirectoryName}")
    private String unreadableFilesDirectoryName;
    @Value("${newsLoader.invalidNewsDirectoryName}")
    private String invalidNewsDirectoryName;
    @Value("${newsLoader.unexpectedErrorDirectoryName}")
    private String unexpectedErrorDirectoryName;

    private Path sharedDirectory;
    private Path invalidFilesDirectory;
    private Path unreadableFilesDirectory;
    private Path invalidNewsDirectory;
    private Path unexpectedErrorDirectory;

    /**
     * Creates references to the directories from given path and name values.
     */
    @PostConstruct
    public void initFiles() {
        sharedDirectory = Paths.get(sharedDirectoryPath);
        invalidFilesDirectory = sharedDirectory.resolve(invalidFilesDirectoryName);
        unreadableFilesDirectory = invalidFilesDirectory.resolve(unreadableFilesDirectoryName);
        invalidNewsDirectory = invalidFilesDirectory.resolve(invalidNewsDirectoryName);
        unexpectedErrorDirectory = invalidFilesDirectory.resolve(unexpectedErrorDirectoryName);
    }

    /**
     * Gets a list of all files in the shared directory and all of its subdirectories (except invalid files directories).
     *
     * @return a list of files
     */
    public List<File> getFilesFromSharedDirectory() {
        final List<File> filesInSharedDirectory = new ArrayList<>();
        try(Stream<Path> paths = Files.walk(sharedDirectory)) {
            paths.forEach(path -> {
                if(Files.isRegularFile(path) && !path.startsWith(invalidFilesDirectory)) {
                    filesInSharedDirectory.add(path.toFile());
                }
            });
        } catch (IOException e) {
            log.error("Exception while reading files in the shared directory", e);
        }

        return filesInSharedDirectory;
    }

    /**
     * Moves a given file to the error directory by the error type.
     *
     * @param fileToMove invalid file
     * @param cause exception that causes error
     * @throws IOException when there is error while moving a file
     */
    public void moveFileToErrorDirectory(File fileToMove, Exception cause) throws IOException {
        Path errorDirectory;
        if (cause instanceof IOException) {
            errorDirectory = unreadableFilesDirectory;
        } else if (cause instanceof DtoValidationException || cause instanceof ServiceValidationException) {
            errorDirectory = invalidNewsDirectory;
        } else {
            errorDirectory = unexpectedErrorDirectory;
        }

        String fileNameInErrorDirectory = LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN)) + fileToMove.getName();
        Path targetPath = errorDirectory.resolve(fileNameInErrorDirectory);
        Files.move(fileToMove.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Removes a given file from the file system.
     *
     * @param file a file to remove
     * @throws IOException when there is error while removing a file
     */
    public void removeFile(File file) throws IOException {
        Files.delete(file.toPath());
    }
}
