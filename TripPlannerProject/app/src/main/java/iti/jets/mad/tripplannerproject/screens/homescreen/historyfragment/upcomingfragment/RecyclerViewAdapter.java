package iti.jets.mad.tripplannerproject.screens.homescreen.historyfragment.upcomingfragment;

import android.content.Context;
import android.content.DialogInterface;
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

import java.util.ArrayList;

import iti.jets.mad.tripplannerproject.R;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> myImages = new ArrayList<>();
    private ArrayList<String> myNames = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(Context context) {

        this.context = context;
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
       viewHolder.tripImageCircularImageView.setImageResource(R.drawable.img);
       viewHolder.tripNameTextView.append(" From Recycler");
       viewHolder.fromTextView.append(" Cairo");
       viewHolder.toTextView.append(" Alex");
       viewHolder.dateTextView.append(" Thursday");
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
                                       switch (which){
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



    }

    @Override
    public int getItemCount() {
        return 5;
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
