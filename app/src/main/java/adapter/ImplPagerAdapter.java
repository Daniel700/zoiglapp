package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import view.CalendarFragment;
import view.TavernFragment;

/**
 * Created by Daniel on 05.01.2017.
 */

public class ImplPagerAdapter extends FragmentStatePagerAdapter {
    final private int NUMBER_OF_TABS = 2;
    private String tabTitles[] = new String[]{"Termine", "Zoiglstuben"};

    public ImplPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new CalendarFragment();
            case 1:
                return new TavernFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return NUMBER_OF_TABS;
    }
}