package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.DatabaseManager;
import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

// Main application
public class FhmdbApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        //Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        // Create a scene witht he loaded FXML content
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        // Add CSS styles to the scene
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        // Set title of the stage
        stage.setTitle("FHMDb");
        // Set scene to the stage
        stage.setScene(scene);
        // Display the stage
        stage.show();

        MovieRepository movieRepository = new MovieRepository();
        MovieAPI movieAPI = new MovieAPI();
        if(movieAPI.getAllMovies()!=null){
            movieRepository.addAllMovies(movieAPI.getAllMovies());
        }



        //DatabaseManager.getDatabase().testDB();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch();

    }
}