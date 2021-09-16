package com.example.movies.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@ConfigurationProperties(value = "storage")
@Setter
@Getter
public class StorageProperties {

    private String source;

    private String location;

    public File getSourceFile(String fileName) {
        return new File(String.format(source, fileName));
    }
}
