package tgo1014.moviedb_demo.network.services;

import retrofit2.Call;
import retrofit2.http.GET;
import tgo1014.moviedb_demo.entities.MovieRequest;

public interface MoviesService {
    @GET("movie/popular")
    Call<MovieRequest> getPopularMoviesList();
}
