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
import java.util.ArrayList;
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
    public List<Movie> genreFilteredMovies = new ArrayList<>();

    public ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

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

        List<Movie> filteredMovies = genreFilteredMovies.stream()
                .filter(movie ->
                        (query.isEmpty() || movie.getTitle().toLowerCase().contains(query) ||
                                (movie.getDescription() != null && movie.getDescription().toLowerCase().contains(query)))
                )
                .distinct()
                .toList();

        observableMovies.clear();
        Platform.runLater(() -> observableMovies.addAll(filteredMovies));
    }

    @FXML
    public void handleGenreFilter() {
        String selectedGenre = genreComboBox.getValue();
        observableMovies.clear();
        genreFilteredMovies.clear();
        if (selectedGenre.equals("ALL")) {
            genreFilteredMovies.addAll(allMovies);
        } else {
            genreFilteredMovies = allMovies.stream()
                    .filter(movie ->
                            movie.getGenres().contains(selectedGenre))
                    .distinct()
                    .collect(Collectors.toList());
        }

        Platform.runLater(() -> observableMovies.addAll(genreFilteredMovies));
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