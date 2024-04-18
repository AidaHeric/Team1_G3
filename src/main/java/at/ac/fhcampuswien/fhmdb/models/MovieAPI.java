package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import java.io.IOException;
import java.util.*;

import com.google.gson.Gson;
import okhttp3.*;

public class MovieAPI {

    public static final String MoviesURL = "https://prog2.fh-campuswien.ac.at/movies";

    private final OkHttpClient client;
    private final Gson gson;

    public MovieAPI() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    public List<Movie> getAllMovies() {
        try {
            Request request = new Request.Builder()
                    .url(MoviesURL)
                    .build();
            return executeRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Movie> searchMovies(String query, String genre) {
        try {
            HttpUrl.Builder URLBuilder = HttpUrl.parse(MoviesURL).newBuilder();
            if (query != null && !query.isEmpty()) {
                URLBuilder.addQueryParameter("query", query);
            }
            if (genre != null && !genre.isEmpty()) {
                URLBuilder.addQueryParameter("genre", genre);
            }
            String URL = URLBuilder.build().toString();
            Request request = new Request.Builder()
                    .url(URL)
                    .build();
            return executeRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<Movie> executeRequest (Request request) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response); //IOException wird ausgegeben
            }
            String jsonResponse = response.body().string();
            Movie movieList = gson.fromJson(jsonResponse, Movie.class);
            return movieList.getMovies; //TODO
        }
    }

}
