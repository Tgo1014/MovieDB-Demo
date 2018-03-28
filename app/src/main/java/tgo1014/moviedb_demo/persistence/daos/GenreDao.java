package tgo1014.moviedb_demo.persistence.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import tgo1014.moviedb_demo.entities.Genre;

@Dao
public interface GenreDao {

    @Query("SELECT * FROM genre")
    LiveData<List<Genre>> getAll();

    @Query("SELECT * FROM genre WHERE id = :id")
    LiveData<Genre> getById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Genre genre);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Genre> genreList);

}
