package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieAPITest {
    @Test
    public void testSearchMovies() {
        // Create an instance of MovieAPI
        MovieAPI movieAPI = new MovieAPI();

        // Call the searchMovies method
        List<Movie> movies = movieAPI.searchMovies("Wolf", Genre.ACTION, 2019, 0.0);

        // Check that the method returned a non-null result
        assertNotNull(movies);
    }

    @Test
    public void testGetAllMovies() {
        // Create an instance of MovieAPI
        MovieAPI movieAPI = new MovieAPI();

        // Call the getAllMovies method
        List<Movie> movies = movieAPI.getAllMovies();

        // Check that the method returned a non-null result
        assertNotNull(movies);

        // Check that the method returned at least one movie
        assertFalse(movies.isEmpty());
    }

    @Test
    public void testExpectedMovieAmount() {
        // Create an instance of MovieAPI
        MovieAPI movieAPI = new MovieAPI();

        // Call the getAllMovies method
        List<Movie> movies = movieAPI.getAllMovies();

        int expectedNumberOfMovies = 31; // Ersetzen Sie dies durch die tatsächliche erwartete Anzahl
        assertEquals(expectedNumberOfMovies, movies.size());

        // Check that the method returned a non-null result
        assertNotNull(movies);
    }

    @Test
    public void testSearchGenre() {
            // Create an instance of MovieAPI
            MovieAPI movieAPI = new MovieAPI();

            // Call the getAllMovies method
            List<Movie> movies = movieAPI.searchMovies("", Genre.ADVENTURE, 0, 0.0);

            int expectedNumberOfMovies = 9;
            assertEquals(expectedNumberOfMovies, movies.size());

            // Check that the method returned a non-null result
            assertNotNull(movies);


        }

        @Test void testQuery() {
                // Create an instance of MovieAPI
                MovieAPI movieAPI = new MovieAPI();

                // Call the getAllMovies method
                List<Movie> movies = movieAPI.searchMovies("Dark", Genre.ALL, 0, 0.0);

                // Check that the method returned a non-null result
                assertNotNull(movies);

            }
}
