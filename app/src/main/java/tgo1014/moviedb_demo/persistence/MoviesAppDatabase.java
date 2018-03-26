package tgo1014.moviedb_demo.persistence;

import android.arch.persistence.room.RoomDatabase;

import tgo1014.moviedb_demo.entities.Movie;

@android.arch.persistence.room.Database(entities = {Movie.class}, version = 1)
public abstract class MoviesAppDatabase extends RoomDatabase {
    public abstract MoviesDao moviesDao();
}
