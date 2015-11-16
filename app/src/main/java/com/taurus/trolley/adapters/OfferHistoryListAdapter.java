package com.taurus.trolley.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.taurus.trolley.R;
import com.taurus.trolley.domain.Offer;
import com.taurus.trolley.domain.OfferHistory;

import java.util.List;

/**
 * Created by semih on 07.11.2015.
 */
public class OfferHistoryListAdapter extends ArrayAdapter<OfferHistory> {
    private List<OfferHistory> itemList;
    private Context context;
    private int layoutId;

    public OfferHistoryListAdapter(Context context, int layoutId, List<OfferHistory> objects) {
        super(context, layoutId, objects);
        this.layoutId = layoutId;
        this.context = context;
        this.itemList = objects;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public OfferHistory getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemList.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutId, null);

            viewHolder = new ViewHolder();
            viewHolder.imageViewBrand = (ImageView) convertView.findViewById(R.id.image_view_list_item_brand);
            viewHolder.textViewDescription = (TextView) convertView.findViewById(R.id.text_view_list_item_brand);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        OfferHistory offerHistory = itemList.get(position);
        Offer offer = offerHistory.getOffer();

        Picasso.with(context)
                .load(offer.getOfferImageUrl())
                .placeholder(R.drawable.ic_bag)
                .into(viewHolder.imageViewBrand);

        String description = offer.getDescription();
        viewHolder.textViewDescription.setText(description);

        return convertView;
    }

    protected class ViewHolder {
        ImageView imageViewBrand;
        TextView textViewDescription;
    }
}
