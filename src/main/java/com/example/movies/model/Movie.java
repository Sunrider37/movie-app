package com.example.movies.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    private Double score;

    private String alias;

    @Column(name = "release_date")
    private String releaseDate;

    private Integer length;

    private String director;

    private String screenwriter;

    @Column(columnDefinition = "text")
    private String cast;

    @Column(columnDefinition = "text")
    private String overview;

    private String post;

    @ManyToMany
    @JoinTable(name = "movie_type",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id"))
    private Set<Type> type;

}
