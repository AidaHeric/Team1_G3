package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.MovieEntity;
import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class DatabaseTest {

    MovieRepository testing = new MovieRepository();

    public DatabaseTest() throws DatabaseException {
    }

    public Movie initalizeMovie(){
        ArrayList<Genre> movie1Genres = new ArrayList();
        movie1Genres.add(Genre.ANIMATION);
        movie1Genres.add(Genre.FAMILY);
        ArrayList<String> movie1Directors = new ArrayList<>();
        movie1Directors.add("Hermann");
        ArrayList<String> movie1Writers = new ArrayList<>();
        movie1Writers.add("Hermann");
        ArrayList<String> movie1Actors = new ArrayList<>();
        movie1Actors.add("Hermann");
        Movie movie1 = new Movie("one","das Ist Der ErsteFilm","Hermanns erster Film.", movie1Genres,
                2024, 120, "http:/image/.com/", movie1Directors,movie1Actors,1.2);
        return movie1;
    }

    @Test
    public void testGenresToString() {
        List<Genre> movieGenres2 = initalizeMovie().getGenres();
        String actual = testing.genresToString(movieGenres2);

        assertEquals("ANIMATION,FAMILY",actual);
    }

    @Test
    public void testMovietoMovieEntity(){
        String actual = String.valueOf(testing.movieToMovieEntity(initalizeMovie()).getClass());

        assertEquals("class at.ac.fhcampuswien.fhmdb.database.MovieEntity",actual);

    }

    @Test
    public void testGenreStringToList(){
        String genreString = "ANIMATION,FAMILY";
        List<Genre> genreList = testing.convertGenresToList(genreString);

        assertEquals(List.of(Genre.ANIMATION,Genre.FAMILY),genreList);
    }


}
