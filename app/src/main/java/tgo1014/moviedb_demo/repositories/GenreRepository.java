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
import tgo1014.moviedb_demo.entities.Genre;
import tgo1014.moviedb_demo.entities.requests.GenreRequest;
import tgo1014.moviedb_demo.network.ApiResponse;
import tgo1014.moviedb_demo.network.RestClient;
import tgo1014.moviedb_demo.persistence.daos.GenreDao;
import tgo1014.moviedb_demo.repositories.utils.NetworkBoundResource;
import tgo1014.moviedb_demo.repositories.utils.Resource;
import tgo1014.moviedb_demo.utils.AppExecutors;

public class GenreRepository {

    private AppExecutors appExecutors = App.appExecutors;
    private GenreDao genreDao = App.database.genreDao();

    public LiveData<Resource<List<Genre>>> getGenres() {
        return new NetworkBoundResource<List<Genre>, GenreRequest>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull GenreRequest item) {
                genreDao.insert(item.getGenres());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Genre> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<Genre>> loadFromDb() {
                return genreDao.getAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<GenreRequest>> createCall() {
                MediatorLiveData<ApiResponse<GenreRequest>> liveData = new MediatorLiveData<>();
                RestClient.getInstance().getGenresService().getMoviesGenres().enqueue(new Callback<GenreRequest>() {
                    @Override
                    public void onResponse(Call<GenreRequest> call, Response<GenreRequest> response) {
                        if (response.isSuccessful()) {
                            liveData.setValue(new ApiResponse<>(response));
                            return;
                        }
                        onFailure(call, new Throwable(response.message()));
                    }

                    @Override
                    public void onFailure(Call<GenreRequest> call, Throwable t) {
                        liveData.setValue(new ApiResponse<>(t));
                    }
                });
                return liveData;
            }
        }.asLiveData();
    }
}
