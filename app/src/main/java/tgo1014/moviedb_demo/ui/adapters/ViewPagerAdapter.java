package tgo1014.moviedb_demo.ui.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;

import tgo1014.moviedb_demo.ui.MovieListFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Pair<Integer, String>> fragmentList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void add(int genreId, String title) {
        fragmentList.add(new Pair<>(genreId, title));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentList.get(position).second;
    }

    @Override
    public Fragment getItem(int position) {
        return MovieListFragment.newInstance(fragmentList.get(position).first);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
