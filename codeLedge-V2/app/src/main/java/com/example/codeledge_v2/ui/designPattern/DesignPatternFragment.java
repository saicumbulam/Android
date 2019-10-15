package com.example.codeledge_v2.ui.designPattern;

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
import com.example.codeledge_v2.ui.ProgramDisplay.ProgramDisplayFragment;
import com.example.codeledge_v2.ui.SubCategories.SubCategoriesFragment;

import java.util.List;

public class DesignPatternFragment extends Fragment {
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
        root = inflater.inflate(R.layout.fragment_designpattern, container, false);
        UpdateRecyclerView(root);
        return root;
    }

    private void UpdateRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.designPatternRecycler);
        RecyclerView.Adapter mAdapter = new DesignPatternListAdapter(loadDesignPatternData(), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(mAdapter);
    }

    private List<DesignPatternData> loadDesignPatternData() {
        List<DesignPatternData> designPatternData = dbHelper.getDesignPatterName();
        return designPatternData;
    }

    public void startFragmentTransaction(View view, String categoryItem) {
        SubCategoriesFragment subCategoriesFragment = new SubCategoriesFragment ();
        Bundle args = new Bundle();
        args.putString("category", categoryItem);
        args.putString("type", "DesignPattern");
        subCategoriesFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_category, subCategoriesFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
