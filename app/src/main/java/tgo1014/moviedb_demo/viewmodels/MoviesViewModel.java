package tgo1014.moviedb_demo.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import tgo1014.moviedb_demo.entities.Movie;
import tgo1014.moviedb_demo.repositories.MoviesRepository;
import tgo1014.moviedb_demo.repositories.utils.Resource;

public class MoviesViewModel extends ViewModel {

    private MoviesRepository moviesRepository;

    public MoviesViewModel() {
    }

    public MoviesViewModel(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public LiveData<Resource<List<Movie>>> getMovies() {
        return moviesRepository.getMovies();
    }

}
