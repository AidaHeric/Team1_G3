package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;



public class HomeControllerTest {
    //private HomeController controller;
    HomeController controller = new HomeController();

    public List<Movie> allMovies = Movie.initializeMovies();
    List<Movie> filteredMovies = controller.observableMovies;

    @Before
    public void setUp() {
        controller = new HomeController();
        Platform.startup(() -> {
            controller.searchField = new TextField();
        });
    }

   /* @Test
    public void testSearchFilter() {
        controller.searchField.setText("Godfather");
        controller.handleSearch();
        assertEquals(1, controller.observableMovies.size());
    }*/

    @Test
    public void testGenreFilter() {
        controller.genreComboBox.setValue("ACTION");
        controller.handleGenreFilter();
        filteredMovies = controller.observableMovies;
        for (Movie movie : filteredMovies) {
            assertTrue(movie.getGenres().contains("ACTION"));
        }
    }

    /*@Test
    public void testSort() {
        controller.handleSort();
        String currentText = controller.sortBtn.getText();
        assertEquals("Sort (desc)", currentText);
        controller.handleSort();
        assertEquals("Sort (asc)", currentText);
    }*/

}
