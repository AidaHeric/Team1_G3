package at.ac.fhcampuswien.fhmdb.models;
import at.ac.fhcampuswien.fhmdb.database.MovieEntity;
import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class Movie {
    private String title;                   //Mithilfe von Swagger
    private String description;
    private List<Genre> genres;
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

    public Movie(String id, String title, String description, List<Genre> genres, int releaseYear, int lengthInMinutes, String imgUrl, double rating){
        this.id = id;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.lengthInMinutes = lengthInMinutes;
        this.imgUrl = imgUrl;
        this.rating = rating;
    }

    public static List<Movie> initializeMovies() throws DatabaseException {
        MovieRepository movieRepository = new MovieRepository();
        List<Movie> movies = new ArrayList<>();
        List<MovieEntity> movieEntities = movieRepository.getAllMovies();

        for (MovieEntity movieEntity : movieEntities) {
            Movie movie = movieRepository.toMovies(movieEntity);
            movies.add(movie);
        }
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


