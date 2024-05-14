package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.DatabaseManager;
import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

// Main application
public class FhmdbApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, DatabaseException {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("FHMDb");
        stage.setScene(scene);
        stage.show();

        MovieRepository movieRepository = new MovieRepository();
        MovieAPI movieAPI = new MovieAPI();
        DatabaseManager.getDatabase();
        if (movieAPI.getAllMovies() != null) {
            movieRepository.removeAll();
            movieRepository.addAllMovies(movieAPI.getAllMovies());
        }
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch();

    }
}