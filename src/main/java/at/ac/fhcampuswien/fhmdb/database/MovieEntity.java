package at.ac.fhcampuswien.fhmdb.database;


import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "movie")
public class MovieEntity {
    @DatabaseField(generatedId = true)
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

    //TODO: Implement these methods
    String genresToString(List<Genre> genres) {
        return null;
    }

    List<MovieEntity> fromMovies(List<Movie> movies) {

        return null;
    }

    List<Movie> toMovies(List<MovieEntity> movieEntities) {

        return null;
    }


    public MovieEntity() {
    }

    public MovieEntity(String apiId, String title, String description, String genres, int releaseYear, String imgUrl, int lengthInMinutes, double rating) {
        this.apiId = apiId;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
    }
}
