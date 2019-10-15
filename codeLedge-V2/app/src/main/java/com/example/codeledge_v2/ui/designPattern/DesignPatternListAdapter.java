package com.example.codeledge_v2.ui.designPattern;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeledge_v2.R;

import java.util.List;

public class DesignPatternListAdapter extends RecyclerView.Adapter<DesignPatternListAdapter.ViewHolder>{
    private List<DesignPatternData> listdata;
    private DesignPatternFragment designPatternFragment;

    // RecyclerView recyclerView;
    public DesignPatternListAdapter(List<DesignPatternData> listdata, DesignPatternFragment designPatternFragment) {
        this.listdata = listdata;
        this.designPatternFragment = designPatternFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.fragment_designpattern_cardview,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DesignPatternData designPatternData = listdata.get(position);
        holder.textView.setText(listdata.get(position).getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                designPatternFragment.startFragmentTransaction(view, designPatternData.getName());
                Toast.makeText(view.getContext(),"click on item: "
                        + designPatternData.getName(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.designPatternCardViewTextView);
            this.cardView = itemView.findViewById(R.id.designPatternCardView);
        }
    }
}
