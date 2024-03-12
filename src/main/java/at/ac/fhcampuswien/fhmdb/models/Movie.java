package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class Movie {
    private String title;
    private String description;
    // TODO add more properties here
    private List<String> genres;

    public Movie(String title, String description, List<String> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;

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

    // Add this method to get all genres
    public static List<String> getAllGenres() {
        return Arrays.asList("ACTION", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY",
                "CRIME", "DRAMA", "DOCUMENTARY", "FAMILY", "FANTASY", "HISTORY", "HORROR",
                "MUSICAL", "MYSTERY", "ROMANCE", "SCIENCE_FICTION", "SPORT", "THRILLER", "WAR",
                "WESTERN");
    }

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();
        Movie theGodfather = new Movie("The Godfather","The Godfather \"Don\" Vito Corleone is the head of the Corleone mafia family in New York. He is at the event of his daughter's wedding. Michael, Vito's youngest son and a decorated WW II Marine is also present at the wedding. Michael seems to be uninterested in being a part of the family business. Vito is a powerful man, and is kind to all those who give him respect but is ruthless against those who do not. But when a powerful and treacherous rival wants to sell drugs and needs the Don's influence for the same, Vito refuses to do it. What follows is a clash between Vito's fading old values and the new ways which may cause Michael to do the thing he was most reluctant in doing and wage a mob war against all the other mafia families which could tear the Corleone family apart.",List.of("ACTION", "DRAMA"));
        Movie inception = new Movie("Inception", "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.",List.of("ACTION", "FANTASY"));
        movies.add(theGodfather);
        movies.add(inception);
        return movies;
    }
}


