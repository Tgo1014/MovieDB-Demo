package tgo1014.moviedb_demo.ui;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import tgo1014.moviedb_demo.R;
import tgo1014.moviedb_demo.entities.Movie;
import tgo1014.moviedb_demo.network.RestClient;
import tgo1014.moviedb_demo.viewmodels.MoviesViewModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String MOVIE_ID = "MOVIE_ID";
    private static final String MOVIE_TRANSITION_NAME = "MOVIE_TRANSITION_NAME";
    int movieId;

    private MoviesViewModel moviesVM;

    private Toolbar detailActivityToolbar;
    private TextView detailActivityTvTitle;
    private TextView detailActivityTvTagline;
    private ImageView detailActivityIvHeader;
    private ImageView detailActivityIvPoster;
    private TextView detailActivityTvOverviewContent;
    private TextView detailActivityTvHomepageContent;
    private TextView detailActivityTvReleaseDateContent;
    private TextView detailActivityTvStatusContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        detailActivityTvTitle = findViewById(R.id.detail_activity_tv_title);
        detailActivityTvTagline = findViewById(R.id.detail_activity_tv_tagline);
        detailActivityIvHeader = findViewById(R.id.detail_activity_iv_header);
        detailActivityIvPoster = findViewById(R.id.detail_activity_iv_poster);
        detailActivityTvOverviewContent = findViewById(R.id.detail_activity_tv_overview_content);
        detailActivityTvHomepageContent = findViewById(R.id.detail_activity_tv_homepage_content);
        detailActivityTvReleaseDateContent = findViewById(R.id.detail_activity_tv_release_date_content);
        detailActivityTvStatusContent = findViewById(R.id.detail_activity_tv_status_content);

        detailActivityToolbar = findViewById(R.id.detail_activity_toolbar);
        setSupportActionBar(detailActivityToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        supportPostponeEnterTransition();
        Intent intent = getIntent();
        movieId = intent.getIntExtra(MOVIE_ID, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String imageTransitionNam2e = intent.getStringExtra(MOVIE_TRANSITION_NAME);
            detailActivityIvPoster.setTransitionName(imageTransitionNam2e);
        }

        moviesVM = ViewModelProviders.of(this).get(MoviesViewModel.class);
        subscribeToVM(moviesVM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return (super.onOptionsItemSelected(item));
        }
    }

    private void subscribeToVM(MoviesViewModel moviesVM) {
        moviesVM.getMovieDetail(movieId).observe(this, data -> {
            if (data != null) {
                switch (data.status) {
                    case SUCCESS:
                        configureFields(data.data);
                        break;
                    case ERROR:
                        Toast.makeText(this,
                                data.message == null || data.message.isEmpty() ? getString(R.string.str_unable_to_get_movie_details) : data.message,
                                Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        configureFields(data.data);
                        break;
                }
            }
        });
    }

    private void configureFields(Movie movie) {
        if (movie != null) {
            getSupportActionBar().setTitle(movie.getTitle());
            detailActivityTvTitle.setText(movie.getTitle());
            detailActivityTvTagline.setText(movie.getTagline());
            detailActivityTvOverviewContent.setText(movie.getOverview());
            detailActivityTvReleaseDateContent.setText(movie.getReleaseDate());
            detailActivityTvStatusContent.setText(movie.getStatus());

            if (movie.getHomepage() != null) {
                detailActivityTvHomepageContent.setText(Html.fromHtml("<a href=\"" + movie.getHomepage() + "\">" + movie.getHomepage() + "</a>"));
                detailActivityTvHomepageContent.setMovementMethod(LinkMovementMethod.getInstance());
            }
            if (movie.getHomepage() == null) detailActivityTvHomepageContent.setText("-");

            Glide.with(this)
                    .load(RestClient.BASE_URL_POSTER_SIZE_500 + movie.getPosterPath())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_movie_placeholder).centerCrop())
                    .apply(new RequestOptions().centerCrop())
                    .into(detailActivityIvHeader);

            Glide.with(this)
                    .load(RestClient.BASE_URL_POSTER_SIZE_342 + movie.getPosterPath())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_movie_placeholder).centerCrop())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            supportStartPostponedEnterTransition();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            supportStartPostponedEnterTransition();
                            return false;
                        }
                    })
                    .into(detailActivityIvPoster);
        }
    }

    public static void start(Context context, int movieId) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(MOVIE_ID, movieId);
        context.startActivity(intent);
    }

    public static void startWithAnimation(Context context, Activity activity, int movieId, View sharedImageView) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                sharedImageView,
                ViewCompat.getTransitionName(sharedImageView));
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(MOVIE_ID, movieId);
        intent.putExtra(MOVIE_TRANSITION_NAME, ViewCompat.getTransitionName(sharedImageView));
        context.startActivity(intent, options.toBundle());
    }
}
