package com.taurus.trolley.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.taurus.trolley.R;
import com.taurus.trolley.domain.Offer;
import com.taurus.trolley.domain.Transaction;

import java.util.List;

/**
 * Created by semih on 07.11.2015.
 */
public class ShoppingListAdapter extends ArrayAdapter<Transaction> {
    private List<Transaction> itemList;
    private Context context;
    private int layoutId;

    public ShoppingListAdapter(Context context, int layoutId, List<Transaction> objects) {
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
    public Transaction getItem(int position) {
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

        Transaction transaction = itemList.get(position);
        Offer offer = transaction.getOffer();

//        String brandLogoUrl = offer.getShop().getBrand().getLogoUrl();
//        Picasso.with(context)
//                .load(brandLogoUrl)
//                .placeholder(R.drawable.ic_bag)
//                .into(viewHolder.imageViewBrand);
        viewHolder.imageViewBrand.setImageResource(R.drawable.ic_bag);

        String description = offer.getDescription();
        viewHolder.textViewDescription.setText(description);

        return convertView;
    }

    protected class ViewHolder {
        ImageView imageViewBrand;
        TextView textViewDescription;
    }
}
