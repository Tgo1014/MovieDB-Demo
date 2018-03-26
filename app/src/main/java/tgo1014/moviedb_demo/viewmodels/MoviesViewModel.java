package tgo1014.moviedb_demo.viewmodels;

import android.arch.lifecycle.LiveData;

import java.util.List;

import tgo1014.moviedb_demo.entities.Movie;
import tgo1014.moviedb_demo.repositories.MoviesRepository;
import tgo1014.moviedb_demo.repositories.Resource;

public class MoviesViewModel {

    private MoviesRepository moviesRepository;

    public MoviesViewModel(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public LiveData<Resource<List<Movie>>> getMovies() {
        return moviesRepository.getMovies();
    }

}
