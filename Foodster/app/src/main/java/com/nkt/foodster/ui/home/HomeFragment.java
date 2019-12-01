package com.nkt.foodster.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.nkt.foodster.Cuisine;
import com.nkt.foodster.FirebaseDatabaseHelper;
import com.nkt.foodster.R;
import com.nkt.foodster.RecyclerView_Config;

import java.util.List;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView mRecyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerview_cuisines);

        new FirebaseDatabaseHelper().readCuisines(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Cuisine> cuisines, List<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerView, getActivity(), cuisines, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });



        return root;
    }



}