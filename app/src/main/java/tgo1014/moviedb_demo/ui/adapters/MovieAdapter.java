package tgo1014.moviedb_demo.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import tgo1014.moviedb_demo.R;
import tgo1014.moviedb_demo.entities.Movie;
import tgo1014.moviedb_demo.network.RestClient;
import tgo1014.moviedb_demo.ui.adapters.diffutil.MovieDiffCallback;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private Context context;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.movieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(holder.getAdapterPosition());
        holder.movieTitle.setText(movie.getTitle());

        Glide.with(context)
                .load(RestClient.BASE_URL_POSTER_SIZE_185 + movie.getPosterPath())
                .apply(new RequestOptions().placeholder(R.drawable.movie_placeholder).centerCrop())
                .apply(new RequestOptions().centerCrop())
                .into(holder.movieImage);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void add(Movie movie) {
        if (!movieList.contains(movie)) {
            List<Movie> newMovieList = new ArrayList<>(movieList);
            newMovieList.add(movie);
            updateMovieList(newMovieList);
        }
    }

    public void updateMovieList(List<Movie> items) {
        final MovieDiffCallback diffCallback = new MovieDiffCallback(movieList, items);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        movieList.clear();
        movieList.addAll(items);
        diffResult.dispatchUpdatesTo(this);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public ImageView movieImage;
        public TextView movieTitle;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.item_movie_iv_movie);
            movieTitle = itemView.findViewById(R.id.item_movie_tv_title);
        }
    }
}