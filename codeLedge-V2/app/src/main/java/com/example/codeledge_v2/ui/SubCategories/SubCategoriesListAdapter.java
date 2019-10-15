package com.example.codeledge_v2.ui.SubCategories;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeledge_v2.R;

import java.util.List;

public class SubCategoriesListAdapter extends RecyclerView.Adapter<SubCategoriesListAdapter.ViewHolder>{
    private List<SubCategoriesData> listdata;
    private SubCategoriesFragment subCategoriesFragment;

    // RecyclerView recyclerView;
    public SubCategoriesListAdapter(List<SubCategoriesData> listdata, SubCategoriesFragment categoriesFragment) {
        this.listdata = listdata;
        this.subCategoriesFragment = categoriesFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.fragment_subcategories_cardview,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SubCategoriesData subCategoriesData = listdata.get(position);
        holder.textView.setText(listdata.get(position).getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subCategoriesFragment.startFragmentTransaction(view, subCategoriesData.getName());
                Toast.makeText(view.getContext(),"click on item: "
                        + subCategoriesData.getName(), Toast.LENGTH_LONG).show();
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
            this.textView = itemView.findViewById(R.id.SubCategoriesCardViewTextView);
            this.cardView = itemView.findViewById(R.id.SubCategoriesCardView);
        }
    }
}
