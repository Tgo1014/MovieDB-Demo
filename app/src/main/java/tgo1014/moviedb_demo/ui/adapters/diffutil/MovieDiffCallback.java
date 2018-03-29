package tgo1014.moviedb_demo.ui.adapters.diffutil;

import android.support.v7.util.DiffUtil;

import java.util.List;

import tgo1014.moviedb_demo.entities.Movie;

public class MovieDiffCallback extends DiffUtil.Callback {

    private final List<Movie> oldMovieList;
    private final List<Movie> newMovieList;

    public MovieDiffCallback(List<Movie> oldMovieList, List<Movie> newMovieList) {
        this.oldMovieList = oldMovieList;
        this.newMovieList = newMovieList;
    }

    @Override
    public int getOldListSize() {
        return oldMovieList.size();
    }

    @Override
    public int getNewListSize() {
        return newMovieList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMovieList.get(oldItemPosition).getId().equals(newMovieList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMovieList.get(oldItemPosition).getTitle().equals(newMovieList.get(newItemPosition).getTitle());
    }
}
