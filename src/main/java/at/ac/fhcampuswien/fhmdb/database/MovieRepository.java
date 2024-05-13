package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class MovieRepository {
    Dao<MovieEntity,Long> dao;

    public MovieRepository() throws DatabaseException {
        this.dao = DatabaseManager.getDatabase().movieDao;
    }

    public List<MovieEntity> getAllMovies() throws DatabaseException {
        try {
            return dao.queryForAll();
        } catch(SQLException e){
            System.out.println("Error getting all movies:" + e.getMessage());
            return null;
        }
    }

    public int removeAll() throws DatabaseException{                                                             ////TODO: testen
        try {
            DeleteBuilder<MovieEntity, Long> deleteBuilder = dao.deleteBuilder();
            return dao.delete(deleteBuilder.prepare());
        } catch (SQLException e) {
            System.out.println("Error removing all movies: " + e.getMessage());
            return 0;
        }
    }

    public MovieEntity getMovie(Long movieId) throws DatabaseException {               ////TODO: testen
        try{
            return dao.queryForId(movieId);
        } catch (SQLException e) {
            System.out.println("Error getting movie with ID " + movieId + ": " + e.getMessage());
            return null;
        }
    }

    public int addAllMovies(List<Movie> movies) throws DatabaseException {          //TODO: testen
        List<MovieEntity> movieEntities = new ArrayList<>();
        try {
            for (Movie movie : movies) {
                movieEntities.add(movieToMovieEntity(movie));
            }
            dao.create(movieEntities);
            return movieEntities.size();
        } catch (SQLException e) {
            System.out.println("Error, couldn't add the movies: " + e.getMessage());
            return 0;
        }
    }

    public String genresToString(List<Genre> genres) {
        return genres.stream()
                .map(Genre::name)
                .collect(Collectors.joining(","));
    }

    public MovieEntity movieToMovieEntity(Movie movie){
        String genresString = genresToString(movie.getGenres());
        return new MovieEntity(movie.getId(),movie.getTitle(),movie.getDescription(),genresString,movie.getReleaseYear(),movie.getImgUrl(),movie.getLengthInMinutes(),movie.getRating());
    }

    public Movie toMovies(MovieEntity movieEntity) {

        return new Movie(String.valueOf(movieEntity.getId()), movieEntity.getTitle(), movieEntity.getDescription(),
                convertGenresToList(movieEntity.getGenres()), movieEntity.getReleaseYear(), movieEntity.getLengthInMinutes(),
                movieEntity.getImgUrl(),movieEntity.getRating());
    }

    public List<Genre> convertGenresToList(String genresStr) {
        return Arrays.stream(genresStr.split(","))
                .map(String::trim)
                .map(genreStr -> {
                    try {
                        return Genre.valueOf(genreStr.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        // Log this error or handle it as per your requirement
                        System.err.println("Invalid genre: " + genreStr);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


}
