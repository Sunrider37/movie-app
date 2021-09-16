package com.example.movies.exception;

import com.example.movies.utils.ResultEnum;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(ResultEnum movieNotFound) {
    }
}
