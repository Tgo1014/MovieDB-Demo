package tgo1014.moviedb_demo.network.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import tgo1014.moviedb_demo.entities.Movie;
import tgo1014.moviedb_demo.entities.MovieRequest;

public interface MoviesService {
    @GET("movie/popular")
    Call<MovieRequest> getPopularMoviesList();

    @GET("genre/{genre_id}/movies")
    Call<MovieRequest> getMoviesByGenre(@Path("genre_id") int genre_id);

    @GET("movie/{movie_id}")
    Call<Movie> getMovieDetails(@Path("movie_id") int movie_id);
}
