package com.epam.esm.task1.scheduling;

import com.epam.esm.task1.dto.NewsDto;
import com.epam.esm.task1.scheduling.helper.NewsLoaderDirectoryHelper;
import com.epam.esm.task1.scheduling.helper.NewsLoaderParsingHelper;
import com.epam.esm.task1.service.NewsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Loads and process files with news in a shared directory.
 *
 * @author Uladzislau Kastsevich
 */
@Component
public class NewsLoader {
    private static final Logger log = LogManager.getLogger(NewsLoader.class.getName());

    @Autowired
    @Qualifier("loadNewsExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private NewsService newsService;
    @Autowired
    private NewsLoaderDirectoryHelper directoryHelper;
    @Autowired
    private NewsLoaderParsingHelper parsingHelper;

    private Set<File> processedFiles = Collections.newSetFromMap(new ConcurrentHashMap<>());

    /**
     * Read all files from the shared directory and all of its subdirectories
     * and runs threads for a single file with news processing.
     * Invokes periodically according to a schedule of the TaskScheduler.
     */
    public void loadNews() {
        List<File> filesWithNews = directoryHelper.getFilesFromSharedDirectory();
        for (final File fileWithNews : filesWithNews) {
            if (!processedFiles.contains(fileWithNews)) {
                processedFiles.add(fileWithNews);
                taskExecutor.execute(() -> {
                    try {
                        List<NewsDto> newsDtoList = parsingHelper.parseJsonFile(fileWithNews);
                        newsService.addListOfNews(newsDtoList);
                        removeFile(fileWithNews);
                    } catch (Exception e) {
                        log.error("Error while processing file with news", e);
                        moveFileToErrorDirectory(fileWithNews, e);
                    } finally {
                        processedFiles.remove(fileWithNews);
                    }
                });
            }
        }
    }

    private void moveFileToErrorDirectory(File file, Exception cause) {
        try {
            directoryHelper.moveFileToErrorDirectory(file, cause);
        } catch (IOException e) {
            log.error("Exception while moving invalid file into error-directory", e);
        }
    }

    private void removeFile(File file) {
        try {
            directoryHelper.removeFile(file);
        } catch (IOException e) {
            log.error("Exception while removing a processed file", e);
        }
    }
}
