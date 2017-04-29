package com.epam.esm.task1.scheduling.helper;

import com.epam.esm.task1.dto.NewsDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Parses a content of files with news.
 *
 * @author Uladzislau Kastsevich
 */
@Component
public class NewsLoaderParsingHelper {
    @Value("${newsLoader.supportedFileExtensions}")
    private String supportedFileExtensionsProperty;

    private Set<String> supportedExtensions = new HashSet<>();

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void initSupportedExtensions() {
        String[] supportedFileExtensionsValues = supportedFileExtensionsProperty.split(",");
        Collections.addAll(supportedExtensions, supportedFileExtensionsValues);
    }

    /**
     * Parses a given JSON-file to a list of NewsDto.
     *
     * @param jsonFile a file with news to parse
     * @return a list of news
     * @throws IOException when there is some error while parsing
     */
    public List<NewsDto> parseJsonFile(File jsonFile) throws IOException {
        if (!supportedExtensions.contains(getFileExtension(jsonFile))) {
            throw new IOException("Unsupported file format");
        }

        return objectMapper.readValue(jsonFile, new TypeReference<List<NewsDto>>() {});
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }
}
