package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.MovieEntity;
import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import at.ac.fhcampuswien.fhmdb.ui.WatchlistCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WatchlistController {
    @FXML
    private JFXListView<Movie> movieListView;

    @FXML
    public JFXButton homeBtn;

    @FXML
    public JFXButton watchBtn;

    public void initialize() throws DatabaseException {

        MovieRepository movieRepository = new MovieRepository();
        //movieListView.setCellFactory(param -> new WatchlistCell());
        List<Movie> watchlistMovies = new ArrayList<>();
        List<WatchlistMovieEntity> watchlistList = fetchWatchlistMovies();
        for (WatchlistMovieEntity watchlistmovieEntity : watchlistList){
            MovieEntity movieEntity = movieRepository.getMovie(watchlistmovieEntity.getApiId());
            Movie movie = movieRepository.toMovies(movieEntity);
            watchlistMovies.add(movie);
        }


        // Convert the list to an ObservableList
        ObservableList<Movie> observableList = FXCollections.observableArrayList(watchlistMovies);

        // Set the items of the JFXListView
        movieListView.setItems(observableList);

        // Set the cell factory of the JFXListView
        movieListView.setCellFactory(param -> {
            try {
                return new WatchlistCell();
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
        });


        //Navigation

        homeBtn.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 890, 620);
                scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private List<WatchlistMovieEntity> fetchWatchlistMovies() throws DatabaseException {

        WatchlistRepository watchlistRepository = new WatchlistRepository();
        return watchlistRepository.getAllWatchlistMovies();
    }
}