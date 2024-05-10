package at.ac.fhcampuswien.fhmdb.models;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName = "movies")

public class Movie {
    @DatabaseField

    private String title;                   //Mithilfe von Swagger
    private String description;
    private List<Genre> genres;
    @DatabaseField(id = true)

    private String id;
    private int releaseYear;
    private String imgUrl;
    private int lengthInMinutes;
    private List<String> directors;
    private List<String> mainCast;
    private double rating;


    public Movie(String id, String title, String description, List<Genre> genres, int releaseYear, int lengthInMinutes, String imgUrl, List<String> directors, List<String> mainCast, double rating){
        this.id = id;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.lengthInMinutes = lengthInMinutes;
        this.imgUrl = imgUrl;
        this.directors = directors;
        this.mainCast = mainCast;
        this.rating = rating;
    }

    public static List<Movie> initializeMovies(){
        MovieAPI movieAPI = new MovieAPI();                 //we now get movies from API
        List<Movie> movies = movieAPI.getAllMovies();
        return movies;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public String getId() {
        return id;
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

    public List<String> getDirectors() {
        return directors;
    }

    public List<String> getMainCast() {
        return mainCast;
    }

    public double getRating() {
        return rating;
    }
    
}


