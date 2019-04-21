package iti.jets.mad.tripplannerproject.screens.addtripscreen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.model.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {
    private Context context ;
    private ArrayList<String> notesList;

    public NoteAdapter(Context _context ) {
        notesList = new ArrayList<>();
        context=_context;
    }

    public NoteAdapter(Context _context , ArrayList<String> _notesList){
        context=_context;
        notesList=_notesList;
    }

    public ArrayList<String> getNotesList() {
        return notesList;
    }

    public void setNotesList(ArrayList<String> notesList) {
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public NoteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.note_list_item, viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.MyViewHolder myViewHolder, int i) {

        // Get the clicked item label
        final String note = notesList.get(i);
        myViewHolder.txtNote.setText(note);
        myViewHolder.deleteNote.setOnClickListener(e->{

            // Remove the item on remove/button click
           notesList.remove(i);

                /*
                    public final void notifyItemRemoved (int position)
                        Notify any registered observers that the item previously located at position
                        has been removed from the data set. The items previously located at and
                        after position may now be found at oldPosition - 1.

                        This is a structural change event. Representations of other existing items
                        in the data set are still considered up to date and will not be rebound,
                        though their positions may be altered.

                    Parameters
                        position : Position of the item that has now been removed
                */
            notifyItemRemoved(i);

                /*
                    public final void notifyItemRangeChanged (int positionStart, int itemCount)
                        Notify any registered observers that the itemCount items starting at
                        position positionStart have changed. Equivalent to calling
                        notifyItemRangeChanged(position, itemCount, null);.

                        This is an item change event, not a structural change event. It indicates
                        that any reflection of the data in the given position range is out of date
                        and should be updated. The items in the given range retain the same identity.

                    Parameters
                        positionStart : Position of the first item that has changed
                        itemCount : Number of items that have changed
                */
            notifyItemRangeChanged(i,notesList.size());

            // Show the removed item label
            Toast.makeText(context,"Removed : " + note,Toast.LENGTH_SHORT).show();

        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNote;
        private Button deleteNote;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNote= itemView.findViewById(R.id.textViewNote);
            deleteNote=itemView.findViewById(R.id.buttonDeleteNote);
        }
    }
}
