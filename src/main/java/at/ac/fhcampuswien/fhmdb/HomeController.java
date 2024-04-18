package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.Genre;
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
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;

import at.ac.fhcampuswien.fhmdb.models.MovieAPI;


public class HomeController implements Initializable {

    MovieAPI movieAPI = new MovieAPI();
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView<Movie> movieListView;

    @FXML
    public JFXComboBox<Genre> genreComboBox;

    @FXML
    public TextField releaseYear;

    @FXML
    public TextField rating;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

    public List<Movie> genreFilteredMovies = new ArrayList<>();

    // Observable list of movies for dynamic UI updates
    public ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes


    public void initialize(URL location, ResourceBundle resources) {
        observableMovies = FXCollections.observableArrayList();

        //MovieAPI movieAPI = new MovieAPI();
        //List<Movie> movies = movieAPI.getAllMovies();

        observableMovies.addAll(allMovies);

        movieListView.setItems(observableMovies);


        genreComboBox.getItems().addAll(Genre.values());
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.setOnAction(event -> handleGenreFilter());
        genreComboBox.setValue(Genre.ALL);

        movieListView.setCellFactory(param -> new MovieCell());
        searchBtn.setOnAction(event -> handleSearch());
        sortBtn.setOnAction(event -> handleSort());

    }

    //Search action
    @FXML
    public void handleSearch() {
        String query = searchField.getText().toLowerCase();
        Genre genre = genreComboBox.getValue();

        int year = releaseYear.getText().isEmpty() ? 0 : Integer.parseInt(releaseYear.getText());
        double rating = this.rating.getText().isEmpty() ? 0 : Double.parseDouble(this.rating.getText());

        List<Movie> filteredMovies = genreFilteredMovies.stream()       //Filter already Genre-Filtered Movies
                .filter(movie ->
                        (query.isEmpty() || movie.getTitle().toLowerCase().contains(query) ||
                                (movie.getDescription() != null && movie.getDescription().toLowerCase().contains(query))) &&
                                (genre.equals(Genre.ALL) || movie.getGenres().contains(genre)) &&
                                (year == 0 || movie.getReleaseYear() == year) &&
                                (rating == 0 || movie.getRating() == rating)
                )
                .distinct()
                .toList();

        observableMovies.clear();
        Platform.runLater(() -> observableMovies.addAll(filteredMovies));
    }

    // Genre filter action
    @FXML
    public void handleGenreFilter() {
        Genre selectedGenre = genreComboBox.getValue();

        observableMovies.clear();
        genreFilteredMovies.clear();
        if (selectedGenre.equals(Genre.ALL)) {                                  //add all movies, if all movies selected
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

    String getMostPopularActor(List<Movie> movies){
        Map<Object, Long> maincastMap = movies.stream()
                .flatMap(eachMovie -> eachMovie.getMainCast().stream())
                .collect(Collectors.groupingBy(mainCast -> mainCast, Collectors.counting()));

        String mostPopular = "";
        mostPopular = String.valueOf(maincastMap.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey));
        return mostPopular;
    }

    int getLongestMovieTitle(List<Movie> movies){
      return movies.stream().mapToInt(eachMovie -> eachMovie.getTitle().length()).max().orElse(0);
    }

    long countMoviesFrom(List<Movie> movies, String director){
        return movies.stream().filter(eachMovie -> eachMovie.getDirectors().contains(director)).count();
    }

    List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear){
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }




}