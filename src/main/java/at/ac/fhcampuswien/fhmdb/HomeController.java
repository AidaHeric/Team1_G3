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
import java.io.IOException;

import at.ac.fhcampuswien.fhmdb.models.MovieAPI;


import static at.ac.fhcampuswien.fhmdb.models.Movie.getAllGenres;

// Controller class for managing the home view of the application

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

    // Observable list of movies for dynamic UI updates
    public ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes


    //Initialize the controller
    @Override
    public void initialize(URL MoviesURL, ResourceBundle resourceBundle) {
        // Add allMovies to observableMovies
        observableMovies.addAll(allMovies);
        genreFilteredMovies.addAll(allMovies);

        // Initialize UI stuff
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

    //Search action
    @FXML
    public void handleSearch() {
        String query = searchField.getText().toLowerCase();

        List<Movie> filteredMovies = genreFilteredMovies.stream()       //Filter already Genre-Filtered Movies
                .filter(movie ->
                        (query.isEmpty() || movie.getTitle().toLowerCase().contains(query) ||
                                (movie.getDescription() != null && movie.getDescription().toLowerCase().contains(query)))
                )
                .distinct()
                .toList();

        observableMovies.clear();
        Platform.runLater(() -> observableMovies.addAll(filteredMovies));
    }

    // Genre filter action
    @FXML
    public void handleGenreFilter() {
        String selectedGenre = genreComboBox.getValue();
        observableMovies.clear();
        genreFilteredMovies.clear();
        if (selectedGenre.equals("ALL")) {                                  //add all movies, if all movies selected
            genreFilteredMovies.addAll(allMovies);
        } else {
            genreFilteredMovies = allMovies.stream()                        //add all movies with selected genre
                    .filter(movie ->
                            movie.getGenres().contains(selectedGenre))
                    .distinct()
                    .collect(Collectors.toList());
        }

        Platform.runLater(() -> observableMovies.addAll(genreFilteredMovies));
    }

    // Sorting action
    @FXML
    public void handleSort() {
        String currentText = sortBtn.getText();                              //sort ascending/descending
        if (currentText.equals("Sort (asc)")) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle));
        } else {
            observableMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
        }
        sortBtn.setText(currentText.equals("Sort (asc)") ? "Sort (desc)" : "Sort (asc)");
    }

    /*@Override
    public static List<Movie> initializeMovies() {
        List<Movie> movies = new ArrayList<>();
        try {
            List<Movie> fetchedMovies = MovieAPI.getAllMovies(); // This method should be in your MovieAPI class
            if (fetchedMovies != null) {
                movies.addAll(fetchedMovies);
            }
        } catch (IOException e) {
            System.err.println("Error fetching movies from API: " + e.getMessage());
        }
        return movies;
    }*/ //Aida auskommentiert wegen MovieAPI

}