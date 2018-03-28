package tgo1014.moviedb_demo.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tgo1014.moviedb_demo.App;
import tgo1014.moviedb_demo.entities.Movie;
import tgo1014.moviedb_demo.entities.requests.MovieRequest;
import tgo1014.moviedb_demo.network.ApiResponse;
import tgo1014.moviedb_demo.network.RestClient;
import tgo1014.moviedb_demo.persistence.daos.MoviesDao;
import tgo1014.moviedb_demo.repositories.utils.NetworkBoundResource;
import tgo1014.moviedb_demo.repositories.utils.Resource;
import tgo1014.moviedb_demo.utils.AppExecutors;

public class MoviesRepository {

    private AppExecutors appExecutors = App.appExecutors;
    private MoviesDao moviesDao = App.database.moviesDao();

    public LiveData<Resource<List<Movie>>> getMovies() {
        return new NetworkBoundResource<List<Movie>, MovieRequest>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull MovieRequest item) {
                moviesDao.insertAll(item.getResults());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Movie> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                return moviesDao.getAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<MovieRequest>> createCall() {
                MediatorLiveData<ApiResponse<MovieRequest>> liveData = new MediatorLiveData<>();
                RestClient.getInstance().getMovieService().getPopularMoviesList().enqueue(new Callback<MovieRequest>() {
                    @Override
                    public void onResponse(Call<MovieRequest> call, Response<MovieRequest> response) {
                        if (response.isSuccessful()) {
                            liveData.setValue(new ApiResponse<>(response));
                            return;
                        }
                        onFailure(call, new Throwable(response.message()));
                    }

                    @Override
                    public void onFailure(Call<MovieRequest> call, Throwable t) {
                        liveData.setValue(new ApiResponse<>(t));
                    }
                });
                return liveData;
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<Movie>>> getMoviesByGenre(int genreId) {
        return new NetworkBoundResource<List<Movie>, MovieRequest>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull MovieRequest item) {
                moviesDao.insertAll(item.getResults());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Movie> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                return moviesDao.getByGenreId("%" + genreId + "%");
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<MovieRequest>> createCall() {
                MediatorLiveData<ApiResponse<MovieRequest>> liveData = new MediatorLiveData<>();
                RestClient.getInstance().getMovieService().getMoviesByGenre(genreId).enqueue(new Callback<MovieRequest>() {
                    @Override
                    public void onResponse(Call<MovieRequest> call, Response<MovieRequest> response) {
                        if (response.isSuccessful()) {
                            liveData.setValue(new ApiResponse<>(response));
                            return;
                        }
                        onFailure(call, new Throwable(response.message()));
                    }

                    @Override
                    public void onFailure(Call<MovieRequest> call, Throwable t) {
                        liveData.setValue(new ApiResponse<>(t));
                    }
                });
                return liveData;
            }
        }.asLiveData();
    }

    public LiveData<Resource<Movie>> getMovieDetails(int movieId) {
        return new NetworkBoundResource<Movie, Movie>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull Movie item) {
                moviesDao.insert(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable Movie data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<Movie> loadFromDb() {
                return moviesDao.getById(movieId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Movie>> createCall() {
                MediatorLiveData<ApiResponse<Movie>> liveData = new MediatorLiveData<>();
                RestClient.getInstance().getMovieService().getMovieDetails(movieId).enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        if (response.isSuccessful()) {
                            liveData.setValue(new ApiResponse<>(response));
                            return;
                        }
                        onFailure(call, new Throwable(response.message()));
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        liveData.setValue(new ApiResponse<>(t));
                    }
                });
                return liveData;
            }
        }.asLiveData();
    }
}
