package com.example.healthapp.DealsActivityContents;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthapp.NearMeActivityContents.NearMeListData;
import com.example.healthapp.R;

import java.util.List;

public class DealsListAdapter extends BaseAdapter {
    Context context;
    List<DealsListData> dealsListData;

    public DealsListAdapter(Context context, List<DealsListData> items) {
        this.context = context;
        this.dealsListData = items;
    }
    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
        TextView price;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_deals, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.subtitle);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            holder.price = (TextView) convertView.findViewById(R.id.price);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        DealsListData rowItem = (DealsListData) getItem(position);
        holder.txtDesc.setText(rowItem.getSubtitle());
        holder.txtTitle.setText(rowItem.getTitle());
        holder.imageView.setImageResource(rowItem.getImageId());
        holder.price.setText("Price: " + String.valueOf(rowItem.getPrice()));
        return convertView;
    }
    @Override
    public int getCount() {
        return dealsListData.size();
    }
    @Override
    public Object getItem(int position) {
        return dealsListData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return dealsListData.indexOf(getItem(position));
    }
}
