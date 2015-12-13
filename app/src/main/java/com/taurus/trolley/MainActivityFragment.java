package com.taurus.trolley;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taurus.trolley.adapters.TabFragmentAdapter;

import io.karim.MaterialTabs;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initTabAndPager(rootView);

        return rootView;
    }

    private void initTabAndPager(View rootView) {
        // Initialize the ViewPager and set an adapter
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
        pager.setAdapter(new TabFragmentAdapter(getActivity(), getChildFragmentManager()));

        // Bind the tabs to the ViewPager
        MaterialTabs tabs = (MaterialTabs) rootView.findViewById(R.id.tabs);
        tabs.setViewPager(pager);
        tabs.setTextSize(getActivity().getResources().getDimensionPixelSize(R.dimen.tab_text_size));
    }
}
