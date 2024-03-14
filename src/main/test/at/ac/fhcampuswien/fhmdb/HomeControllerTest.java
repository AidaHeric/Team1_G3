package at.ac.fhcampuswien.fhmdb;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class HomeControllerTest {
    private HomeController controller;

    @Before
    public void setUp() {
        controller = new HomeController();
        Platform.startup(() -> {
        });
        Platform.runLater(() -> {
            controller.searchField = new TextField();
        });
    }

    @Test
    public void testSearchFilter() {
        controller.searchField.setText("Godfather");
        controller.handleSearch();
        assertEquals(1, controller.observableMovies.size());
    }

    @Test
    public void testGenreFilter() {
        controller.genreComboBox.setValue("ACTION");
        controller.handleGenreFilter();
        assertEquals(2, controller.observableMovies.size());
    }

    @Test
    public void testSort() {
        controller.handleSort();
        String currentText = controller.sortBtn.getText();
        assertEquals("Sort (desc)", currentText);
        controller.handleSort();
        assertEquals("Sort (asc)", currentText);
    }
}
