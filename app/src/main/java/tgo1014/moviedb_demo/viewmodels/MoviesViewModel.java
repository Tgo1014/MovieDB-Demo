package tgo1014.moviedb_demo.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
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
    private int page = 1;
    private boolean isLoading = false;
    private boolean lastPageReached = false;

    private final MediatorLiveData<Resource<List<Movie>>> observableMovieList = new MediatorLiveData<>();

    public MoviesViewModel() {
    }

    private MoviesViewModel(int genreId) {
        this.genreId = genreId;

        if (genreId == 0) {
            observableMovieList.addSource(moviesRepository.getMovies(page), observableMovieList::setValue);
            return;
        }
        observableMovieList.addSource(moviesRepository.getMoviesByGenre(genreId, page), observableMovieList::setValue);
    }

    public LiveData<Resource<List<Movie>>> getMovies() {
        return observableMovieList;
    }

    public LiveData<Resource<Movie>> getMovieDetail(int movieId) {
        return moviesRepository.getMovieDetails(movieId);
    }

    public void loadNextPage() {
        if (!isLoading && !lastPageReached) {
            page++;
            if (genreId == 0) {
                observableMovieList.addSource(moviesRepository.getMovies(page), observableMovieList::setValue);
                return;
            }
            observableMovieList.addSource(moviesRepository.getMoviesByGenre(genreId, page), observableMovieList::setValue);
        }
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public boolean isLastPageReached() {
        return lastPageReached;
    }

    public void setLastPageReached(boolean lastPageReached) {
        this.lastPageReached = lastPageReached;
    }

    @SuppressWarnings("unchecked")
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
