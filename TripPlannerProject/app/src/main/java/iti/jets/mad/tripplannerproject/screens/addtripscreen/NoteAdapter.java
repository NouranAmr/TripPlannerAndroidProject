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

    public NoteAdapter(Context _context , ArrayList<String> _notesList){
        context=_context;
        notesList=_notesList;
    }
    @NonNull
    @Override
    public NoteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.note_list_item, viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.MyViewHolder myViewHolder, int i) {

        final String note = notesList.get(i);
        myViewHolder.txtNote.setText(note);
        myViewHolder.deleteNote.setOnClickListener(e->{

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
