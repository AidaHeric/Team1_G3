package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.Watchlist;
import at.ac.fhcampuswien.fhmdb.models.WatchlistRepository;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;


// Custom implementation for displaying Movie objects
public class MovieCell extends ListCell<Movie> {
    //UI components
    private final Label title = new Label();
    private final Label detail = new Label();
    //private final VBox layout = new VBox(title, detail);
    private final Label genre = new Label();
    private final Label releaseYear = new Label();
    private final Label rating = new Label();
    private final HBox header = new HBox(title);
    private final HBox year = new HBox(releaseYear);
    private final HBox description = new HBox(detail);
    private final HBox genresAndRating = new HBox(genre, rating);
    private final VBox layout = new VBox(header,year, description, genresAndRating);
    public Button removeButton = new Button("Remove");
    VBox navigationPanel = new VBox();
    public Button navigateButton = new Button("Watchlist");


    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setText(null);
            setGraphic(null);
        } else {
            this.getStyleClass().add("movie-cell");
            title.setText(movie.getTitle());
            releaseYear.setText("Released: " + Integer.toString(movie.getReleaseYear()));
            detail.setText(
                    movie.getDescription() != null
                            ? movie.getDescription()
                            : "No description available"
            );

            genre.setText(movie.getGenres().toString().replaceAll("[\\[\\]]", ""));
            rating.setText(" - Rating: " + movie.getRating());


            //colour scheme
            title.getStyleClass().add("text-yellow");
            releaseYear.getStyleClass().add("text-white");
            detail.getStyleClass().add("text-white");
            rating.getStyleClass().add("text-white");
            genre.getStyleClass().add("text-white");
            layout.setBackground(new Background(new BackgroundFill(Color.web("777"), null, null)));

            // Configure layout
            title.fontProperty().set(title.getFont().font(20));
            //releaseYear.fontProperty().set(title.getFont().font(20));
            detail.setMaxWidth(this.getScene().getWidth() - 30);
            detail.setWrapText(true);
            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(10);
            layout.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);

            navigationPanel.setMinSize(100, 100); // Set minimum size
            navigationPanel.setPadding(new Insets(10)); // Add padding
            navigationPanel.setPrefWidth(200); // Set preferred width
            navigationPanel.setSpacing(10); // Set spacing between elements

            if(navigateButton.getParent() == null){
                navigationPanel.getChildren().add(navigateButton);
                layout.getChildren().add(navigationPanel);
            }

            if(removeButton.getParent() == null){
                navigationPanel.getChildren().add(removeButton);
                layout.getChildren().add(navigationPanel);
            }

            navigateButton.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Watchlist.fxml"));
                    Scene scene = new Scene(loader.load());
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            removeButton.setOnAction(event -> {
                if (movie != null) {
                    try {
                        WatchlistRepository.removeMovieFromWatchlist(new Watchlist(), movie);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
                //Set the graphic of the cell to the layout VBox
                setGraphic(layout);
            }

    }

}

