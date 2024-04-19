package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextField;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
            controller.genreComboBox.setValue(Genre.valueOf("DRAMA"));
            controller.handleGenreFilter();
            controller.handleSearch();
            assertEquals(1, controller.observableMovies.size());
        });
    }


    @Test
    public void testAllGenresNoQueryAfterFilter(){
        //Test case for filtering all genres without a search query afterwards
        Platform.runLater(() -> {
            controller.genreComboBox.setValue(Genre.valueOf("ACTION")); //Setting genre filter
            controller.handleGenreFilter(); //Performing genre filtering
            controller.searchField.setText("Inception"); //Setting search query
            controller.handleSearch(); //Performing search

            controller.searchField.setText(""); //Clearing search query
            controller.genreComboBox.setValue(Genre.valueOf("ALL")); //Setting genre filter to ALL
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
        List<Movie> movies = movieAPI.searchMovies("", Genre.ACTION, 2018, 8.0);

        assertNotNull(movies);
        assertFalse(movies.isEmpty());
    }

    public List<Movie> createMovieList(){
        ArrayList<Genre> movie1Genres = new ArrayList();                //dummy movies created by chatGPT
        movie1Genres.add(Genre.ANIMATION);
        movie1Genres.add(Genre.FAMILY);
        ArrayList<String> movie1Directors = new ArrayList<>();
        movie1Directors.add("Hermann");
        ArrayList<String> movie1Writers = new ArrayList<>();
        movie1Writers.add("Hermann");
        ArrayList<String> movie1Actors = new ArrayList<>();
        movie1Actors.add("Hermann");
        Movie movie1 = new Movie("one","dasIstDerErsteFilm","Hermanns erster Film.", movie1Genres,
                2024, 120, "http:/image/.com/", movie1Directors,movie1Actors,1.2);

        ArrayList<Genre> movie2Genres = new ArrayList<>();
        movie2Genres.add(Genre.ACTION);
        movie2Genres.add(Genre.THRILLER);
        ArrayList<String> movie2Directors = new ArrayList<>();
        movie2Directors.add("Hermann");
        movie2Directors.add("Klaus");
        ArrayList<String> movie2Writers = new ArrayList<>();
        movie2Writers.add("Maria");
        ArrayList<String> movie2Actors = new ArrayList<>();
        movie2Actors.add("Hermann");
        movie2Actors.add("Julia");
        Movie movie2 = new Movie("two", "DieNachtDerEntscheidung", "Ein packender Actionthriller.", movie2Genres,
                2023, 140, "http:/image/.com/", movie2Directors, movie2Actors, 3.5);

        ArrayList<Genre> movie3Genres = new ArrayList<>();
        movie3Genres.add(Genre.DRAMA);
        movie3Genres.add(Genre.HISTORY);
        ArrayList<String> movie3Directors = new ArrayList<>();
        movie3Directors.add("Franziska");
        ArrayList<String> movie3Writers = new ArrayList<>();
        movie3Writers.add("Klaus");
        movie3Writers.add("Maria");
        ArrayList<String> movie3Actors = new ArrayList<>();
        movie3Actors.add("Julia");
        movie3Actors.add("Franz");
        Movie movie3 = new Movie("three", "Tage des Ruhms und des Verlusts", "Eine tiefgreifende historische Erzählung.", movie3Genres,
                2019, 165, "http:/image/.com/", movie3Directors, movie3Actors, 4.8);

        ArrayList<Genre> movie4Genres = new ArrayList<>();
        movie4Genres.add(Genre.COMEDY);
        movie4Genres.add(Genre.ROMANCE);
        ArrayList<String> movie4Directors = new ArrayList<>();
        movie4Directors.add("Klaus");
        ArrayList<String> movie4Writers = new ArrayList<>();
        movie4Writers.add("Franziska");
        ArrayList<String> movie4Actors = new ArrayList<>();
        movie4Actors.add("Franz");
        movie4Actors.add("Maria");
        Movie movie4 = new Movie("four", "Lachende Herzen", "Eine romantische Komödie, die Herzen schmelzen lässt.", movie4Genres,
                2021, 95, "http:/image/.com/", movie4Directors, movie4Actors, 2.7);

        ArrayList<Genre> movie5Genres = new ArrayList<>();
        movie5Genres.add(Genre.SCIENCE_FICTION);
        movie5Genres.add(Genre.ADVENTURE);
        ArrayList<String> movie5Directors = new ArrayList<>();
        movie5Directors.add("Franziska");
        movie5Directors.add("Hermann");
        ArrayList<String> movie5Writers = new ArrayList<>();
        movie5Writers.add("Klaus");
        ArrayList<String> movie5Actors = new ArrayList<>();
        movie5Actors.add("Julia");
        movie5Actors.add("Hermann");
        Movie movie5 = new Movie("five", "Sterne über uns", "Ein Abenteuer, das über die Grenzen unserer Welt hinausgeht.", movie5Genres,
                2022, 130, "http:/image/.com/", movie5Directors, movie5Actors, 3.9);

        ArrayList<Genre> movie6Genres = new ArrayList<>();
        movie6Genres.add(Genre.FANTASY);
        movie6Genres.add(Genre.MYSTERY);
        ArrayList<String> movie6Directors = new ArrayList<>();
        movie6Directors.add("Klaus");
        ArrayList<String> movie6Writers = new ArrayList<>();
        movie6Writers.add("Maria");
        movie6Writers.add("Franziska");
        ArrayList<String> movie6Actors = new ArrayList<>();
        movie6Actors.add("Franz");
        movie6Actors.add("Hermann");
        Movie movie6 = new Movie("six", "Das Geheimnis der alten Mühle", "Ein mystisches Abenteuer in einer Welt voller Wunder.", movie6Genres,
                2018, 108, "http:/image/.com/", movie6Directors, movie6Actors, 4.4);

        ArrayList<Movie> createMovieList = new ArrayList<>();
        createMovieList.add(movie1);
        createMovieList.add(movie2);
        createMovieList.add(movie3);
        createMovieList.add(movie4);
        createMovieList.add(movie5);
        createMovieList.add(movie6);
        return createMovieList;
    }

    @Test
    public void testGetMostPopularActor(){
        assertEquals("Hermann", controller.getMostPopularActor(createMovieList()));
    }

    @Test
    public void testGetLongestMovieTitleLength(){
        assertEquals(31,controller.getLongestMovieTitle(createMovieList()));
    }

    @Test
    public void testCountMoviesFromDirector(){
        String chosenDirector = "Klaus";
        long numberOfMovies = controller.countMoviesFrom(createMovieList(),"Klaus");
        assertEquals(3,numberOfMovies);
    }

    @Test
    public void testGetMoviesBetweenYears2018and2020(){
        assertEquals(2,controller.getMoviesBetweenYears(createMovieList(),2018,2020).size());
    }

    @Test
    public void testGetMoviesBetweenYears2020and2024(){
        assertEquals(4,controller.getMoviesBetweenYears(createMovieList(),2020,2024).size());
    }





    /*@Test
    public void testGetMostPopularActorTwoSameOften(){
        createMovieList().add(new Movie("seven", "Das neue Abenteuer", "Ein weiteres episches Erlebnis.", Arrays.asList(Genre.ADVENTURE), 2024, 110, "http:/image.com/seven", Arrays.asList("Franz"), Arrays.asList("Franz"),
                3.2));
        assertEquals("Franz", controller.getMostPopularActor(createMovieList()));
    }*/

   /* @AfterAll
    static void done() {
        log.info("All tests are done.");
    }*/

}



