package com.taurus.trolley;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.taurus.trolley.adapters.ShoppingListAdapter;
import com.taurus.trolley.domain.Transaction;
import com.taurus.trolley.dummy.DummyData;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private ListView listViewShoppings;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        listViewShoppings = (ListView) rootView.findViewById(R.id.list_view_shopping);
        List<Transaction> listTransactions = DummyData.createTransactionList();
        ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(getActivity(),
                R.layout.list_item_shopping,
                listTransactions);
        listViewShoppings.setAdapter(shoppingListAdapter);

        return rootView;
    }


    public static Fragment newInstance() {
        return new ProfileFragment();
    }
}
