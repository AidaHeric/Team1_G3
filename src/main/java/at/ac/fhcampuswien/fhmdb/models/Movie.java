package at.ac.fhcampuswien.fhmdb.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;

public class Movie implements Comparable<Movie>{
    public List<Movie> getMovies;
    private String title;                   //Mithilfe von Swagger
    private String description;
    private List<String> genres;            //TODO: Enum array/list
    private String id;
    private int releaseYear;
    private String imgUrl;
    private int lengthInMinutes;
    private List<String> directors;
    private List<String> mainCast;
    private double rating;

    public Movie(String title, String description, List<String> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public Movie(String id, String title, String description, List<String> genres, int releaseYear, int lengthInMinutes, String imgUrl, List<String> directors, List<String> mainCast, double rating){
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

    public static List<String> getAllGenres() {
        return Arrays.asList("ALL","ACTION", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY",
                "CRIME", "DRAMA", "DOCUMENTARY", "FAMILY", "FANTASY", "HISTORY", "HORROR",
                "MUSICAL", "MYSTERY", "ROMANCE", "SCIENCE_FICTION", "SPORT", "THRILLER", "WAR",
                "WESTERN");
    }

    //DONE New initalizeMovies with API

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();
        MovieAPI movieAPI = new MovieAPI();

        List<Movie> fetchedMovies = movieAPI.getAllMovies();
        if (fetchedMovies != null) {
            movies.addAll(fetchedMovies);
        }
        return movies;

    }

    @Override
    public int compareTo(Movie movie) {
        return this.title.compareTo(movie.getTitle());
    }
    public boolean containsString(String substring) { //Checks if the string of title and description contains the given substring
        return String.format("%s %s", title, description).toLowerCase().contains(substring.toLowerCase());
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getGenres() {
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


