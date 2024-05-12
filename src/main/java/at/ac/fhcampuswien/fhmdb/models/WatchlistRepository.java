package at.ac.fhcampuswien.fhmdb.models;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;


public class WatchlistRepository {
    private Dao<Watchlist, Integer> watchlistDao;
    private Dao<Movie, Integer> movieDao;

    public WatchlistRepository(ConnectionSource connectionSource) {
        try {
            watchlistDao = DaoManager.createDao(connectionSource, Watchlist.class);
            movieDao = DaoManager.createDao(connectionSource, Movie.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMovieToWatchlist(Watchlist watchlist, Movie movie) {
        try {
            watchlist.getMovies().add(movie);
            watchlistDao.update(watchlist);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeMovieFromWatchlist(Watchlist watchlist, Movie movie) {
        try {
            watchlist.getMovies().remove(movie);
            watchlistDao.update(watchlist);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


