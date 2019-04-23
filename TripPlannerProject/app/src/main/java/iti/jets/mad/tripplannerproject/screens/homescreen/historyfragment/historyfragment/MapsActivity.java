package iti.jets.mad.tripplannerproject.screens.homescreen.historyfragment.historyfragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.model.Note;
import iti.jets.mad.tripplannerproject.model.Trip;
import iti.jets.mad.tripplannerproject.model.TripLocation;
import iti.jets.mad.tripplannerproject.screens.addtripscreen.AddTripActivity;
import iti.jets.mad.tripplannerproject.screens.homescreen.HomeActivity;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<TripLocation> markerPoints;
    private FloatingActionButton floatingActionButton;
    ArrayList<Trip> tripArrayList;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;

    public  MapsActivity(){
        markerPoints = new ArrayList<>();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        floatingActionButton=findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(MapsActivity.this, AddTripActivity.class);
                startActivity(intent);*/
                finish();
            }
        });

        ////read from firebase

        tripArrayList = new ArrayList<>();

        firebaseDatabase= FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); //getcurrentuser
        String userID=firebaseUser.getUid();
        databaseReference=firebaseDatabase.getReference("Trips").child(userID);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                   fillDataList(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        for(int i=0 ; i<tripArrayList.size() ; i++)
        {

            String markerColor = generateColor();
            BitmapDescriptor marker = getMarkerIcon(markerColor);
            LatLng fromLatLang = new LatLng(tripArrayList.get(i).getStartLocation().getLat(),tripArrayList.get(i).getStartLocation().getLng());
            String fromTitle = tripArrayList.get(i).getStartLocation().getPointName();
            mMap.addMarker(new MarkerOptions().position(fromLatLang).title(fromTitle).icon(marker));

            LatLng toLatLang = new LatLng(tripArrayList.get(i).getEndLocation().getLat(),tripArrayList.get(i).getEndLocation().getLng());
            String toTitle = tripArrayList.get(i).getEndLocation().getPointName();
            mMap.addMarker(new MarkerOptions().position(toLatLang).title(toTitle).icon(marker));


            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(fromLatLang, toLatLang)
                    .width(5)
                    .color(Color.parseColor(markerColor)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(fromLatLang));
        }

    }

    public void fillDataList(DataSnapshot dataSnapshot) {
        Trip trip=null;

        for(DataSnapshot snapshot :dataSnapshot.getChildren())
        {
            TripLocation startLocation =snapshot.child("startLocation").getValue(TripLocation.class);
            TripLocation endLocation = snapshot.child("endLocation").getValue(TripLocation.class);
            long timeStamp= Long.valueOf(snapshot.child("timeStamp").getValue().toString());
            String tripName=snapshot.child("tripName").getValue().toString();
            List<Note> tripNote=(List<Note>) snapshot.child("tripNote").getValue();
            trip=new Trip(tripName,startLocation,endLocation,timeStamp,tripNote);
            tripArrayList.add(trip);
            //arrayAdapter.add(dataSnapshot.getValue(Note.class));
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


      /*  LatLng sydney = new LatLng(51.5, -0.1);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").icon(marker));

        LatLng location2 = new LatLng(40.7, -74.0);
        mMap.addMarker(new MarkerOptions().position(location2).title("Marker in Location2").icon(marker));
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(sydney, location2)
                .width(5)
                .color(Color.parseColor(markerColor)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */

        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));
       /* // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));*/
    }


    private static String generateColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        final char [] hex = { '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        char [] s = new char[7];
        s[0] = '#';
        for (int i=1;i<7;i++) {
            s[i] = hex[color & 0xf];
            color >>= 4;
        }
        return new String(s);
    }
    /*
        private static String generateColor(Random r) {
            final char [] hex = { '0', '1', '2', '3', '4', '5', '6', '7',
                    '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
            char [] s = new char[7];
            int     n = r.nextInt(0x1000000);

            s[0] = '#';
            for (int i=1;i<7;i++) {
                s[i] = hex[n & 0xf];
                n >>= 4;
            }
            return new String(s);
        }
        */
    // method definition
    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }
}
