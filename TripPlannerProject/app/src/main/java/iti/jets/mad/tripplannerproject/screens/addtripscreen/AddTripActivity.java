package iti.jets.mad.tripplannerproject.screens.addtripscreen;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.model.Note;
import iti.jets.mad.tripplannerproject.model.Trip;
import iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmbroadcast.AlertReciever;
import iti.jets.mad.tripplannerproject.screens.addtripscreen.datepicker.DatePickerFragment;
import iti.jets.mad.tripplannerproject.screens.addtripscreen.datepicker.TimePickerFragment;


public class AddTripActivity extends AppCompatActivity implements AddTripContract.IView  , TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    AddTripContract.IPresnter presenter;
    ImageButton btnTime,btnDate;
    TextView timeTxt,dateTxt;
    boolean timeFlag,dateFlag=false;
    Calendar calendar;

    TextInputEditText tripName;

    View expendedCard;
    Switch aSwitch;
    private MenuItem logoutitem;

    private RecyclerView notesRecyclerView;
    private Note note ;
    private EditText editTextNote;
    private EditText editTextTripName;
    private Button buttonAddNote;
    private ArrayList<String> notes;
    // The Entry point of the database
    private FirebaseDatabase mFirebaseDatabase;
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    String currentUserUID;
    String currentUserName;
    private FirebaseUser firebaseUser;

    private static final String TAG = "PlaceAutocomplete";

    public AddTripActivity() {
        note = new Note();
        notes=new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        logoutitem=menu.findItem(R.id.LogoutToolBarID);

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        Toolbar toolbar=findViewById(R.id.toolbarID);
        setSupportActionBar(toolbar);

        tripName=findViewById(R.id.editTextTripName);

        presenter = new AddTripPresenter(this);
        calendar=Calendar.getInstance();
        btnDate=findViewById(R.id.dateBtnID);
        btnTime=findViewById(R.id.TimebtnID);
        expendedCard=findViewById(R.id.expendedCard);
        notesRecyclerView = findViewById(R.id.recyclerViewNots);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(AddTripActivity.this));
        editTextTripName = findViewById(R.id.editTextTripName);
        editTextNote = findViewById(R.id.editTextNote);
        buttonAddNote = findViewById(R.id.buttonAddNote);
        editTextNote.setText("");
        note.setNoteTitle(editTextTripName.getText().toString());
        aSwitch = (Switch) findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    expendedCard.setVisibility(View.VISIBLE);

                } else {
                    // The toggle is disabled
                    expendedCard.setVisibility(View.GONE);
                }
            }
        });

        buttonAddNote.setOnClickListener(v->{
            if(!editTextNote.getText().toString().equals(""))
            {
                notes.add(editTextNote.getText().toString());
                editTextNote.setText("");
                presenter.setNotes(notes);
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timeFragment=new TimePickerFragment();
                timeFragment.show(getSupportFragmentManager(),"Time Picker");
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dateFragment=new DatePickerFragment();
                dateFragment.show(getSupportFragmentManager(),"Date Picker");
            }
        });


        // Creating a database object
        mFirebaseDatabase= FirebaseDatabase.getInstance( );
        // [START initialize_database_ref]
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); //getcurrentuser
        currentUserUID=firebaseUser.getUid();

        Log.e("currentUserUID",currentUserUID);
        //mDatabase = mFirebaseDatabase.getReference().child("Trips").child(currentUserUID);
        // [END initialize_database_ref]

        /////place auto compleat/////
        // Initialize Places.
        Places.initialize(getApplicationContext(), "AIzaSyDWfuuXPaayWjdUpTv-kiQogKe65Y3qr8M");
        PlacesClient placesClient = Places.createClient(AddTripActivity.this);

        // from
        initPlaceAutocomplete(R.id.place_autocomplete_fragment_from);
        // to
        initPlaceAutocomplete(R.id.place_autocomplete_fragment_to);
        ///////////end place auto compleat////////////


    }

    private void initPlaceAutocomplete(final int place_autocomplete_fragment)
    {
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(place_autocomplete_fragment);

        autocompleteFragment.setCountry("EG");
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                //Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                if(place_autocomplete_fragment == R.id.place_autocomplete_fragment_from)
                {
                    //start Point
                    place.getLatLng();

                }
                else
                {
                    //end Point
                }
                String placeName = place.getName();
                Toast.makeText(AddTripActivity.this, placeName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
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

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);

        timeFlag=true;

        if(timeFlag&&dateFlag==true)
            startAlarm(calendar);

        String currentTime= DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        timeTxt=findViewById(R.id.textView_Time);
        timeTxt.setText(currentTime);
    }
    private void startAlarm(Calendar calendar) {
        AlarmManager alarmManager =(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        AlertReciever alertReciever= new AlertReciever();
        Intent intent=new Intent(this,AlertReciever.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,intent,0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        }
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        dateFlag=true;
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDate= DateFormat.getDateInstance().format(calendar.getTime());
        dateTxt=findViewById(R.id.textView_Calender);
        dateTxt.setText(currentDate);
    }

    @Override
    public void showNotes(NoteAdapter adapter) {
        notesRecyclerView.setAdapter(adapter);
    }
}