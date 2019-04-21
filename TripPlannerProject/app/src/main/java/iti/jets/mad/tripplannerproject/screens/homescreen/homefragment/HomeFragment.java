package iti.jets.mad.tripplannerproject.screens.homescreen.homefragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.screens.homescreen.historyfragment.upcomingfragment.RecyclerViewAdapter;


public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);

        RecyclerView recyclerView= view.findViewById(R.id.recycleView);
        RecyclerViewAdapter recyclerViewAdapter= new RecyclerViewAdapter(getContext());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }
}
