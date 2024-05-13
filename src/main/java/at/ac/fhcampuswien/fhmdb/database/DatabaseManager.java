package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseManager {
    public static final String DB_URL = "jdbc:h2:file:./db/database";
    public static final String username = "username";
    public static final String password = "password";
    public static JdbcConnectionSource connectionSource;
    Dao<MovieEntity, Long> movieDao;
    Dao<WatchlistMovieEntity, Long> watchlistDao;

    private static DatabaseManager instance;
    private DatabaseManager() throws DatabaseException {                  //singleton, es darf nur einmal eine connection hergestellt werden
       try {
           createConnectionSource();
           movieDao = DaoManager.createDao(connectionSource, MovieEntity.class);         //dao and table for db created
           watchlistDao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
           TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
           TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
       } catch (SQLException e) {
            throw new DatabaseException("Failed to initialize the database",e);
        }
    }

    /*public void testDB() throws SQLException {
        MovieEntity newMovie = new MovieEntity("1","Titletest", "this test", "genres", 1994, "url", 19, 1);
        dao.create(newMovie);

    }*/

    public static DatabaseManager getDatabase() throws DatabaseException {      //only create instance, if first one
        if(instance == null){
            instance = new DatabaseManager();
        }

        return instance;
    }

    public static void createConnectionSource() throws SQLException {           //actual connection to database
        connectionSource = new JdbcConnectionSource(DB_URL,username,password);   //ohne catch, weil ein ausgegebener Fehler hier auch nichts mehr bringt
    }
}
