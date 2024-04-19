package at.ac.fhcampuswien.fhmdb.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class Movie implements Comparable<Movie>{
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

    public static List<Movie> initializeMovies(){
        /*List<Movie> movies = new ArrayList<>();
        MovieAPI movieAPI = new MovieAPI();

        List<Movie> fetchedMovies = movieAPI.getAllMovies();
            movies.addAll(fetchedMovies);
        return movies;*/

        //API integration
        MovieAPI movieAPI = new MovieAPI();
        List<Movie> movies = movieAPI.getAllMovies();
        return movies;
    }

    @Override
    public int compareTo(Movie movie) {
        return this.title.compareTo(movie.getTitle());
    }
    /*public boolean containsString(String substring) { //Checks if the string of title and description contains the given substring
        return String.format("%s %s", title, description).toLowerCase().contains(substring.toLowerCase());
    }*/

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


