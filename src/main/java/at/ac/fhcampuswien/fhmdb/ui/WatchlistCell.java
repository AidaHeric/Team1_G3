package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.WatchlistRepository;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class WatchlistCell extends ListCell<Movie> {
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
    VBox navWatchPanel = new VBox();
    public Button removeButton = new Button("Remove");
    WatchlistRepository watchlistRepository;
    public WatchlistCell() throws DatabaseException {
        this.watchlistRepository = new WatchlistRepository();
    }

    ClickEventHandler<Movie> onRemoveButtonClickHandler = (Movie movie) -> {
        WatchlistMovieEntity watchlistMovieEntity = new WatchlistMovieEntity();
        watchlistMovieEntity.setApiID(movie.getId());
        try {
            watchlistRepository.deleteWatchlistMovie(watchlistMovieEntity);
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
            releaseYear.setText("Released: " + movie.getReleaseYear());
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

            removeButton.setOnAction(event -> onRemoveButtonClickHandler.onClick(movie));
            if (!navWatchPanel.getChildren().contains(removeButton)) {
                navWatchPanel.getChildren().add(removeButton);
                if (!layout.getChildren().contains(navWatchPanel)) {
                    layout.getChildren().add(navWatchPanel);
                }
            }

            setGraphic(layout);

            if (removeButton.getParent() == null) {
                navWatchPanel.getChildren().add(removeButton);
                layout.getChildren().add(navWatchPanel);
            }

            removeButton.setOnAction(event -> {
                try {
                    WatchlistMovieEntity watchlistMovieEntity = new WatchlistMovieEntity();
                    watchlistMovieEntity.setApiID(movie.getId());
                    WatchlistRepository watchlistRepo = new WatchlistRepository();
                    watchlistRepo.deleteWatchlistMovie(watchlistMovieEntity);
                    watchlistRepository.deleteWatchlistMovie(watchlistMovieEntity);
                } catch (DatabaseException e) {
                    e.printStackTrace();
                }
            });

            }
        }
    private void messageForUser(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message);
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK);
    }
    }

