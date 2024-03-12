package at.ac.fhcampuswien.fhmdb;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HomeControllerTest {
    private HomeController controller;

    @Before
    public void setUp() {
        controller = new HomeController();
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
        assertEquals("Sort (desc)", controller.sortBtn.getText());
        controller.handleSort();
        assertEquals("Sort (asc)", controller.sortBtn.getText());
    }
}
