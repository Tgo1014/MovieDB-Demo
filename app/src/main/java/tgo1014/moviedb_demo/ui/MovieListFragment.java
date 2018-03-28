package tgo1014.moviedb_demo.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import tgo1014.moviedb_demo.R;
import tgo1014.moviedb_demo.viewmodels.MoviesViewModel;

public class MovieListFragment extends Fragment {

    private static final String MOVIE_GENRE = "MOVIE_GENRE";

    private String movieGenre;
    private MoviesViewModel moviesVM;

    private TextView movieFragmentTextViewError;
    private ProgressBar movieFragmentProgressBar;

    public MovieListFragment() {
        // Required empty public constructor
    }

    public static MovieListFragment newInstance(String movieGenre) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putString(MOVIE_GENRE, movieGenre);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieGenre = getArguments().getString(MOVIE_GENRE);
        }

        moviesVM = ViewModelProviders.of(this).get(MoviesViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movieFragmentTextViewError = view.findViewById(R.id.fragment_movie_textview_error);
        movieFragmentProgressBar = view.findViewById(R.id.fragment_movie_progress_bar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

}
