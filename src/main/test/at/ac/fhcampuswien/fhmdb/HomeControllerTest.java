package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static javafx.application.Platform.runLater;
import static org.junit.Assert.*;


public class HomeControllerTest {
    HomeController controller = new HomeController();

    public List<Movie> allMovies = Movie.initializeMovies();
    List<Movie> filteredMovies = new ArrayList<>();

    @Before
    public void setUp() {
        //controller = new HomeController();
        Platform.startup(() -> {
            controller.searchField = new TextField();
            controller.genreComboBox = new JFXComboBox();
            controller.sortBtn = new JFXButton();
        });
    }

   /* @Test
    public void testSearchFilter() {
        controller.searchField.setText("Godfather");
        controller.handleSearch();
        assertEquals(1, controller.observableMovies.size());
    }*/

    @Test
    public void testAllGenresNoQueryAfterFilter(){
        Platform.runLater(() -> {
            controller.genreComboBox.setValue("ACTION");
            controller.handleGenreFilter();
            controller.searchField.setText("Inception");
            controller.handleSearch();

            controller.searchField.setText("");
            controller.genreComboBox.setValue("ALL");
            controller.handleGenreFilter();
            controller.handleSearch();
        });

        Platform.runLater(() -> {
            assertEquals(allMovies,controller.observableMovies);
        });
    }

    @Test
    public void testGenreFilter() {
        Platform.runLater(() -> {
            controller.genreComboBox.setValue("ACTION");

            controller.handleGenreFilter();
        });
        for (Movie movie : allMovies) {
            if (movie.getGenres().contains("ACTION")) {
                filteredMovies.add(movie);
            }
        }
        Platform.runLater(() -> assertTrue("Die Listen enthalten nicht dieselben Elemente.",
                filteredMovies.size() == controller.observableMovies.size() &&
                        filteredMovies.containsAll(controller.observableMovies) &&
                        controller.observableMovies.containsAll(filteredMovies)));

    }

    @Test
    public void testSortAscendingAndGenreFilter() {
        Platform.runLater(() -> {
            controller.genreComboBox.setValue("ACTION");
            controller.sortBtn.setText("Sort (asc)");

            controller.handleGenreFilter();
            controller.handleSort();
        });

        for (Movie movie : allMovies) {
            if (movie.getGenres().contains("ACTION")) {
                filteredMovies.add(movie);
            }
        }
        filteredMovies.sort(Comparator.comparing(Movie::getTitle));

        Platform.runLater(() -> {
            Assertions.assertIterableEquals(filteredMovies, controller.observableMovies);
        });
    }

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

}
