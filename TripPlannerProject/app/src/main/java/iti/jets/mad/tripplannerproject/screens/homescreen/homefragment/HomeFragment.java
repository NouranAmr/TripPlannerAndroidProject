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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.model.Note;
import iti.jets.mad.tripplannerproject.model.Trip;
import iti.jets.mad.tripplannerproject.model.TripLocation;
import iti.jets.mad.tripplannerproject.model.services.RecyclerViewAdapter;


public class HomeFragment extends Fragment implements HomeFragmentContract.IView {

    private HomeFragmentContract.IPresnter presenter;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private static ArrayList<Trip> tripArrayList, tripHistoryArrayList;
    private static int size = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycleView);
        tripArrayList = new ArrayList<>();
        tripHistoryArrayList = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); //getcurrentuser
        String userID = firebaseUser.getUid();
        databaseReference = firebaseDatabase.getReference("Trips").child(userID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                fillTripList(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);


        return view;
    }

    private void fillTripList(DataSnapshot dataSnapshot) {


        Trip trip = null;
        tripArrayList = new ArrayList<>();
        tripHistoryArrayList = new ArrayList<>();

        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

            TripLocation startLocation = snapshot.child("startLocation").getValue(TripLocation.class);
            TripLocation endLocation = snapshot.child("endLocation").getValue(TripLocation.class);
            long timeStamp = Long.valueOf(snapshot.child("timeStamp").getValue().toString());
            String tripName = snapshot.child("tripName").getValue().toString();
            // trip.setTripKey(snapshot.getKey());
            GenericTypeIndicator<ArrayList<Note>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<Note>>() {
            };
            ArrayList<Note> tripNote = snapshot.child("tripNote").getValue(genericTypeIndicator);

            trip = new Trip(tripName, startLocation, endLocation, timeStamp, tripNote);
            trip.setTripKey(snapshot.child("tripKey").getValue().toString());


            boolean timeNowBefore = new Time(System.currentTimeMillis()).before(new Date(trip.getTimeStamp()));
            boolean dateNowBefore = new Date().before(new Date(trip.getTimeStamp()));

            if (dateNowBefore || timeNowBefore) {

                tripArrayList.add(trip);

            } else tripHistoryArrayList.add(trip);


        }
       /* HomeActivity homeActivity = (HomeActivity) getActivity();
        homeActivity.receiveList(tripArrayList);*/

        recyclerViewAdapter.setList(tripArrayList);
        recyclerViewAdapter.notifyDataSetChanged();

    }

    public ArrayList<Trip> getTripArrayList() {
        return tripArrayList;
    }

    public ArrayList<Trip> getTripHistoryArrayList() {
        return tripHistoryArrayList;
    }
}
