package tgo1014.moviedb_demo.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import tgo1014.moviedb_demo.entities.Movie;
import tgo1014.moviedb_demo.persistence.converters.IntegerListTypeConverter;

@Database(entities = {Movie.class}, version = 1)
@TypeConverters({IntegerListTypeConverter.class})
public abstract class MoviesAppDatabase extends RoomDatabase {
    public abstract MoviesDao moviesDao();
}
