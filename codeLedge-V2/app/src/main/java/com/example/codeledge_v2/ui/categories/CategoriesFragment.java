package com.example.codeledge_v2.ui.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeledge_v2.DatabaseOps.DbHelper;
import com.example.codeledge_v2.R;
import com.example.codeledge_v2.ui.SubCategories.SubCategoriesFragment;


import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {
    private List<CategoriesData> categoriesData;
    private View root;
    private RecyclerView recyclerView;
    private DbHelper dbHelper = DbHelper.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_categories, container, false);
        UpdateRecyclerView(root);
        return root;
    }

    private void UpdateRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.CategoriesRecycler);
        RecyclerView.Adapter mAdapter = new CategoriesListAdapter(loadcategories(), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(mAdapter);
    }

    private List<CategoriesData> loadcategories() {
        List<CategoriesData> categoriesData = new ArrayList<CategoriesData>(){{
            add(new CategoriesData("Array",
                    dbHelper.getNumberofCategoryCounts("Array")));
            add(new CategoriesData("Linked List",
                    dbHelper.getNumberofCategoryCounts("LinkedList")));
            add(new CategoriesData("Binary Tree",
                    dbHelper.getNumberofCategoryCounts("BinaryTree")));
            add(new CategoriesData("Sorting",
                    dbHelper.getNumberofCategoryCounts("Sorting")));
            add(new CategoriesData("Priority Queue",
                    dbHelper.getNumberofCategoryCounts("PriorityQueue")));
            add(new CategoriesData("Strings",
                    dbHelper.getNumberofCategoryCounts("Strings")));
            add(new CategoriesData("Stack",
                    dbHelper.getNumberofCategoryCounts("Stack")));
            add(new CategoriesData("Search",
                    dbHelper.getNumberofCategoryCounts("Search")));
        }};
        return categoriesData;
    }

    public void startFragmentTransaction(View view, String categoryItem) {
        SubCategoriesFragment subCategoriesFragment = new SubCategoriesFragment ();
        Bundle args = new Bundle();
        args.putString("category", categoryItem);
        args.putString("type", "Algorithms");
        subCategoriesFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_category, subCategoriesFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
