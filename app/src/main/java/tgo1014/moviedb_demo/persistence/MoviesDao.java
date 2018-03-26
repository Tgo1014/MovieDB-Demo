package tgo1014.moviedb_demo.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import tgo1014.moviedb_demo.entities.Movie;

@Dao
public interface MoviesDao {

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getMovies();

    @Query("SELECT * FROM movies WHERE id = :id")
    LiveData<Movie> getMovieById(String id);

    @Insert
    void insert(Movie movie);

    @Insert
    void insert(List<Movie> movieList);

}
