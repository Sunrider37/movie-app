package com.example.movies.repository;

import com.example.movies.model.Movie;
import com.example.movies.model.Type;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {
    Type findByName(String name);

    @Query(nativeQuery = true)
    List<Movie> findMovies(@Param("typeName") String type, Pageable pageable);

    long countByName(String name);
}
