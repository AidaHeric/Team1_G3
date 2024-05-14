package at.ac.fhcampuswien.fhmdb.models;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import at.ac.fhcampuswien.fhmdb.exceptions.MovieAPIException;
import com.google.gson.Gson;
import okhttp3.*;
import com.google.gson.reflect.TypeToken;

public class MovieAPI {

    public static final String MoviesURL = "https://prog2.fh-campuswien.ac.at/movies";

    private final OkHttpClient client;

    private final Gson gson;

    public MovieAPI() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    //To fetch all movies from the API
    public List<Movie> getAllMovies() throws MovieAPIException {
        //Build the HTTP request with the MoviesURL
        Request request = new Request.Builder()
                .url(MoviesURL)
                .header("User-Agent", "http.agent") //Set the User-Agent header
                .build();
        //Execute the request and return the result
        return executeRequest(request);
    }

    //Search movies based on the query, genre, release year and rating
    public List<Movie> searchMovies(String query, Genre genre, int releaseYear, double rating) throws MovieAPIException {
        //Build the URL with query parameters based on the provided parameters
        HttpUrl.Builder URLBuilder = HttpUrl.parse(MoviesURL).newBuilder();
        if (query != null && !query.isEmpty()) {
            URLBuilder.addQueryParameter("query", query);
        }
        if (genre != null && genre != Genre.ALL) {
            URLBuilder.addQueryParameter("genre", genre.toString());
        }
        if (releaseYear != 0) {
            URLBuilder.addQueryParameter("releaseYear", String.valueOf(releaseYear));
        }
        if (rating != 0.0) {
            URLBuilder.addQueryParameter("ratingFrom", String.valueOf(rating));
        }
        String URL = URLBuilder.build().toString();

        //Build the HTTP request with the constructed URL
        Request request = new Request.Builder()
                .url(URL)
                .header("User-Agent", "http.agent") //Set the User-Agent header
                .build();
        //Execute the request and return the result
        return executeRequest(request);
    }

    //To execute HTTP request and parse the response into a list of Movie objects
    private List<Movie> executeRequest (Request request) throws MovieAPIException {
        try (Response response = client.newCall(request).execute()) {
            //Read the JSON response as a string
            String jsonResponse = response.body().string();
            //Define the type of the list using Gsons TypeToken
            Type movieListType = new TypeToken<ArrayList<Movie>>(){}.getType();
            //Parse the JSON response into a list of Movie objects
            List<Movie> movieList = gson.fromJson(jsonResponse, movieListType);
            return movieList;
        } catch (IOException e) {
            throw new MovieAPIException("Unexpected response code: " + e);
        }
    }

}