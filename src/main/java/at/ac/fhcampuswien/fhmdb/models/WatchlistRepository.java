package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.database.DatabaseManager;
import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.util.*;

import java.sql.SQLException;

import static at.ac.fhcampuswien.fhmdb.database.DatabaseManager.connectionSource;


public class WatchlistRepository {
    Dao<WatchlistMovieEntity, Long> watchlistDao;

    public WatchlistRepository() throws DatabaseException {
        this.watchlistDao = DatabaseManager.getDatabase().watchlistDao;
    }

    public List<WatchlistMovieEntity> getAllWatchlistMovies() throws DatabaseException {
        try {
            return watchlistDao.queryForAll();
        } catch (SQLException e) {
            throw new DatabaseException("Error while getting all watchlist movies.", e);
        }
    }

    public void addWatchlistMovie(WatchlistMovieEntity watchlistMovie) throws DatabaseException {
        try {
            watchlistDao.update(watchlistMovie);
        } catch (SQLException e) {
            throw new DatabaseException("Error while adding watchlist movie.", e);
        }
    }

    public void deleteWatchlistMovie(WatchlistMovieEntity watchlistMovie) throws DatabaseException {
        try {
            watchlistDao.delete(watchlistMovie);
        } catch (SQLException e) {
            throw new DatabaseException("Error while deleting watchlist movie.", e);
        }
    }

}


