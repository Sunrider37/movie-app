package com.example.movies.controller;

import com.example.movies.model.Result;
import com.example.movies.service.FileSystemStorageService;
import com.example.movies.service.StorageProperties;
import com.example.movies.utils.ResultEnum;
import com.example.movies.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MovieUploadController {

    private final FileSystemStorageService storageService;
    private final StorageProperties storageProperties;

    @GetMapping("/movie-src-list")
    public Result<List<String>> listUploadMovies() {
        return ResultUtil.success(ResultEnum.GET_MOVIE_SRC_LIST,
                storageService.loadAll().map(path ->
                        MvcUriComponentsBuilder.fromMethodName(
                                MovieUploadController.class,
                                "serveMovie",
                                path.getFileName().toString()
                        ).build().toString()).collect(Collectors.toList()));
    }

    @GetMapping("/movie/{filename:.+")
    public ResponseEntity<Resource> serveMovie(@PathVariable String filename){
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/play/{movie}")
    public Result<String> uploadMovieFile(@PathVariable("movie") String movie){
        if(storageService.loadAll().noneMatch(
                path -> path.getFileName().toString().contains(movie)
        )){
            File file = storageProperties.getSourceFile(movie);
            storageService.store(file);
        }

        return ResultUtil.success(ResultEnum.UPLOAD_MOVIE_FILE,
                MvcUriComponentsBuilder.fromMethodName(
                        MovieUploadController.class, "serveMovie",movie
                ).build().toString() + ".mp4");

    }

    @PostMapping("/upload")
    public Result<String> uploadMovieFile(@RequestParam("file")MultipartFile file){
        storageService.store(file);
        return ResultUtil.success(ResultEnum.UPLOAD_MOVIE_FILE,
                MvcUriComponentsBuilder.fromMethodName(
                        MovieUploadController.class,"serveMovie",
                        file.getOriginalFilename()
                ).build().toString());
    }
}
