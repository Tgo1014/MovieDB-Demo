package tgo1014.moviedb_demo.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import tgo1014.moviedb_demo.entities.Genre;
import tgo1014.moviedb_demo.repositories.GenreRepository;
import tgo1014.moviedb_demo.repositories.utils.Resource;

public class GenresViewModel extends ViewModel {

    private GenreRepository genreRepository;

    public GenresViewModel() {
        this.genreRepository = new GenreRepository();
    }

    public LiveData<Resource<List<Genre>>> getGenres() {
        return genreRepository.getGenres();
    }
}
