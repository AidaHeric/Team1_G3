package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import javafx.scene.chart.PieChart;

import java.sql.SQLException;

public class DatabaseManager {
    public static final String DB_URL = "jdbc:h2:file:./db/database";
    public static final String username = "username";
    public static final String password = "password";
    public static JdbcConnectionSource connectionSource;
    Dao<MovieEntity, Long> dao;

    private static DatabaseManager instance;
    private DatabaseManager(){                  //singleton, es darf nur einmal eine connection hergestellt werden
        try {
            createConnectionSource();
            dao = DaoManager.createDao(connectionSource, MovieEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void testDB() throws SQLException {
        MovieEntity newMovie = new MovieEntity("1","Titletest", "this test", "genres", 1994, "url", 19, 1);
        dao.create(newMovie);

    }

    public static DatabaseManager getDatabase(){
        if(instance == null){
            instance = new DatabaseManager();
        }

        return instance;
    }

    public static void createConnectionSource() throws SQLException {           //ohne catch, weil ein ausgegebener Fehler hier auch nichts mehr bringt
        connectionSource = new JdbcConnectionSource(DB_URL,username,password);
    }
}
