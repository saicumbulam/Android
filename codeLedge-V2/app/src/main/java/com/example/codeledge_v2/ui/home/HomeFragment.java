package com.example.codeledge_v2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeledge_v2.DatabaseOps.DbHelper;
import com.example.codeledge_v2.R;
import com.example.codeledge_v2.ui.categories.CategoriesFragment;
import com.example.codeledge_v2.ui.designPattern.DesignPatternFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private DbHelper dbHelper = DbHelper.getInstance();
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        UpdateRecyclerView(root);

        return root;
    }

    private void UpdateRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.homeRecycler);
        RecyclerView.Adapter mAdapter = new ListAdapter(loadcategories(), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(mAdapter);
    }

    private List<HomeData> loadcategories() {
        List<HomeData> homeData = new ArrayList<HomeData>(){{
            add(new HomeData("Algorithms", R.drawable.algorithms,
                    dbHelper.getNumberofMainCategoryCounts(
                            "programs")));
            add(new HomeData("DataStructures", R.drawable.datastructures,
                    dbHelper.getNumberofMainCategoryCounts("datastructures")));
            add(new HomeData("Design Patterns", R.drawable.designpattern,
                    dbHelper.getNumberofMainCategoryCounts("designpattern")));
        }};
        return homeData;
    }

    public void startFragmentTransaction(View view, String typeName){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (typeName.equals("Algorithms")) {
            fragmentTransaction.replace(R.id.nav_host_fragment_category, new CategoriesFragment());
        } else if (typeName.equals("Design Patterns")) {
            fragmentTransaction.replace(R.id.nav_host_fragment_category, new DesignPatternFragment());
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
 }