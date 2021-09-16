package com.example.movies.model;

import com.example.movies.utils.TypeSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SqlResultSetMapping(
        name = "Movie",
        classes = {
                @ConstructorResult(
                        targetClass = Movie.class, columns = {
                                @ColumnResult(name = "title", type = String.class),
                                @ColumnResult(name = "score", type = Double.class),
                                @ColumnResult(name = "post", type = String.class)
                })

        }
)
@NamedNativeQuery(
        name = "Type.findMovies", query = "select movie.title,movie.score,movie.post " +
        "from movie, type, movie_type where movie_type.type_id=type_id " +
        "and movie_type.movie_id=movie.id and type.name=:typeName",
resultSetMapping = "Movie")
@JsonSerialize(using = TypeSerialize.class)
public class Type {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "movie_type",
            joinColumns = @JoinColumn(name = "type_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Set<Movie> movies;
}
