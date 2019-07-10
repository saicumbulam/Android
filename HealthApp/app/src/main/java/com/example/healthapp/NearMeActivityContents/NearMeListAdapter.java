package com.example.healthapp.NearMeActivityContents;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthapp.R;

import java.util.List;

public class NearMeListAdapter extends BaseAdapter {
    Context context;
    List<NearMeListData> nearMeListData;

    public NearMeListAdapter(Context context, List<NearMeListData> items) {
        this.context = context;
        this.nearMeListData = items;
    }
    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.subtitle);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        NearMeListData rowItem = (NearMeListData) getItem(position);
        holder.txtDesc.setText(rowItem.getSubtitle());
        holder.txtTitle.setText(rowItem.getTitle());
        holder.imageView.setImageResource(rowItem.getImageId());
        return convertView;
    }
    @Override
    public int getCount() {
        return nearMeListData.size();
    }
    @Override
    public Object getItem(int position) {
        return nearMeListData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return nearMeListData.indexOf(getItem(position));
    }
}
