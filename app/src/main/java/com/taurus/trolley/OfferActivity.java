package com.taurus.trolley;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.taurus.trolley.domain.Offer;

public class OfferActivity extends AppCompatActivity {

    public static final String EXTRA_OFFER_ID = "offerId";
    public static final String EXTRA_OFFER_DESC = "offerDescription";
    public static final String EXTRA_BRAND_LOGO_URL = "brandLogoUrl";
    private TextView textViewOfferDesc;
    private ImageView imageViewBrand;
    private String offerId;
    private String offerDesc;
    private String brandLogoUrl;
    private TextView textViewOfferId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        handleIntent();

        initViews();
        
        Picasso.with(this).load(brandLogoUrl).into(imageViewBrand);
        textViewOfferDesc.setText(offerDesc);
        textViewOfferId.setText(offerId);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews() {
        textViewOfferDesc = (TextView) findViewById(R.id.text_view_offer_description);
        textViewOfferId = (TextView) findViewById(R.id.text_view_offer_id);
        imageViewBrand = (ImageView) findViewById(R.id.image_view_offer_brand_logo);
    }

    private void handleIntent() {
        Intent i = getIntent();
        offerId = i.getStringExtra(EXTRA_OFFER_ID);
        offerDesc = i.getStringExtra(EXTRA_OFFER_DESC);
        brandLogoUrl = i.getStringExtra(EXTRA_BRAND_LOGO_URL);
    }

    public static Intent newIntent(Context context, Offer offer) {
        Intent intent = new Intent(context, OfferActivity.class);
        intent.putExtra(EXTRA_OFFER_ID, offer.getObjectId());
        intent.putExtra(EXTRA_OFFER_DESC, offer.getDescription());
        intent.putExtra(EXTRA_BRAND_LOGO_URL, offer.getOfferImageUrl());
        return intent;
    }
}
