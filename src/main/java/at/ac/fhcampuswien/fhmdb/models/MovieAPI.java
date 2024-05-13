package at.ac.fhcampuswien.fhmdb.models;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import at.ac.fhcampuswien.fhmdb.exceptions.MovieAPIException;
import com.google.gson.Gson;
import okhttp3.*;
import com.google.gson.reflect.TypeToken;

public class MovieAPI {

    //Base URL for the API
    public static final String MoviesURL = "https://prog2.fh-campuswien.ac.at/movies";

    //Instance for making HTTP requests
    private final OkHttpClient client;

    //Gson instance for JSON serialization/deserialization
    private final Gson gson;

    //Constructor to initialize client and gson
    public MovieAPI() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    //To fetch all movies from the API
    public List<Movie> getAllMovies() {
        try {
            //Build the HTTP request with the MoviesURL
            Request request = new Request.Builder()
                    .url(MoviesURL)
                    .header("User-Agent", "http.agent") //Set the User-Agent header
                    .build();
            //Execute the request and return the result
            return executeRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>(); //Return an empty list in case of an error
        }
    }

    //Search movies based on the query, genre, release year and rating
    public List<Movie> searchMovies(String query, Genre genre, int releaseYear, double rating) throws MovieAPIException {
        try {
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

            //Print the constructed URL
            System.out.println(URL);

            //Build the HTTP request with the constructed URL
            Request request = new Request.Builder()
                    .url(URL)
                    .header("User-Agent", "http.agent") //Set the User-Agent header
                    .build();
            //Execute the request and return the result
            return executeRequest(request);
        } catch (IOException e) {
            throw new MovieAPIException("Error while searching movies", e);
        }
    }

    //To execute HTTP request and parse the response into a list of Movie objects
    private List<Movie> executeRequest (Request request) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            //Check if the response is successful
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }
            //Read the JSON response as a string
            String jsonResponse = response.body().string();
            //Define the type of the list using Gsons TypeToken
            Type movieListType = new TypeToken<ArrayList<Movie>>(){}.getType();
            //Parse the JSON response into a list of Movie objects
            List<Movie> movieList = gson.fromJson(jsonResponse, movieListType);
            //Return the list
            return movieList;
        }
    }

}