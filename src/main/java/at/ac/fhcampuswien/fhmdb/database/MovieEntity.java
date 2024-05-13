package at.ac.fhcampuswien.fhmdb.database;


import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public long getId() {
        return id;
    }

    public String getApiId() {
        return apiId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getGenres() {
        return genres;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public double getRating() {
        return rating;
    }
}
