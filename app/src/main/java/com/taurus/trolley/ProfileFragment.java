package com.taurus.trolley;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.squareup.otto.Subscribe;
import com.taurus.trolley.adapters.OfferHistoryListAdapter;
import com.taurus.trolley.busevents.NewOfferEvent;
import com.taurus.trolley.busevents.UserSavedSuccesfullyEvent;
import com.taurus.trolley.domain.OfferHistory;
import com.taurus.trolley.domain.User;
import com.taurus.trolley.helper.ParseQueryHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private ListView listViewOfferHistory;
    private OfferHistoryListAdapter offerHistoryListAdapter;
    private ImageView imageViewProfile;
    private TextView textViewWelcome;
    private TextView textViewCoins;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        App.bus.register(this);
    }

    @Override
    public void onPause() {
        App.bus.unregister(this);
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        initViews(rootView);
        if (User.currentUser() != null) {
            handleOfferHistory();
            handleScore();
            handleWelcomeMessage();
        }

        return rootView;
    }

    private void initViews(View rootView) {
        imageViewProfile = (ImageView) rootView.findViewById(R.id.image_view_profile);
        textViewWelcome = (TextView) rootView.findViewById(R.id.text_view_welcome_message);
        textViewCoins = (TextView) rootView.findViewById(R.id.text_view_coins);
        listViewOfferHistory = (ListView) rootView.findViewById(R.id.list_view_offer_history);
    }

    private void handleWelcomeMessage() {
        textViewWelcome.setText("Hi, " + User.currentUser().getFirstName() + " " + User.currentUser().getLastName());
    }

    private void handleScore() {
        textViewCoins.setText(String.valueOf(User.currentUser().getScore()));
    }

    private void handleOfferHistory() {
        ParseQueryHelper.getOfferHistory(new ParseQueryHelper.Callback() {
            @Override
            public void done(List results, ParseException e) {
                if (e == null) {
                    List<OfferHistory> listOfferHistory = new ArrayList<>((List<OfferHistory>) results.get(ParseQueryHelper.OFFER_HISTORY_INDEX));
                    offerHistoryListAdapter = new OfferHistoryListAdapter(getActivity(),
                            R.layout.list_item_offer_history,
                            listOfferHistory);
                    listViewOfferHistory.setAdapter(offerHistoryListAdapter);
                    ParseObject.pinAllInBackground(listOfferHistory);
                }
            }
        });
    }

    @Subscribe
    public void newOfferEventReceived(NewOfferEvent event) {
        offerHistoryListAdapter.insert(event.offerHistory, 0);
    }

    @Subscribe
    public void newUserSavedSuccesfullyEventReceived(UserSavedSuccesfullyEvent event) {
        handleScore();
        handleWelcomeMessage();
    }

    public static Fragment newInstance() {
        return new ProfileFragment();
    }
}
