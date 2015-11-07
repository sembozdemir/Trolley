package com.taurus.trolley.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.taurus.trolley.BadgesFragment;
import com.taurus.trolley.NearbyFragment;
import com.taurus.trolley.ProfileFragment;
import com.taurus.trolley.R;

/**
 * Created by semih on 07.11.2015.
 */
public class TabFragmentAdapter extends FragmentPagerAdapter {
    private final String[] titles;

    public TabFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        titles = new String[]{
                context.getString(R.string.tab_profile_title),
                context.getString(R.string.tab_nearby_title),
                context.getString(R.string.tab_badges_title)
        };
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ProfileFragment.newInstance();
            case 1:
                return NearbyFragment.newInstance();
            case 2:
                return BadgesFragment.newInstance();
            default:
                return ProfileFragment.newInstance();
        }
    }
}
