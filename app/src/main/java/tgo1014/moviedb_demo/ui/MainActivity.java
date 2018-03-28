package tgo1014.moviedb_demo.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.List;

import tgo1014.moviedb_demo.R;
import tgo1014.moviedb_demo.entities.Genre;
import tgo1014.moviedb_demo.ui.adapters.ViewPagerAdapter;
import tgo1014.moviedb_demo.viewmodels.GenresViewModel;

public class MainActivity extends AppCompatActivity {

    private Toolbar mainActivityToolbar;
    private TabLayout mainActivtyTabLayout;
    private ViewPager mainActivityViewPager;
    private GenresViewModel genresViewModel;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityToolbar = findViewById(R.id.main_activity_toolbar);
        mainActivityViewPager = findViewById(R.id.main_activity_view_pager);
        mainActivtyTabLayout = findViewById(R.id.main_activity_tabs);

        setSupportActionBar(mainActivityToolbar);
        addTabs(mainActivityViewPager);
        mainActivtyTabLayout.setupWithViewPager(mainActivityViewPager);

        genresViewModel = ViewModelProviders.of(this).get(GenresViewModel.class);
        subscribeToVM(genresViewModel);
    }

    private void addTabs(ViewPager mainActivityViewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        addDefaultHomeTab(viewPagerAdapter);
        mainActivityViewPager.setAdapter(viewPagerAdapter);
    }

    private void addDefaultHomeTab(ViewPagerAdapter adapter) {
        adapter.add(0, getString(R.string.str_home));
    }

    private void subscribeToVM(GenresViewModel genresViewModel) {
        genresViewModel.getGenres().observe(this, data -> {
            if (data != null) {
                switch (data.status) {
                    case SUCCESS:
                        configureGenreTabsWithData(data.data, mainActivityViewPager);
                        break;
                    case ERROR:
                        break;
                    case LOADING:
                        configureGenreTabsWithData(data.data, mainActivityViewPager);
                        break;
                }
            }
        });
    }

    private void configureGenreTabsWithData(List<Genre> genreList, ViewPager viewPager) {
        if (genreList != null && viewPager != null) {
            ViewPagerAdapter newPageAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            addDefaultHomeTab(newPageAdapter);
            for (Genre genre : genreList) {
                newPageAdapter.add(genre.getId(), genre.getName());
                viewPager.setAdapter(newPageAdapter);
            }
        }
    }
}
