package com.example.codeledge_v2.ui.SubCategories;

import android.content.Intent;
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
import com.example.codeledge_v2.ui.ProgramDisplay.ProgramDisplayActivity;
import com.example.codeledge_v2.ui.ProgramDisplay.ProgramDisplayFragment;

import java.util.ArrayList;
import java.util.List;

public class SubCategoriesFragment extends Fragment {
    private View root;
    private RecyclerView recyclerView;
    private DbHelper dbHelper = DbHelper.getInstance();
    private String table;
    private Bundle args = new Bundle();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_subcategories, container, false);
        UpdateRecyclerView(root);
        return root;
    }

    private void UpdateRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.subCategoriesRecycler);
        RecyclerView.Adapter mAdapter = new SubCategoriesListAdapter(loadcategories(),
                this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(mAdapter);
    }

    private List<SubCategoriesData> loadcategories() {
        tableType();
        List<SubCategoriesData> categoriesData = new ArrayList<>();
        List<String> arr = dbHelper.getNumberofCategoryElemnts(
                getArguments().getString("category"), table);
        for (String item: arr
             ) {
            categoriesData.add(new SubCategoriesData(item));
        }
        return categoriesData;
    }

    private void tableType() {
        String tableType = getArguments().getString("type");
        if (tableType.equals("Algorithms")) {
            table = "programs";
        } else if (tableType.equals("DesignPattern")) {
            table = "designpattern";
        }
    }

    public void startFragmentTransaction(View view, String categoryItem) {
        //CallActivity(view, categoryItem);
        //Put the value
        ProgramDisplayFragment programDisplayFragment = new ProgramDisplayFragment ();

        args.putString("PROGRAM_NAME", categoryItem);
        args.putString("TABLETYPE", table);

        programDisplayFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_category, programDisplayFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void CallActivity(View view, String categoryItem) {
        Intent intent = new Intent(view.getContext(), ProgramDisplayActivity.class);
        intent.putExtra("PROGRAM_NAME", categoryItem);
        view.getContext().startActivity(intent);
    }
}
