package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "watchlist")
public class WatchlistMovieEntity {
    @DatabaseField(id = true)
    private long id;
    @DatabaseField
    private int apiId;

    public WatchlistMovieEntity(long id, int apiId) {
        this.id = id;
        this.apiId = apiId;
    }

    public WatchlistMovieEntity() {
    }

    public int getApiId() {
        return apiId;
    }

    public long getId() {
        return id;
    }
    /*public void setApiID(int apiId) {
        this.apiId =aApiId;
    }*/
}