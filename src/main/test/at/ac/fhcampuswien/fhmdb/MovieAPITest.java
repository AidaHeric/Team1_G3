package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.exceptions.MovieAPIException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieAPITest {

    //Test for searching movies wit specific parameters
    @Test
    public void testSearchMovies() throws MovieAPIException {
        //Create an instance of MovieAPI
        MovieAPI movieAPI = new MovieAPI();

        //Call the searchMovies method
        List<Movie> movies = movieAPI.searchMovies("Wolf", Genre.ACTION, 2019, 0.0);

        //Check that the method returned a non-null result
        assertNotNull(movies);
    }

    //Test for getting all movies
    @Test
    public void testGetAllMovies() throws MovieAPIException {
        //Create an instance of MovieAPI
        MovieAPI movieAPI = new MovieAPI();

        //Call the getAllMovies method
        List<Movie> movies = movieAPI.getAllMovies();

        //Check that the method returned a non-null result
        assertNotNull(movies);

        //Check that the method returned at least one movie
        assertFalse(movies.isEmpty());
    }

    //Test for checking if all movies are parsed from API
    @Test
    public void testExpectedMovieAmount() throws MovieAPIException {
        //Create an instance of MovieAPI
        MovieAPI movieAPI = new MovieAPI();

        //Call the getAllMovies method
        List<Movie> movies = movieAPI.getAllMovies();

        int expectedNumberOfMovies = 31; //Expected number of movies from API
        assertEquals(expectedNumberOfMovies, movies.size());

        //Check that the method returned a non-null result
        assertNotNull(movies);
    }

    //Test for searching movies with specific genre
    @Test
    public void testSearchGenre() throws MovieAPIException {
            //Create an instance of MovieAPI
            MovieAPI movieAPI = new MovieAPI();

            //Call the getAllMovies method
            List<Movie> movies = movieAPI.searchMovies("", Genre.ADVENTURE, 0, 0.0);

            int expectedNumberOfMovies = 9;
            assertEquals(expectedNumberOfMovies, movies.size());

            //Check that the method returned a non-null result
            assertNotNull(movies);


        }

        //Test for searching movies with specific query
        @Test void testQuery() throws MovieAPIException {
                //Create an instance of MovieAPI
                MovieAPI movieAPI = new MovieAPI();

                //Call the getAllMovies method
                List<Movie> movies = movieAPI.searchMovies("Dark", Genre.ALL, 0, 0.0);

                //Check that the method returned a non-null result
                assertNotNull(movies);

            }
}
