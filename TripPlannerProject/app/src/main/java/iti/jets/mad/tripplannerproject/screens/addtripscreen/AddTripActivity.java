package iti.jets.mad.tripplannerproject.screens.addtripscreen;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import com.google.android.gms.maps.model.LatLng;
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

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.model.Note;
import iti.jets.mad.tripplannerproject.model.Trip;
import iti.jets.mad.tripplannerproject.model.TripLocation;
import iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmbroadcast.AlertReciever;
import iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmbroadcast.BroadCastAlert;
import iti.jets.mad.tripplannerproject.screens.addtripscreen.datepicker.DatePickerFragment;
import iti.jets.mad.tripplannerproject.screens.addtripscreen.datepicker.TimePickerFragment;
import iti.jets.mad.tripplannerproject.screens.homescreen.HomeActivity;


public class AddTripActivity extends AppCompatActivity implements AddTripContract.IView, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private AddTripContract.IPresnter presenter;

    private static final String TAG = "PlaceAutocomplete";

    private ImageButton btnTime, btnDate, timeBtn2, calendarBtn2;
    private TextView timeTxt, dateTxt, timeTxt2, calendarTxt2;
    private Calendar calendar, secondCalendar;
    private Button addTripBtn, buttonAddNote;
    private TextInputEditText tripName;
    private View expendedCard;
    private Switch aSwitch;
    private MenuItem logoutitem;
    private RecyclerView notesRecyclerView;
    private EditText editTextNote;

    // The Entry point of the database
    private FirebaseDatabase mFirebaseDatabase;
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private FirebaseUser firebaseUser;

    private Trip trip;
    private TripLocation startLocation, endLocation;
    private Note note;

    private ArrayList<Note> notes;
    private boolean firstTripFlagTime, firstTripFlagDate,
            secondTripFlagTime, secondTripFlagDate = false,
            timeFlag, dateFlag = false,
            check1, check2 = false;
    private String userID, tripKey = null;

    public AddTripActivity() {
        note = new Note();
        notes = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        logoutitem = menu.findItem(R.id.LogoutToolBarID);

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        Toolbar toolbar = findViewById(R.id.toolbarID);
        toolbar.setTitle("New Trip");
        setSupportActionBar(toolbar);

        presenter = new AddTripPresenter(this);
        calendar = Calendar.getInstance();

        dateTxt = findViewById(R.id.textView_Calender);
        timeTxt = findViewById(R.id.textView_Time);
        tripName = findViewById(R.id.editTextTripName);
        addTripBtn = findViewById(R.id.Button_OK);
        btnDate = findViewById(R.id.dateBtnID);
        btnTime = findViewById(R.id.TimebtnID);
        expendedCard = findViewById(R.id.expendedCard);
        notesRecyclerView = findViewById(R.id.recyclerViewNots);
        editTextNote = findViewById(R.id.editTextNote);
        buttonAddNote = findViewById(R.id.buttonAddNote);
        timeBtn2 = findViewById(R.id.TimebtnID2);
        calendarBtn2 = findViewById(R.id.dateBtnID2);
        timeTxt2 = findViewById(R.id.textView_Time2);
        aSwitch = findViewById(R.id.switch1);
        calendarTxt2 = findViewById(R.id.textView_Calender2);

        //get current user
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseUser.getUid();
        // database settings
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference("Trips").child(userID);//get each user

        notesRecyclerView.setLayoutManager(new LinearLayoutManager(AddTripActivity.this));
        editTextNote.setText("");
        note.setNoteTitle(tripName.getText().toString());

        aSwitch.setClickable(true);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    expendedCard.setVisibility(View.VISIBLE);
                    secondCalendar = Calendar.getInstance();


                } else {
                    // The toggle is disabled
                    expendedCard.setVisibility(View.GONE);
                }
            }
        });

        buttonAddNote.setOnClickListener(v -> {
            if (!editTextNote.getText().toString().equals("")) {
                Note userNote = new Note();
                userNote.setNoteTitle(editTextNote.getText().toString());
                notes.add(userNote);
                editTextNote.setText("");

            }
            presenter.setNotes(notes);
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstTripFlagTime = true;
                DialogFragment timeFragment;
                if (getIntent().hasExtra("editItem")) {
                    String time = trip.getTimeTimeStamp();
                    String[] timeSplit = time.split(":");
                    timeFragment = new TimePickerFragment(Integer.parseInt(timeSplit[0]), Integer.parseInt(timeSplit[1].split(" ")[0]), timeSplit[1].split(" ")[1]);
                } else
                    timeFragment = new TimePickerFragment();
                timeFragment.show(getSupportFragmentManager(), "Time Picker");
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstTripFlagDate = true;
                DialogFragment dateFragment;

                dateFragment = new DatePickerFragment();
                dateFragment.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        timeBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondTripFlagTime = true;
                DialogFragment timeFragment = new TimePickerFragment();
                timeFragment.show(getSupportFragmentManager(), "Time Picker");

            }
        });

        calendarBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondTripFlagDate = true;
                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.show(getSupportFragmentManager(), "Date Picker");
            }
        });


        addTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (timeFlag && dateFlag) {
                    startAlarm(calendar);
                    saveTripToFireBaseDatabase(tripName.getText().toString(), startLocation, endLocation, calendar, notes, tripKey);
                }


                if (check1 && check2) {
                    startSecondAlarm(secondCalendar);
                    saveTripToFireBaseDatabase(tripName.getText().toString(), endLocation, startLocation, secondCalendar, notes, tripKey);
                    secondTripFlagDate = false;
                    secondTripFlagTime = false;
                    check1 = false;
                    check2 = false;
                }

                startActivity(new Intent(AddTripActivity.this, HomeActivity.class));
            }
        });


        /////place auto complete/////
        // Initialize Places.
        Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        PlacesClient placesClient = Places.createClient(AddTripActivity.this);

        if (getIntent().hasExtra("editItem")) {
            trip = getIntent().getParcelableExtra("trip");
            startLocation = trip.getStartLocation();
            endLocation = trip.getEndLocation();
            tripName.setText(trip.getTripName());
            calendar.setTimeInMillis(trip.getTimeStamp());
            dateTxt.setText(DateFormat.getDateInstance().format(calendar.getTime()));
            timeTxt.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime()));
            notes = trip.getTripNote();
            presenter.setNotes(notes);
            addTripBtn.setText("Update Trip");
            tripKey = getIntent().getStringExtra("tripKey");
            dateFlag = true;
            timeFlag = true;
            aSwitch.setClickable(false);
        }
        // from
        initPlaceAutocomplete(R.id.place_autocomplete_fragment_from);
        // to
        initPlaceAutocomplete(R.id.place_autocomplete_fragment_to);
        ///////////end place auto complete////////////


    }

    private void initPlaceAutocomplete(final int place_autocomplete_fragment) {
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(place_autocomplete_fragment);

       // autocompleteFragment.setCountry("EG");
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        if (getIntent().hasExtra("editItem")) {
            if (place_autocomplete_fragment == R.id.place_autocomplete_fragment_from) {

                autocompleteFragment.setText(trip.getStartLocation().getPointName());

            } else {

                autocompleteFragment.setText(trip.getEndLocation().getPointName());
            }
        }

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                //Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                if (place_autocomplete_fragment == R.id.place_autocomplete_fragment_from) {
                    //start Point

                    startLocation = new TripLocation();
                    startLocation.setPointName(place.getName());
                    startLocation.setLat(place.getLatLng().latitude);
                    startLocation.setLng(place.getLatLng().longitude);

                } else {
                    //end Point

                    endLocation = new TripLocation();
                    endLocation.setPointName(place.getName());
                    endLocation.setLat(place.getLatLng().latitude);
                    endLocation.setLng(place.getLatLng().longitude);
                }
                String placeName = place.getName();
                LatLng latLng = place.getLatLng();
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

    private void saveTripToFireBaseDatabase(String tripName, TripLocation startLocation, TripLocation endLocation, Calendar calendar, ArrayList<Note> tripNote, String tripKey) {
        if (!(tripName.equals("")) && !(startLocation.equals(null)) && !(endLocation.equals(null)) && !(calendar.equals(null)) && !(tripNote.size() == 0)) {
            writeNewTrip(tripName, startLocation, endLocation, calendar, tripNote, tripKey);
            Toast.makeText(AddTripActivity.this, "Trip Saved", Toast.LENGTH_SHORT).show();

        }
    }

    private void writeNewTrip(String tripName, TripLocation startLocation, TripLocation endLocation, Calendar calendar, ArrayList<Note> tripNote, String tripKey) {


        try {
            Trip tripAdd = new Trip(tripName, startLocation, endLocation, calendar.getTimeInMillis(), tripNote);
            //  System.out.println("Current time of the day using Date - 12 hour format: " + formattedDate);

            if (getIntent().hasExtra("editItem")) {
                tripAdd.setTripKey(tripKey);
                mDatabase.child(tripKey).setValue(tripAdd);
            } else {
                tripAdd.setTripKey(mDatabase.push().getKey());
                mDatabase.child(tripAdd.getTripKey()).setValue(tripAdd);
            }


        } catch (Exception e) {
            String m = e.getMessage();
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (firstTripFlagTime) {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);

            timeFlag = true;


            String currentTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
            timeTxt.setText(currentTime);
            firstTripFlagTime = false;
        }


        if (secondTripFlagTime) {
            secondCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            secondCalendar.set(Calendar.MINUTE, minute);
            secondCalendar.set(Calendar.SECOND, 0);
            String currentTime2 = DateFormat.getTimeInstance(DateFormat.SHORT).format(secondCalendar.getTime());
            timeTxt2.setText(currentTime2);

            check1 = true;
            secondTripFlagTime = false;
        }

    }

    private void startAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReciever.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("tripBundle",new Trip(tripName.getText().toString(),  startLocation, endLocation, 0 , notes));
        intent.putExtra("trip",bundle);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);


        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void startSecondAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, BroadCastAlert.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("tripBundle",new Trip(tripName.getText().toString(),  startLocation, endLocation, 0 , notes));
        intent.putExtra("trip",bundle);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (firstTripFlagDate) {
            dateFlag = true;
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
            dateTxt.setText(currentDate);
            firstTripFlagDate = false;
        }

        if (secondTripFlagDate) {

            secondCalendar.set(Calendar.YEAR, year);
            secondCalendar.set(Calendar.MONTH, month);
            secondCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String currentDate2 = DateFormat.getDateInstance().format(secondCalendar.getTime());
            calendarTxt2.setText(currentDate2);
            calendarTxt2.setText(currentDate2);
            check2 = true;
            secondTripFlagDate = false;
        }
    }

    @Override
    public void showNotes(NoteAdapter adapter) {
        notesRecyclerView.setAdapter(adapter);
    }
}