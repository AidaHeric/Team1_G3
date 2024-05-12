package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.database.MovieEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import javafx.scene.chart.PieChart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MovieRepository {
    Dao<MovieEntity,Long> dao;

    public MovieRepository(){
        this.dao = DatabaseManager.getDatabase().dao;
    }

    List<MovieEntity> getAllMovies() throws SQLException {
        return dao.queryForAll();
    }

    int removeAll(){
        try {
            DeleteBuilder<MovieEntity, Long> deleteBuilder = dao.deleteBuilder();
            return dao.delete(deleteBuilder.prepare());
        } catch (SQLException e) {
            System.err.println("Error removing all movies: " + e.getMessage());
            return 0;
        }
    }

    MovieEntity getMovie(Long movieId){
        try {
            return dao.queryForId(movieId);
        } catch (SQLException e) {
            System.err.println("Error retrieving movie with ID " + movieId + ": " + e.getMessage());
            return null;
        }
    }

    public int addAllMovies(List<Movie> movies) throws SQLException {
        List<MovieEntity> movieEntities = new ArrayList<>();
        for(Movie movie: movies){
            movieEntities.add(movieToMovieEntity(movie));
        }
        dao.create(movieEntities);
        return movieEntities.size();
    }

    public static String genresToString(List<Genre> genres) {
        return genres.stream()
                .map(Genre::name)
                .collect(Collectors.joining(","));
    }

    private MovieEntity movieToMovieEntity(Movie movie){
        String genresString = genresToString(movie.getGenres());
        return new MovieEntity(movie.getId(),movie.getTitle(),movie.getDescription(),genresString,movie.getReleaseYear(),movie.getImgUrl(),movie.getLengthInMinutes(),movie.getRating());
    }


}
