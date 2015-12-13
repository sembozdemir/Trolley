package com.taurus.trolley;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseException;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;
import com.taurus.trolley.busevents.CouldNotGetLocationEvent;
import com.taurus.trolley.domain.Shop;
import com.taurus.trolley.helper.ParseQueryHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, SlidingUpPanelLayout.PanelSlideListener {
    private static final String TAG = NearbyFragment.class.getSimpleName();
    private static final int MAX_DISTANCE_AS_KM = 15;

    private TextView textViewShopName;
    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private ArrayList<Shop> nearShopList;
    private HashMap<String, Shop> hashMarkers;
    private ImageView imageViewBrand;
    private SlidingUpPanelLayout slidingPanel;

    public NearbyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_nearby, container, false);

        hashMarkers = new HashMap<>();

        initViews(rootView);
        initGoogleApiClient();
        initMapFragment(true);

        return rootView;
    }

    public void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    private void initGoogleApiClient() {
        // Create an instance of GoogleAPIClient.
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void initViews(View rootView) {
        slidingPanel = (SlidingUpPanelLayout) rootView.findViewById(R.id.sliding_layout);
        slidingPanel.setPanelSlideListener(this);
        textViewShopName = (TextView) rootView.findViewById(R.id.text_view_shop_name);
        imageViewBrand = (ImageView) rootView.findViewById(R.id.image_view_brand);
    }

    private void initMapFragment(boolean liteMode) {
        GoogleMapOptions options = new GoogleMapOptions();
        options.mapToolbarEnabled(false);
//        CameraPosition cameraPos = CameraPosition.fromLatLngZoom(
//                new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()),
//                11
//        );
//        options.camera(cameraPos);
        options.liteMode(liteMode);
        mapFragment = SupportMapFragment.newInstance(options);
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map_container, mapFragment);
        fragmentTransaction.commit();
        mapFragment.getMapAsync(this);
    }

    public static Fragment newInstance() {
        return new NearbyFragment();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        initMap();
    }

    private void initMap() {
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                collapsePanel();
            }
        });

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Shop shop = hashMarkers.get(marker.getId());
                textViewShopName.setText(shop.getName());
                Picasso.with(getActivity())
                        .load(shop.getBrand().getLogoUrl())
                        .resizeDimen(R.dimen.shop_detail_panel_brand_logo_size,
                                R.dimen.shop_detail_panel_brand_logo_size)
                        .placeholder(R.drawable.ic_placeholder_shop)
                        .centerCrop()
                        .into(imageViewBrand);
                map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(
                        shop.getGeoPoint().getLatitude(),
                        shop.getGeoPoint().getLongitude()
                )));
                openPanel();
                return false;
            }
        });
    }

    private void setMarkers() {
        for (Shop shop : nearShopList) {
            Marker marker = map.addMarker(new MarkerOptions()
                    .position(new LatLng(shop.getGeoPoint().getLatitude(),
                            shop.getGeoPoint().getLongitude()))
                    .title(shop.getName()));
            hashMarkers.put(marker.getId(), shop);
        }
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 11));
    }

    @Override
    public void onConnected(Bundle bundle) {
        getLastLocation();
    }

    private void getLastLocation() {
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        if (lastLocation != null) {
            Log.d(TAG, lastLocation.getLatitude() + ", " + lastLocation.getLongitude());
            getNearShops();
        } else {
            App.bus.post(new CouldNotGetLocationEvent());
        }
    }

    private void getNearShops() {
        ParseQueryHelper.getNearShops(lastLocation, MAX_DISTANCE_AS_KM, new ParseQueryHelper.Callback() {
            @Override
            public void done(List results, ParseException e) {
                if (e == null) {
                    nearShopList = new ArrayList<Shop>(
                            (List<Shop>) results.get(ParseQueryHelper.NEAR_SHOPS_INDEX));
                    setMarkers();
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void tooglePanel() {
        if (slidingPanel.getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)) {
            slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        } else {
            slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
    }

    private void openPanel() {
        if (slidingPanel.getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)) {
            slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        }
    }

    private void collapsePanel() {
        if (slidingPanel.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED) ||
                slidingPanel.getPanelState().equals(SlidingUpPanelLayout.PanelState.ANCHORED)) {
            slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {

    }

    @Override
    public void onPanelCollapsed(View panel) {
        map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(
                lastLocation.getLatitude(),
                lastLocation.getLongitude()
        )));
    }

    @Override
    public void onPanelExpanded(View panel) {

    }

    @Override
    public void onPanelAnchored(View panel) {

    }

    @Override
    public void onPanelHidden(View panel) {

    }
}
