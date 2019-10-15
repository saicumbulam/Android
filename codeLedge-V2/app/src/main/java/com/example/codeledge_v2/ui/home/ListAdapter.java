package com.example.codeledge_v2.ui.home;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeledge_v2.R;
import com.example.codeledge_v2.ui.categories.CategoriesFragment;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private List<HomeData> listdata;
    private HomeFragment homeFragment;

    // RecyclerView recyclerView;
    public ListAdapter(List<HomeData> listdata, HomeFragment homeFragment) {
        this.listdata = listdata;
        this.homeFragment = homeFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.fragment_home_cardview,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HomeData homeData = listdata.get(position);
        holder.textView.setText(listdata.get(position).getName());
        holder.imageView.setImageResource(listdata.get(position).getImgLocation());
        holder.totalNumberView.setText("Total Programs: " +
                (listdata.get(position).getTotalPrograms() != -1 ?
                        listdata.get(position).getTotalPrograms() : 0));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeFragment.startFragmentTransaction(view, homeData.getName());
                Toast.makeText(view.getContext(),"click on item: "
                        + homeData.getName(), Toast.LENGTH_LONG).show();
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
        public ImageView imageView;
        public TextView totalNumberView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.homeCardViewTextView);
            this.cardView = itemView.findViewById(R.id.homeCardView);
            this.imageView = itemView.findViewById(R.id.homeCardViewImageView);
            this.totalNumberView = itemView.findViewById(R.id.homeCardViewTotalPrograms);
        }
    }
}
