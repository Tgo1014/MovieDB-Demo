package tgo1014.moviedb_demo.network.services;

import retrofit2.Call;
import retrofit2.http.GET;
import tgo1014.moviedb_demo.entities.requests.GenreRequest;

public interface GenresService {
    @GET("genre/movie/list")
    Call<GenreRequest> getMoviesGenres();
}
