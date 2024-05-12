package at.ac.fhcampuswien.fhmdb.database;


import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "movie")
public class MovieEntity {
    @DatabaseField(generatedId = true, id = true)
    private long id;
    @DatabaseField
    private String apiId;
    @DatabaseField
    String title;
    @DatabaseField
    String description;
    @DatabaseField
    String genres;
    @DatabaseField
    int releaseYear;
    @DatabaseField
    String imgUrl;
    @DatabaseField
    int lengthInMinutes;
    @DatabaseField
    double rating;

    String genresToString(List<Genre> genres) {
        return null;
    }

    List<MovieEntity> fromMovies(List<Movie> movies) {

        return null;
    }

    List<Movie> toMovies(List<MovieEntities> movieEntities) {

        return null;
    }

}
