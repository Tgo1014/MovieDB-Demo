package tgo1014.moviedb_demo.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import tgo1014.moviedb_demo.entities.Genre;
import tgo1014.moviedb_demo.entities.Movie;
import tgo1014.moviedb_demo.persistence.converters.IntegerListTypeConverter;
import tgo1014.moviedb_demo.persistence.daos.GenreDao;
import tgo1014.moviedb_demo.persistence.daos.MoviesDao;

@Database(entities = {Movie.class, Genre.class}, version = 1, exportSchema = false)
@TypeConverters({IntegerListTypeConverter.class})
public abstract class MoviesAppDatabase extends RoomDatabase {
    public abstract MoviesDao moviesDao();
    public abstract GenreDao genreDao();
}
