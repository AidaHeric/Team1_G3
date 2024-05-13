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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;


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
    Button addButton = new Button("Watchlist");


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



            if (addButton.getParent() == null) {
                navigationPanel.getChildren().add(addButton);
                layout.getChildren().add(navigationPanel);
            }
            addButton.setOnAction(event -> {
                if (movie != null) {
                    try {
                        WatchlistMovieEntity watchlistMovieEntity = new WatchlistMovieEntity();
                        MovieRepository movieRepository = new MovieRepository();
                        MovieEntity movieEntity = movieRepository.movieToMovieEntity(movie);
                        watchlistMovieEntity.setApiID(movie.getId());  // Die Datenbank-ID von MovieEntity setzen
                        watchlistMovieEntity.setId(movieEntity.getId());   // Die externe API-ID setzen

                        WatchlistRepository watchlistRepo = new WatchlistRepository();
                        watchlistRepo.addWatchlistMovie(watchlistMovieEntity);
                    } catch (DatabaseException e) {
                        e.printStackTrace();
                    }
                }
            });
            //Set the graphic of the cell to the layout VBox
            setGraphic(layout);
        }

    }


}

