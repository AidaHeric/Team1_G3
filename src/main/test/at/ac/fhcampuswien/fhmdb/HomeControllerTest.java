package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextField;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static javafx.application.Platform.runLater;
import static org.junit.Assert.*;


// Tests for the HomeController.java
public class HomeControllerTest {
    //Instance for the controller
    HomeController controller = new HomeController();

    //List of all movies
    List<Movie> allMovies = new ArrayList<>();

    //List of filtered movies
    List<Movie> filteredMovies = new ArrayList<>();

    private static boolean toolkitInitialized = false;

    /*public void addToAllMovies(){
        Movie starWars = new Movie("Star Wars: Episode IV - A New Hope", "A farm boy joins a rebel alliance to save the galaxy from an evil empire.", List.of("ACTION", "ADVENTURE", "SCIENCE_FICTION"));
        allMovies.add(starWars);
    }*/



    @Before
    public void setUp() {                                                                                 //by ChatGPT
        //Initializing controller
        controller = new HomeController();
        //Initializing the JavaFX platform
        if(!toolkitInitialized){
            Platform.startup(() -> {
                new JFXPanel();
                controller.searchField = new TextField(); //Initializing the search field
                controller.genreComboBox = new JFXComboBox(); //Initializing the genre ComboBox
                controller.sortBtn = new JFXButton(); //Initializing the sort button
            });
            toolkitInitialized = true;
        }
    }

   @Test
    public void testQueryFilter() {
       Platform.runLater(() -> {
        controller.searchField.setText("Godfather");
        controller.handleSearch();
        assertEquals(1, controller.observableMovies.size());
       });
    }

    @Test
    public void testQueryandGenreFilter() {
        Platform.runLater(() -> {
            controller.searchField.setText("Godfather");
            controller.genreComboBox.setValue("DRAMA");
            controller.handleGenreFilter();
            controller.handleSearch();
            assertEquals(1, controller.observableMovies.size());
        });
    }


    @Test
    public void testAllGenresNoQueryAfterFilter(){
        //Test case for filtering all genres without a search query afterwards
        Platform.runLater(() -> {
            controller.genreComboBox.setValue("ACTION"); //Setting genre filter
            controller.handleGenreFilter(); //Performing genre filtering
            controller.searchField.setText("Inception"); //Setting search query
            controller.handleSearch(); //Performing search

            controller.searchField.setText(""); //Clearing search query
            controller.genreComboBox.setValue("ALL"); //Setting genre filter to ALL
            controller.handleGenreFilter(); //Performing genre filtering
            controller.handleSearch(); //Performing search
        });

        Platform.runLater(() -> {
            assertEquals(allMovies,controller.observableMovies); //Verifying all movies all displayed
        });
    }

   /*   @Test
    public void testGenreFilter() {
        //Test case for genre filtering
        Platform.runLater(() -> {
            controller.genreComboBox.setValue("ACTION"); //Setting genre filter
            controller.handleGenreFilter(); //Perfoming genre filter
        });
        for (Movie movie : allMovies) { //Iterating through all movies
            if (movie.getGenres().contains("ACTION")) { //Checking if movie contains genre
                filteredMovies.add(movie); //Adding movie to filtered movie list
            }
        }
        Platform.runLater(() -> //Verifying filtered movies are correct
                assertTrue("Die Listen enthalten nicht dieselben Elemente.",
                filteredMovies.size() == controller.observableMovies.size() &&
                        filteredMovies.containsAll(controller.observableMovies) &&
                        controller.observableMovies.containsAll(filteredMovies))
        );
    }

  @Test
    public void testSortAscendingAndGenreFilter() {
        //Test case for ascending sorting and genre filtering
        Platform.runLater(() -> {
            controller.genreComboBox.setValue("ACTION"); //Setting genre filter
            controller.sortBtn.setText("Sort (asc)"); //Setting sorting to ascending

            controller.handleGenreFilter(); //Performing genre filtering
            controller.handleSort(); //Performing sorting
        });

        for (Movie movie : allMovies) { //Iterating through all movies
            if (movie.getGenres().contains("ACTION")) { //Checking if movie contains genre
                filteredMovies.add(movie); //Adding movie to filtered movie list
            }
        }
        filteredMovies.sort(Comparator.comparing(Movie::getTitle)); //Sorting movies by title

        Platform.runLater(() -> {
            Assertions.assertIterableEquals(filteredMovies, controller.observableMovies); //Verifying sorting is correct
        });
    }*/

    ///Steffi: no loops or if cond within a test

    @Test
    public void testSortAscending() {
        //Test case for scending sorting
        Platform.runLater(() -> {
            controller.sortBtn.setText("Sort (asc)"); //Setting sorting to ascending
            controller.handleSort(); //Performing sorting
                });
        Platform.runLater(() -> {
                    assertEquals("Sort (desc)", controller.sortBtn.getText()); //Verifying sorting is correct
                });
    }

    @Test
    public void testSortDescending() {
        //Test case for descending sorting
        Platform.runLater(() -> {
            controller.sortBtn.setText("Sort (desc)"); //Setting sorting to descending
            controller.handleSort(); //Performing sorting
        });
        Platform.runLater(() -> {
            assertEquals("Sort (asc)", controller.sortBtn.getText()); //Verifying sorting is correct
        });
    }

    @Test
    public void testMovieInitialization() {
        Movie movie = new Movie("1", "Test Movie", "Test Description", Arrays.asList(Genre.ACTION), 2022, 120, "test.jpg", Arrays.asList("Director1"), Arrays.asList("Actor1"), 8.0);

        assertEquals("1", movie.getId());
        assertEquals("Test Movie", movie.getTitle());
        assertEquals("Test Description", movie.getDescription());
        assertEquals(Arrays.asList(Genre.ACTION), movie.getGenres());
        assertEquals(2022, movie.getReleaseYear());
        assertEquals(120, movie.getLengthInMinutes());
        assertEquals("test.jpg", movie.getImgUrl());
        assertEquals(Arrays.asList("Director1"), movie.getDirectors());
        assertEquals(Arrays.asList("Actor1"), movie.getMainCast());
        assertEquals(8.0, movie.getRating(), 0.001);
    }

    @Test
    public void testCompareTo() {
        Movie movie1 = new Movie("1", "Movie A", "", Arrays.asList(Genre.ACTION), 2022, 120, "", Arrays.asList(""), Arrays.asList(""), 8.0);
        Movie movie2 = new Movie("2", "Movie B", "", Arrays.asList(Genre.ACTION), 2022, 120, "", Arrays.asList(""), Arrays.asList(""), 8.0);

        assertTrue(movie1.compareTo(movie2) < 0);
        assertTrue(movie2.compareTo(movie1) > 0);
        assertEquals(0, movie1.compareTo(movie1));
    }

    @Test
    public void testGetAllMovies() {
        MovieAPI movieAPI = new MovieAPI();
        List<Movie> movies = movieAPI.getAllMovies();

        assertNotNull(movies);
        assertFalse(movies.isEmpty());
    }

    @Test
    public void testSearchMovies() {
        MovieAPI movieAPI = new MovieAPI();
        List<Movie> movies = movieAPI.searchMovies("Action", Genre.ACTION, 2022, 8.0);

        assertNotNull(movies);
        assertFalse(movies.isEmpty());
    }

    @AfterAll
    static void done() {
        log.info("All tests are done.");
    }

}



