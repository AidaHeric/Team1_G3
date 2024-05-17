package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.database.MovieEntity;
import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.WatchlistRepository;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


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
    VBox navigationPanel = new VBox();
    Button addButton = new Button("Watchlist");
    WatchlistRepository watchlistRepository;
    ClickEventHandler<Movie> onAddButtonClickHandler = (Movie movie) -> {
        WatchlistMovieEntity watchlistMovieEntity = new WatchlistMovieEntity();
        watchlistMovieEntity.setApiID(movie.getId());
        try {
            watchlistRepository.addWatchlistMovie(watchlistMovieEntity);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    };

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
            layout.alignmentProperty().set(Pos.CENTER_LEFT);

            addButton.setOnAction(event -> onAddButtonClickHandler.onClick(movie));
            if (!navigationPanel.getChildren().contains(addButton)) {
                navigationPanel.getChildren().add(addButton);
                layout.getChildren().add(navigationPanel);
            }

            setGraphic(layout);


            if (addButton.getParent() == null) {
                navigationPanel.getChildren().add(addButton);
                layout.getChildren().add(navigationPanel);
            }
            addButton.setOnAction(event -> {
                try {
                    WatchlistMovieEntity watchlistMovieEntity = new WatchlistMovieEntity();
                    watchlistMovieEntity.setApiID(movie.getId());  // Die Datenbank-ID von MovieEntity setzen
                    WatchlistRepository watchlistRepo = new WatchlistRepository();
                    watchlistRepo.addWatchlistMovie(watchlistMovieEntity);
                    messageForUser(Alert.AlertType.INFORMATION, "Movie added to watchlist");
                } catch (DatabaseException e) {
                    e.printStackTrace();
                    messageForUser(Alert.AlertType.ERROR, "Failed to add movie to watchlist: " + e.getMessage());
                }
            });
                //Set the graphic of the cell to the layout VBox
                setGraphic(layout);
            }

        }
    private void messageForUser(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message);
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK);
    }

    }

