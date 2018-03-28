package tgo1014.moviedb_demo.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.List;

import tgo1014.moviedb_demo.entities.Movie;
import tgo1014.moviedb_demo.repositories.MoviesRepository;
import tgo1014.moviedb_demo.repositories.utils.Resource;

public class MoviesViewModel extends ViewModel {

    private MoviesRepository moviesRepository = new MoviesRepository();
    private int genreId;

    public MoviesViewModel() {
    }

    private MoviesViewModel(int genreId) {
        this.genreId = genreId;
    }

    public LiveData<Resource<List<Movie>>> getMovies() {
        if (genreId == 0)
            return moviesRepository.getMovies();
        return moviesRepository.getMoviesByGenre(genreId);
    }

    public LiveData<Resource<Movie>> getMovieDetail(int movieId){
        return moviesRepository.getMovieDetails(movieId);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private int genreId;

        public Factory(int genreId) {
            this.genreId = genreId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MoviesViewModel(genreId);
        }
    }
}
