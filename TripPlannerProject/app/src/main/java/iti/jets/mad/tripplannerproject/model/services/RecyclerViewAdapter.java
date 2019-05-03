package iti.jets.mad.tripplannerproject.model.services;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.model.Trip;

import iti.jets.mad.tripplannerproject.screens.addtripscreen.AddTripActivity;
import iti.jets.mad.tripplannerproject.screens.addtripscreen.floatingView;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private final FirebaseDatabase firebaseDatabase;
    private final FirebaseUser firebaseUser;
    private final FirebaseDatabase mFirebaseDatabase;
    private final DatabaseReference mDatabase;
    private String userID;
    private ArrayList<String> myImages = new ArrayList<>();
    private ArrayList<String> myNames = new ArrayList<>();
    private Context context;
    private ArrayList<Trip>tripArrayList = null;
    private boolean flag;
    public RecyclerViewAdapter(Context context,boolean flag) {


        this.context = context;
        this.flag=flag;
        //Activity a= (Activity) context;
        firebaseDatabase= FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); //getcurrentuser
        userID=firebaseUser.getUid();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference("Trips").child(userID);


    }
    public void updateList(ArrayList<Trip>tripArrayList){
        this.tripArrayList = tripArrayList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        Log.e(TAG,"onBindViewHolder:called.");
       /* viewHolder.textView.setText(myNames.get(position));
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick :clicked on "+myNames.get(position));

            }
        });*/

        if (tripArrayList == null){

        }
        else if (tripArrayList.size()>0){

            viewHolder.tripImageCircularImageView.setVisibility(View.VISIBLE);
            viewHolder.tripNameTextView.setVisibility(View.VISIBLE);
            viewHolder.fromTextView.setVisibility(View.VISIBLE);
            viewHolder.dateTextView.setVisibility(View.VISIBLE);
            viewHolder.editButton.setVisibility(View.VISIBLE);
            //default image
           // viewHolder.tripImageCircularImageView.setImageResource(R.drawable.ic_globe_grid);
            //load map image using retrofit glide
            String photo_url_str ="https://maps.googleapis.com/maps/api/staticmap?";
            photo_url_str+="&zoom=15";
            photo_url_str+="&size=150x150";
            photo_url_str+="&maptype=roadmap";
            photo_url_str+="&markers=color:purple%7Clabel:G%7C"+tripArrayList.get(position).getEndLocation().getLat()+", "+tripArrayList.get(position).getEndLocation().getLng();
            photo_url_str+="&key="+ context.getString(R.string.google_api_key);
                Glide.with(context)
                        .load(photo_url_str)
                        .placeholder(R.drawable.ic_globe_grid)
                        .error(R.drawable.ic_globe_grid)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(viewHolder.tripImageCircularImageView);

            viewHolder.tripNameTextView.setText(tripArrayList.get(position).getTripName());
            viewHolder.fromTextView.setText("from: "+tripArrayList.get(position).getStartLocation().getPointName());
            viewHolder.toTextView.setText("To: "+tripArrayList.get(position).getEndLocation().getPointName());

            viewHolder.dateTextView.setText("Date :"+tripArrayList.get(position).getDateTimeStamp() + "\n" + tripArrayList.get(position).getTimeTimeStamp());
            viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context, viewHolder.editButton);
                    if(flag) {
                        popupMenu.inflate(R.menu.buttonmenuitems);
                    }
                    else if(!flag)
                    {
                        popupMenu.inflate(R.menu.notesmenuitem);
                    }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.startItem:
                                    LatLng from = new LatLng(tripArrayList.get(position).getStartLocation().getLat(),tripArrayList.get(position).getStartLocation().getLng());
                                    LatLng to = new LatLng(tripArrayList.get(position).getEndLocation().getLat(),tripArrayList.get(position).getEndLocation().getLng());

                                    // from current location to
                                    //String uri = String.format(Locale.ENGLISH,  "google.navigation:q=%f,%f",from.latitude, from.longitude);

                                    //from loc1 to loc2
                                    String uri = "http://maps.google.com/maps?saddr=" + from.latitude + "," + from.longitude + "&daddr=" + to.latitude + "," + to.longitude;
                                    Intent intentFloating=new Intent(context, floatingView.class);
                                    intentFloating.putExtra("URI",uri);
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("tripBundle",tripArrayList.get(position));
                                    intentFloating.putExtra("trip",bundle);
                                    //intentFloating.putExtra("TripList",tripArrayList);
                                    //intentFloating.putExtra("tripIndex",position);
                                    context.startActivity(intentFloating);
                                    //showMap(Uri.parse(uri));
                                    //Toast.makeText(context, "Started", Toast.LENGTH_LONG).show();
                                    break;
                                case R.id.editItem:
                                    //Delete item
                                    Intent intent=new Intent(context, AddTripActivity.class);
                                    intent.putExtra("trip",tripArrayList.get(position));
                                    intent.putExtra("editItem",true);
                                    intent.putExtra("tripKey",tripArrayList.get(position).getTripKey());
                                    context.startActivity(intent);
                                    break;
                                case R.id.deleteItem:
                                    //Delete item
                                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:

                                                    mDatabase.child(tripArrayList.get(position).getTripKey()).setValue(null);
                                                    RecyclerViewAdapter.this.notifyDataSetChanged();
                                                    Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show();
                                                    break;

                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    break;
                                            }
                                        }
                                    };
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage("Are you sure you want to delete "+ tripArrayList.get(position).getTripName()+"?").setPositiveButton("Yes", dialogClickListener)
                                            .setNegativeButton("No", dialogClickListener).show();

                                    break;
                                case R.id.noteItem:
                                    //Delete item
                                    Dialog notesHistoryDialog = new Dialog(context);
                                    notesHistoryDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    notesHistoryDialog.setContentView(R.layout.notes_details);

                                    RecyclerView notesHistoryRecyclerView = notesHistoryDialog.findViewById(R.id.notesDetailsRecyclerView);

                                    notesHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(context));

                                    NoteDetailsAdapter noteDetailsAdapter = new NoteDetailsAdapter(context,tripArrayList.get(position).getTripNote());

                                    notesHistoryRecyclerView.setAdapter(noteDetailsAdapter);
                                    notesHistoryDialog.show();

                                    break;


                                default:
                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });

        }else {
            viewHolder.tripImageCircularImageView.setVisibility(View.INVISIBLE);

            viewHolder.tripNameTextView.setVisibility(View.INVISIBLE);

            viewHolder.fromTextView.setVisibility(View.INVISIBLE);
            viewHolder.dateTextView.setVisibility(View.INVISIBLE);
            viewHolder.editButton.setVisibility(View.INVISIBLE);

            viewHolder.toTextView.setText("No upcoming trips.");
        }

    }

   /* public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }*/

    @Override
    public int getItemCount() {

        if (tripArrayList==null){
            return 0;
        }
        else if (tripArrayList.size()==0)
            return 1;
        else
            return tripArrayList.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{

        ImageView tripImageCircularImageView;
        TextView tripNameTextView,fromTextView,toTextView,dateTextView;
        Button editButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tripImageCircularImageView=itemView.findViewById(R.id.tripImageCircularImageView);
            tripNameTextView=itemView.findViewById(R.id.tripNameTextView);
            fromTextView=itemView.findViewById(R.id.fromTextView);
            toTextView = itemView.findViewById(R.id.toTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }
    public void setList(ArrayList<Trip>tripArrayList){
        this.tripArrayList=tripArrayList;
    }




}
