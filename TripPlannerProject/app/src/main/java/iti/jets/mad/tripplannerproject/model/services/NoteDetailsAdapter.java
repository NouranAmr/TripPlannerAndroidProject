package iti.jets.mad.tripplannerproject.model.services;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.model.Note;

public class NoteDetailsAdapter extends RecyclerView.Adapter<NoteDetailsAdapter.MyViewHolder> {
    private Context context ;
    private ArrayList<Note> notesList;

    public NoteDetailsAdapter(Context _context ) {
        notesList = new ArrayList<>();
        context=_context;
    }

    public NoteDetailsAdapter(Context context , ArrayList<Note> notesList){
        this.context=context;

        this.notesList=  notesList;
    }

    public ArrayList<Note> getNotesList() {
        return notesList;
    }

    public void setNotesList(ArrayList<Note> notesList) {
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public NoteDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.history_notes, viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteDetailsAdapter.MyViewHolder myViewHolder, int i) {

        // Get the clicked item label
        Note note = notesList.get(i);
        String noteTitle = note.getNoteTitle();
        myViewHolder.txtNote.setText(noteTitle);


    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNote;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNote= itemView.findViewById(R.id.textViewNoteDetails);
        }
    }
}
