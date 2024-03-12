package at.ac.fhcampuswien.fhmdb;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {
    private HomeController controller;

    @Before
    public void setUp() {
        controller = new HomeController();
    }

    @Test
    public void testSearchFilter() {
        // Add your test cases for search filtering
        // For example:
        controller.searchField.setText("Godfather");
        controller.handleSearch();
        assertEquals(1, controller.observableMovies.size());
    }

    @Test
    public void testGenreFilter() {
        // Add your test cases for genre filtering
        // For example:
        controller.genreComboBox.setValue("ACTION");
        controller.handleSearch();
        assertEquals(2, controller.observableMovies.size());
    }

    @Test
    public void testSort() {
        // Add your test cases for sorting
        // For example:
        controller.handleSort();
        assertEquals("Sort (desc)", controller.sortBtn.getText());
        controller.handleSort();
        assertEquals("Sort (asc)", controller.sortBtn.getText());
    }
}