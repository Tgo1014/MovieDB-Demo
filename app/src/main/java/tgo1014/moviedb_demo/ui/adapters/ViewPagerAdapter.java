package tgo1014.moviedb_demo.ui.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Pair<Fragment, String>> fragmentList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void add(Fragment f, String title) {
        fragmentList.add(new Pair<>(f, title));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentList.get(position).second;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position).first;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
