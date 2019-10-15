package com.example.codeledge_v2.ui.categories;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeledge_v2.R;

import java.util.List;

public class CategoriesListAdapter extends RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>{
    private List<CategoriesData> listdata;
    private CategoriesFragment categoriesFragment;

    // RecyclerView recyclerView;
    public CategoriesListAdapter(List<CategoriesData> listdata, CategoriesFragment categoriesFragment) {
        this.listdata = listdata;
        this.categoriesFragment = categoriesFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.fragment_categories_cardview,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CategoriesData categoriesData = listdata.get(position);
        holder.textView.setText(listdata.get(position).getName());
        holder.totalNumberView.setText("Total Programs: " +
                (listdata.get(position).getTotalPrograms() != -1 ?
                        listdata.get(position).getTotalPrograms() : 0));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoriesFragment.startFragmentTransaction(view, categoriesData.getName());
                Toast.makeText(view.getContext(),"click on item: "
                        + categoriesData.getName(), Toast.LENGTH_LONG).show();
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
        public TextView totalNumberView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.CategoriesCardViewTextView);
            this.cardView = itemView.findViewById(R.id.CategoriesCardView);
            this.totalNumberView = itemView.findViewById(R.id.CategoriesCardViewTotalPrograms);
        }
    }
}
