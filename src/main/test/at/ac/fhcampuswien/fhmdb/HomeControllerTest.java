package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextField;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;


public class HomeControllerTest {
    HomeController controller = new HomeController();

    private static boolean toolkitInitialized = false;

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
        Movie movie1 = new Movie("one","das Ist Der ErsteFilm","Hermanns erster Film.", movie1Genres,
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
        Movie movie2 = new Movie("two", "Die Nacht Der Entscheidung", "Ein packender Actionthriller.", movie2Genres,
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

    @Before
    public void setUp() {                                                                   //by ChatGPT
        controller = new HomeController();
        //Initializing the JavaFX platform
        if(!toolkitInitialized){
            Platform.startup(() -> {
                new JFXPanel();
                controller.searchField = new TextField();
                controller.genreComboBox = new JFXComboBox();
                controller.sortBtn = new JFXButton();
            });
            toolkitInitialized = true;
        }
    }

    //Test case for ascending sorting
    @Test
    public void testSortAscending() {
        Platform.runLater(() -> {
            controller.sortBtn.setText("Sort (asc)");
            controller.handleSort();
                });
        Platform.runLater(() -> {
                    assertEquals("Sort (desc)", controller.sortBtn.getText());
                });
    }

    //Test case for descending sorting
    @Test
    public void testSortDescending() {
        Platform.runLater(() -> {
            controller.sortBtn.setText("Sort (desc)");
            controller.handleSort();
        });
        Platform.runLater(() -> {
            assertEquals("Sort (asc)", controller.sortBtn.getText());
        });
    }

    //Test for Get-Method getId
    @Test
    public void testMovieInitializationGetId() {
        Movie movie = createMovieList().get(0);
        assertEquals("one", movie.getId());
    }

    //Test for Get-Method getTitle
    @Test
    public void testMovieInitializationGetTitle() {
        Movie movie = createMovieList().get(1);
        assertEquals("Die Nacht Der Entscheidung", movie.getTitle());
    }

    //Testing if most popular actor is returned
    @Test
    public void testGetMostPopularActor(){
        assertEquals("Hermann", controller.getMostPopularActor(createMovieList()));
    }

    //Testing if longest movie is found and correct int is returned
    @Test
    public void testGetLongestMovieTitleLength(){
        assertEquals(31,controller.getLongestMovieTitle(createMovieList()));
    }

    //Test for amount of movies from director Klaus
    @Test
    public void testCountMoviesFromDirectorKlaus(){
        String chosenDirector = "Klaus";
        long numberOfMovies = controller.countMoviesFrom(createMovieList(),chosenDirector);
        assertEquals(3,numberOfMovies);
    }

    //Test for amount of movies from director Franziska
    @Test
    public void testCountMoviesFromDirectorFranziska(){
        String chosenDirector = "Franziska";
        long numberOfMovies = controller.countMoviesFrom(createMovieList(),chosenDirector);
        assertEquals(2,numberOfMovies);
    }

    //Test to get correct amount of movies between 2018 - 2020
    @Test
    public void testGetMoviesBetweenYears2018and2020(){
        assertEquals(2,controller.getMoviesBetweenYears(createMovieList(),2018,2020).size());
    }

    //Test to get correct amount of movies between 2020 - 2024
    @Test
    public void testGetMoviesBetweenYears2020and2024(){
        assertEquals(4,controller.getMoviesBetweenYears(createMovieList(),2020,2024).size());
    }


}



