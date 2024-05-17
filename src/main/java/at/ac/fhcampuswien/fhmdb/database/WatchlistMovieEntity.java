package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "watchlist")
public class WatchlistMovieEntity {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String apiId;

    public WatchlistMovieEntity(long id, String apiId) {
        this.id = id;
        this.apiId = apiId;
    }
    @DatabaseField
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = this.title;
    }

    public WatchlistMovieEntity() {
    }

    public String getApiId() {
        return apiId;
    }

    public long getId() {
        return id;
    }

    /*public String getTitle() {
        return null;
    }*/
    public void setApiID(String apiId) {
        this.apiId = apiId;
    }

    public void setId(long id) {
        this.id = id;
    }
}

