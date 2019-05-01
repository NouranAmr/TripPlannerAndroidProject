package iti.jets.mad.tripplannerproject.model.services;

import android.app.Service;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.model.Note;
import iti.jets.mad.tripplannerproject.model.Trip;
import iti.jets.mad.tripplannerproject.screens.homescreen.HomeActivity;

/**
 * Created by Jaink on 26-07-2017.
 */

public class FloatingService extends Service {
    private WindowManager mWindowManager;
    private View mFloatingView;
    private ArrayList<Trip> tripArrayList = null;
    ArrayList<Note> notes;
    Handler handler;
    public FloatingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        int layout_parms;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)

        {
            layout_parms = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;

        }

        else {

            layout_parms = WindowManager.LayoutParams.TYPE_PHONE;

        }

        //Inflate the floating view layout we created
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.floatingview_layout, null);

        //Add the view to the window.

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layout_parms,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                PixelFormat.TRANSLUCENT);

        //Specify the view position
        params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 100;

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);

//The root element of the collapsed view layout
        final View collapsedView = mFloatingView.findViewById(R.id.collapse_view);
        //The root element of the expanded view layout
        final View expandedView = mFloatingView.findViewById(R.id.expanded_container);

        //Set the close button on floating widget
        ImageView closeButtonCollapsed = (ImageView) mFloatingView.findViewById(R.id.close_btn);
        closeButtonCollapsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close the service and remove the from from the window
                stopSelf();
            }
        });
        final LinearLayout displayNote = (LinearLayout) mFloatingView.findViewById(R.id.displayNote);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 10;
        layoutParams.bottomMargin=10;
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                ArrayList<Note> myNotes = msg.getData().getParcelableArrayList("Notes");

                final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
                if(myNotes != null)
                {
                    for (int i = 0 ; i<myNotes.size() ; i++)
                    {
                        CheckBox cb = new CheckBox(getApplicationContext());
                        final Note pojo = mapper.convertValue(myNotes.get(i), Note.class);
                        String title = pojo.getNoteTitle();
                        cb.setText(title);
                        cb.setTextSize(18);
                        cb.setTypeface(Typeface.DEFAULT_BOLD);
                        cb.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                        cb.setTextColor(getResources().getColor(R.color.colorPrimary));
                        cb.setChecked(pojo.isDone());
                        // cb.setGravity(15);
                        displayNote.addView(cb,layoutParams);
                    }
                }
            }
        };
       /* //display typed message.
        final LinearLayout displayNote = (LinearLayout) mFloatingView.findViewById(R.id.displayNote);

       for (int i = 0 ; i<notes.size() ; i++)
        {
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(notes.get(i).getNoteTitle());
            cb.setTextSize(18);
            cb.setTextColor(getResources().getColor(R.color.colorPrimary));
            cb.setEnabled(notes.get(i).isDone());
            //cb.setGravity(10);
            displayNote.addView(cb);
        }*/


//        Intent intent = new Intent();
//        intent.getExtras().get("Nots");
//        ArrayList<Note> notes = intent.getParcelableArrayListExtra("Nots");


//            for(int i=0 ; i<notes.size() ;i++)
//            {
//
//            }
      /*  // while floating view is expanded.
        //type message.
        final EditText typetext = (EditText) mFloatingView.findViewById(R.id.edittext);
        // Request focus and show soft keyboard automatically



        ImageView senttext = (ImageView ) mFloatingView.findViewById(R.id.sendText);
        senttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("float "," image send click");
                displaytext.setText(typetext.getText().toString());
                displaytext.setVisibility(View.VISIBLE);
            }
        });*/


        //Set the close button on expanded view
        ImageView closeButton = (ImageView) mFloatingView.findViewById(R.id.close_exp_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collapsedView.setVisibility(View.VISIBLE);
                expandedView.setVisibility(View.GONE);
            }
        });


        //Open the application on open button click on expanded view
        ImageView openButton = (ImageView) mFloatingView.findViewById(R.id.open_exp_button);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open main activity
                Intent intent = new Intent(FloatingService.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


                //close the service and remove view from the view hierarchy
                stopSelf();
            }
        });

        //Drag and move floating view using user's touch action.
        mFloatingView.findViewById(R.id.root_container).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        //remember the initial position.
                        initialX = params.x;
                        initialY = params.y;

                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);


                        //The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while clicking.
                        //So that is click event.
                        if (Xdiff < 10 && Ydiff < 10) {
                            if (isViewCollapsed()) {
                                //When user clicks on the image view of the collapsed layout,
                                //visibility of the collapsed layout will be changed to "View.GONE"
                                //and expanded view will become visible.
                                collapsedView.setVisibility(View.GONE);
                                expandedView.setVisibility(View.VISIBLE);
                                // typetext.requestFocus();

                                // attach keypad with edittext
                                // InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                // imm.showSoftInput(typetext, InputMethodManager.SHOW_IMPLICIT);

                            }
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);


                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //notes = intent.getParcelableArrayListExtra("Nots");
        tripArrayList = intent.getParcelableArrayListExtra("TripList");
        int tripIndex = intent.getIntExtra("tripIndex",0);
        notes = (ArrayList<Note>) tripArrayList.get(tripIndex).getTripNote();
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("Notes", notes);
        msg.setData(bundle);
        handler.sendMessage(msg);
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * Detect if the floating view is collapsed or expanded.
     *
     * @return true if the floating view is collapsed.
     */
    private boolean isViewCollapsed() {
        return mFloatingView == null || mFloatingView.findViewById(R.id.collapse_view).getVisibility() == View.VISIBLE;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
    }
}