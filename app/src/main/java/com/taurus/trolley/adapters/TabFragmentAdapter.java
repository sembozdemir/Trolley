package com.taurus.trolley.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.taurus.trolley.BadgesFragment;
import com.taurus.trolley.NearbyFragment;
import com.taurus.trolley.ProfileFragment;
import com.taurus.trolley.R;

import io.karim.MaterialTabs;

/**
 * Created by semih on 07.11.2015.
 */
public class TabFragmentAdapter extends FragmentPagerAdapter implements MaterialTabs.CustomTabProvider {
    private final String[] titles;
    private final int[] icons;

    public TabFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        titles = new String[]{
                context.getString(R.string.tab_profile_title),
                context.getString(R.string.tab_nearby_title),
                context.getString(R.string.tab_badges_title)
        };
        icons = new int[] {
                R.drawable.ic_tab_home,
                R.drawable.ic_tab_nearby,
                R.drawable.ic_tab_badges
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

    @Override
    public View getCustomTabView(ViewGroup parent, int position) {
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setPadding(0, 32, 0, 32);
        imageView.setImageResource(icons[position]);
//        Picasso.with(parent.getContext())
//                .load(icons[position])
//                .resizeDimen(R.dimen.tab_size, R.dimen.tab_size)
//                .into(imageView);
        return imageView;
    }
}
