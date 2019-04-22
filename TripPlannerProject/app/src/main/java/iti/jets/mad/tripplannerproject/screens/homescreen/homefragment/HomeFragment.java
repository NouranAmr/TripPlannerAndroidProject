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

import com.google.firebase.database.DataSnapshot;

import java.util.List;

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.model.Note;
import iti.jets.mad.tripplannerproject.model.Trip;
import iti.jets.mad.tripplannerproject.model.TripLocation;
import iti.jets.mad.tripplannerproject.screens.homescreen.historyfragment.upcomingfragment.RecyclerViewAdapter;


public class HomeFragment extends Fragment {

    private RecyclerViewAdapter recyclerViewAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);

        RecyclerView recyclerView= view.findViewById(R.id.recycleView);
        recyclerViewAdapter= new RecyclerViewAdapter(getContext());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }
    public void fillNoteList(DataSnapshot dataSnapshot) {
        Trip trip=null;
        for(DataSnapshot snapshot :dataSnapshot.getChildren())
        {
            TripLocation startLocation =(TripLocation) snapshot.child("startLocation").getValue();
            TripLocation endLocation =(TripLocation) snapshot.child("endLocation").getValue();
            long timeStamp= Long.valueOf(snapshot.child("timeStamp").getValue().toString());
            String tripName=snapshot.child("tripName").getValue().toString();
            List<Note> tripNote=(List<Note>) snapshot.child("tripName").getValue();
            trip=new Trip(tripName,startLocation,endLocation,timeStamp,tripNote);
            recyclerViewAdapter.(trip);
            //arrayAdapter.add(dataSnapshot.getValue(Note.class));
        }
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerViewAdapter.setAdapter(arrayAdapter);

    }
}
