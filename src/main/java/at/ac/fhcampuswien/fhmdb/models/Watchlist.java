package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.List;

public class Watchlist {

    private List<Movie> movies;

    public Watchlist() {
    this.movies = new ArrayList<>();
}

    public void addMovie(Movie movie) {
        if (!movies.contains(movie)) {
            movies.add(movie);
        } else {
            System.out.println("Movie already in watchlist");
        }
    }
    public void removeMovie(Movie movie) {
        if (movies.contains(movie)) {
            movies.remove(movie);
        } else {
            System.out.println("Movie not in watchlist");
        }
    }
    public List<Movie> getMovies() {
        return movies;
    }

}

