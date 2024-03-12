package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static at.ac.fhcampuswien.fhmdb.models.Movie.getAllGenres;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView<Movie> movieListView;

    @FXML
    public JFXComboBox<String> genreComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

    public final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);

        // initialize UI stuff
        movieListView.setItems(observableMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());

        // Add genre filter items to the combo box
        genreComboBox.getItems().addAll(getAllGenres());
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.setOnAction(actionEvent -> handleGenreFilter());
        // Set event handlers for buttons
        searchBtn.setOnAction(actionEvent -> handleSearch());
        sortBtn.setOnAction(actionEvent -> handleSort());
    }

    @FXML
    public void handleSearch() {
        String query = searchField.getText().toLowerCase();
        String selectedGenre = genreComboBox.getValue();

        List<Movie> filteredMovies = allMovies.stream()
                .filter(movie ->
                        (query.isEmpty() || movie.getTitle().toLowerCase().contains(query) ||
                                (movie.getDescription() != null && movie.getDescription().toLowerCase().contains(query))) &&
                                (selectedGenre == null || movie.getGenres().contains(selectedGenre))
                )
                .collect(Collectors.toList());
        //Test
        System.out.println(genreComboBox.getValue());
        // Update UI on the JavaFX Application Thread
        Platform.runLater(() -> observableMovies.setAll(filteredMovies));
    }
    @FXML
    public void handleGenreFilter() {
        //TODO update method to separate from search method; filter should be multiple choice, deselectable and unselectable
        handleSearch();
    }
    @FXML
    public void handleSort() {
        String currentText = sortBtn.getText();
        if (currentText.equals("Sort (asc)")) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle));
        } else {
            observableMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
        }
        sortBtn.setText(currentText.equals("Sort (asc)") ? "Sort (desc)" : "Sort (asc)");
    }
}