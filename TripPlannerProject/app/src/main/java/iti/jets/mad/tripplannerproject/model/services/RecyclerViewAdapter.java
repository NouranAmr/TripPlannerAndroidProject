package iti.jets.mad.tripplannerproject.model.services;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.model.Trip;
import iti.jets.mad.tripplannerproject.screens.homescreen.homefragment.HomeFragmentContract;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> myImages = new ArrayList<>();
    private ArrayList<String> myNames = new ArrayList<>();
    private Context context;
    private ArrayList<Trip>tripArrayList;
    public RecyclerViewAdapter(Context context,ArrayList<Trip>tripArrayList) {

        this.context = context;
        //Activity a= (Activity) context;
        this.tripArrayList = tripArrayList;
    }

    public RecyclerViewAdapter(Context context) {

        tripArrayList = new ArrayList<>();
        this.context = context;
    }

    public void updateList(ArrayList<Trip>tripArrayList){
        this.tripArrayList = tripArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_list,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
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

       if (tripArrayList.size()>0){

           viewHolder.tripImageCircularImageView.setVisibility(View.VISIBLE);
           viewHolder.tripNameTextView.setVisibility(View.VISIBLE);
           viewHolder.fromTextView.setVisibility(View.VISIBLE);
           viewHolder.dateTextView.setVisibility(View.VISIBLE);
           viewHolder.editButton.setVisibility(View.VISIBLE);

           viewHolder.tripImageCircularImageView.setImageResource(R.drawable.img);
           viewHolder.tripNameTextView.setText(tripArrayList.get(position).getTripName());
           viewHolder.fromTextView.append(tripArrayList.get(position).getStartLocation().getPointName());
           viewHolder.toTextView.setText("To: ");
           viewHolder.toTextView.append(tripArrayList.get(position).getEndLocation().getPointName());
           viewHolder.dateTextView.append(tripArrayList.get(position).getDateTimeStamp() + "\n" + tripArrayList.get(position).getTimeTimeStamp());
           viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   PopupMenu popupMenu = new PopupMenu(context, viewHolder.editButton);
                   popupMenu.inflate(R.menu.buttonmenuitems);
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

                                   showMap(Uri.parse(uri));
                                   Toast.makeText(context, "Started", Toast.LENGTH_LONG).show();
                                   break;
                               case R.id.editButton:
                                   //Delete item
                                   Toast.makeText(context, "Edited", Toast.LENGTH_LONG).show();
                                   break;
                               case R.id.deleteItem:
                                   //Delete item
                                   DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {
                                           switch (which) {
                                               case DialogInterface.BUTTON_POSITIVE:
                                                   Toast.makeText(context, "delete", Toast.LENGTH_LONG).show();
                                                   break;

                                               case DialogInterface.BUTTON_NEGATIVE:
                                                   Toast.makeText(context, "no", Toast.LENGTH_LONG).show();
                                                   break;
                                           }
                                       }
                                   };
                                   AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                   builder.setMessage("Are you sure you want to delete?").setPositiveButton("Yes", dialogClickListener)
                                           .setNegativeButton("No", dialogClickListener).show();

                                   break;
                               case R.id.noteItem:
                                   //Delete item
                                   Toast.makeText(context, "notes", Toast.LENGTH_LONG).show();
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

    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        if(tripArrayList.size()==0)
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
}
