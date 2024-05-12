package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.*;
import at.ac.fhcampuswien.fhmdb.ui.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;


public class HomeController implements Initializable {

    // API instance to fetch movies
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
    public JFXComboBox<Integer> releaseYearComboBox;

    @FXML
    public JFXComboBox<Double> ratingComboBox;

    @FXML
    public JFXButton sortBtn;

    // List to store all movies and genre-filtered movies
    public List<Movie> allMovies = Movie.initializeMovies();

    public List<Movie> genreFilteredMovies = new ArrayList<>();

    // Observable list of movies for dynamic UI updates
    public ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    private WatchlistRepository watchlistRepository;
    private Watchlist watchlist;

    // Initialize method for the controller
    public void initialize(URL location, ResourceBundle resources) {
        observableMovies = FXCollections.observableArrayList();

        // GET movies from API
        allMovies = Movie.initializeMovies();
        observableMovies.addAll(allMovies);

        movieListView.setItems(observableMovies);


        genreComboBox.getItems().addAll(Genre.values());
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.setOnAction(event -> handleGenreFilter());
        genreComboBox.setValue(Genre.ALL);

        releaseYearComboBox.getItems().add(0);
        releaseYearComboBox.getItems().addAll(allMovies.stream()
                .map(Movie -> Movie.getReleaseYear())
                .distinct()
                .sorted()
                .toList());

        ratingComboBox.getItems().add(0.0);
        ratingComboBox.getItems().addAll((allMovies.stream()
                .map(Movie -> Movie.getRating()))
                .distinct()
                .sorted()
                .toList());


        movieListView.setCellFactory(param -> new MovieCell());
        searchBtn.setOnAction(event -> handleSearch());
        sortBtn.setOnAction(event -> handleSort());

        movieListView.setCellFactory(movieListView -> {
            MovieCell movieCell = new MovieCell();

            movieCell.navigateButton.setOnAction(event -> {
                Movie movie = movieCell.getItem();
                ClickEventHandler<Movie> addToWatchlistHandler = item -> {
                    try {
                        watchlistRepository.addMovieToWatchlist(watchlist, item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
                addToWatchlistHandler.onClick(movie);
            });

            movieCell.removeButton.setOnAction(event -> {
                Movie movie = movieCell.getItem();
                ClickEventHandler<Movie> removeFromWatchlistHandler = item -> {
                    try {
                        watchlistRepository.addMovieToWatchlist(watchlist, item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
                removeFromWatchlistHandler.onClick(movie);
            });
            return movieCell;
        });


    }

    @FXML
    public void handleSearch() {
        String query = searchField.getText().toLowerCase();
        Genre genre = genreComboBox.getValue();
        int year = releaseYearComboBox.getValue();
        double rating = this.ratingComboBox.getValue();

        // Search movies using API and update observable list
        List<Movie> movielist = movieAPI.searchMovies(query,genre, year, rating);

        observableMovies.clear();
        observableMovies.addAll(movielist);
    }

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

    String getMostPopularActor(List<Movie> movies) {
        Map<Object, Long> maincastMap = movies.stream()
                .flatMap(eachMovie -> eachMovie.getMainCast().stream())
                .collect(Collectors.groupingBy(mainCast -> mainCast, Collectors.counting()));

        String mostPopular = "";
        mostPopular = String.valueOf(maincastMap.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(""));
        return mostPopular;
    }

    int getLongestMovieTitle(List<Movie> movies) {
        return movies.stream().mapToInt(eachMovie -> eachMovie.getTitle().length()).max().orElse(0);
    }

    long countMoviesFrom(List<Movie> movies, String director) {
        return movies.stream().filter(eachMovie -> eachMovie.getDirectors().contains(director)).count();
    }

    List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }

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

        observableMovies.addAll(genreFilteredMovies);
    }


}