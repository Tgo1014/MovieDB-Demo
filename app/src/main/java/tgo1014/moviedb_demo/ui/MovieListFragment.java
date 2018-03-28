package tgo1014.moviedb_demo.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tgo1014.moviedb_demo.R;
import tgo1014.moviedb_demo.entities.Movie;
import tgo1014.moviedb_demo.ui.adapters.MovieAdapter;
import tgo1014.moviedb_demo.viewmodels.MoviesViewModel;

public class MovieListFragment extends Fragment implements MovieAdapter.OnMovieClickedListener {

    private static final String MOVIE_GENRE_ID = "MOVIE_GENRE_ID";

    private int movieGenreId;
    private MoviesViewModel moviesVM;
    private MovieAdapter movieAdapter;

    private ProgressBar movieFragmentProgressBar;
    private RecyclerView movieFragmentRecyclerView;

    public MovieListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieGenreId = getArguments().getInt(MOVIE_GENRE_ID);
        }

        moviesVM = ViewModelProviders.of(this, new MoviesViewModel.Factory(movieGenreId)).get(MoviesViewModel.class);
        subscribeToVM(moviesVM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieFragmentProgressBar = view.findViewById(R.id.fragment_movie_progress_bar);
        movieFragmentRecyclerView = view.findViewById(R.id.fragment_movie_recycler_movie_list);

        setupRecyclerView();
    }

    private void subscribeToVM(MoviesViewModel moviesVM) {
        moviesVM.getMovies().observe(this, data -> {
            if (data != null) {
                switch (data.status) {
                    case SUCCESS:
                        if (data.data != null) showMovies(data.data);
                        break;
                    case LOADING:
                        Toast.makeText(getContext(),
                                data.message == null || data.message.isEmpty() ? getString(R.string.str_unable_to_refresh_data) : data.message,
                                Toast.LENGTH_SHORT).show();
                        if (data.data != null) {
                            showMovies(data.data);
                            return;
                        }
                        movieFragmentProgressBar.setVisibility(View.VISIBLE);
                        break;
                    case ERROR:
                        Toast.makeText(getContext(), data.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    public void showMovies(List<Movie> movieList) {
        movieFragmentProgressBar.setVisibility(View.GONE);
        movieFragmentRecyclerView.setVisibility(View.VISIBLE);
        movieAdapter.updateMovieList(movieList);
    }

    public void setupRecyclerView() {
        movieAdapter = new MovieAdapter(getContext(), new ArrayList<>(), this);
        movieFragmentRecyclerView.setAdapter(movieAdapter);
        movieFragmentRecyclerView.setHasFixedSize(true);
        movieFragmentRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), calculateNoOfColumns()));
        movieFragmentRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public static MovieListFragment newInstance(Integer genreId) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putInt(MOVIE_GENRE_ID, genreId);
        fragment.setArguments(args);
        return fragment;
    }

    public int calculateNoOfColumns() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 120);
    }

    @Override
    public void onMovieClick(int movieId) {
        MovieDetailsActivity.start(getContext(), movieId);
    }
}
