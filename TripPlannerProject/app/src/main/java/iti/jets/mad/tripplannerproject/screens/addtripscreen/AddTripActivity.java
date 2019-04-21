package iti.jets.mad.tripplannerproject.screens.addtripscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.model.Note;
import iti.jets.mad.tripplannerproject.model.Trip;


public class AddTripActivity extends AppCompatActivity implements AddTripContract.IView {

    private MenuItem logoutitem;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        logoutitem=menu.findItem(R.id.LogoutToolBarID);

        return true;
    }

    // The Entry point of the database
    private FirebaseDatabase mFirebaseDatabase;
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    String currentUserUID;
    String currentUserName;
    private FirebaseUser firebaseUser;

    private static final String TAG = "PlaceAutocomplete";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        Toolbar toolbar=findViewById(R.id.toolbarID);
        setSupportActionBar(toolbar);

        // Creating a database object
        mFirebaseDatabase= FirebaseDatabase.getInstance( );
        // [START initialize_database_ref]
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); //getcurrentuser
        currentUserUID=firebaseUser.getUid();

        Log.e("currentUserUID",currentUserUID);
        //mDatabase = mFirebaseDatabase.getReference().child("Trips").child(currentUserUID);
        // [END initialize_database_ref]

        /////place auto compleat/////
        // from
        initPlaceAutocomplete(R.id.place_autocomplete_fragment_from);
        // to
        initPlaceAutocomplete(R.id.place_autocomplete_fragment_to);
        ///////////end place auto compleat////////////


    }

    private void initPlaceAutocomplete(final int place_autocomplete_fragment)
    {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(place_autocomplete_fragment);

        AutocompleteFilter filter = new AutocompleteFilter.Builder()
                .setCountry("EG")
                .build();
        autocompleteFragment.setFilter(filter);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                if(place_autocomplete_fragment == R.id.place_autocomplete_fragment_from)
                {
                    //start Point
                }
                else
                {
                    //end Point
                }
                String placeName = place.getName().toString();
                Toast.makeText(AddTripActivity.this, placeName, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
                Toast.makeText(AddTripActivity.this, status.getStatus().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addNewTrip(String tripName, String startPoint, String endPoint, Date tripDate , Note tripNote)
    {
        Trip trip=new Trip(tripName,startPoint,endPoint,tripDate,tripNote);
        mDatabase.child(mDatabase.push().getKey()).setValue(trip);
    }

}