package tgo1014.moviedb_demo;

import android.app.Application;
import android.arch.persistence.room.Room;

import tgo1014.moviedb_demo.persistence.MoviesAppDatabase;
import tgo1014.moviedb_demo.utils.AppExecutors;

public class App extends Application {

    public static MoviesAppDatabase database;
    public static AppExecutors appExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, MoviesAppDatabase.class, "movies_app_database.db").build();
        appExecutors = new AppExecutors();
    }
}
