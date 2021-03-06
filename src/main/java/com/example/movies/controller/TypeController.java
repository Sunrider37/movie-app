package com.example.movies.controller;

import com.example.movies.model.Movie;
import com.example.movies.model.Result;
import com.example.movies.model.Type;
import com.example.movies.repository.TypeRepository;
import com.example.movies.utils.ResultEnum;
import com.example.movies.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TypeController {

    private final TypeRepository typeRepository;
    @GetMapping(value = "/{type}/{page}")
    public Result<List<Movie>> getMovieListByType(@PathVariable("type") String type,
                                                  @PathVariable("page") int page) {
        List<Movie> movies = typeRepository.findMovies(type, PageRequest.of(page, 12));
        return ResultUtil.success(ResultEnum.GET_MOVIE_TYPE_LIST, movies);
    }

    @GetMapping(value = "/count/{type}")
    public Result<Long> getTypeCount(@PathVariable("type") String type) {
        return ResultUtil.success(ResultEnum.GET_MOVIE_COUNT, typeRepository.countByName(type));
    }

    @GetMapping(value = "/types")
    public Result<List<Type>> getAllTypes() {
        return ResultUtil.success(ResultEnum.GET_TYPES, typeRepository.findAll());
    }
}
