package com.example.movies.controller;

import com.example.movies.exception.MovieNotFoundException;
import com.example.movies.model.Movie;
import com.example.movies.model.Result;
import com.example.movies.model.Type;
import com.example.movies.repository.MovieRepository;
import com.example.movies.repository.TypeRepository;
import com.example.movies.utils.ResultEnum;
import com.example.movies.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieRepository movieRepository;
    private final TypeRepository typeRepository;

    @GetMapping("/movie-list/{page}")
    public Result<List<Movie>> getMovieInfoList(@PathVariable int page){
        Pageable pageable = PageRequest.of(page,12);
        Page<Movie> movies = movieRepository.findAll(pageable);
        return ResultUtil.success(ResultEnum.GET_MOVIE_INFO_LIST, movies.getContent());
    }

    @GetMapping("/movie-count")
    public Result<Long> getMovieCount(){
        return ResultUtil.success(ResultEnum.GET_MOVIE_COUNT, movieRepository.count());
    }

    @GetMapping("/{title}")
    public Result<Movie> getMovieDetail(@PathVariable String title){
        Movie movie = movieRepository.findByTitle(title);
        if(movie == null){
            throw new MovieNotFoundException(ResultEnum.MOVIE_NOT_FOUND);
        }
        return ResultUtil.success(ResultEnum.GET_MOVIE_DETAIL, movie);
    }

    @PostMapping(value = "/add-movie/{type}")
    public Result addMovieInfo(@PathVariable("type") String type,
                               @RequestBody Movie movie) {
        Movie find = movieRepository.findByTitle(movie.getTitle());
        if (find != null) {
            return ResultUtil.error(ResultEnum.MOVIE_DUPLICATE);
        }
        Set<Type> movieType = new HashSet<>();
        for (String t : type.split("&")) {
            movieType.add(typeRepository.findByName(t));
        }
        movie.setType(movieType);
        movieRepository.save(movie);
        return ResultUtil.success(ResultEnum.ADD_MOVIE);
    }

    @PostMapping(value = "/delete/{title}")
    public Result deleteMovieInfo(@PathVariable("title") String title) {
        Movie movie = movieRepository.findByTitle(title);
        if (movie == null) {
            return ResultUtil.error(ResultEnum.MOVIE_NOT_FOUND);
        }
        movieRepository.delete(movie);
        return ResultUtil.success(ResultEnum.DELETE_MOVIE);
    }

    @PostMapping(value = "/delete-more/{movies}")
    public Result deleteMovies(@PathVariable("movies") String selectedMovies) {
        for (String movie : selectedMovies.split("&")) {
            Result result = deleteMovieInfo(movie);
            if (!result.getStatus().equals(ResultEnum.DELETE_MOVIE.getStatus())) {
                return result;
            }
        }
        return ResultUtil.success(ResultEnum.DELETE_MORE_MOVIES);
    }
}
