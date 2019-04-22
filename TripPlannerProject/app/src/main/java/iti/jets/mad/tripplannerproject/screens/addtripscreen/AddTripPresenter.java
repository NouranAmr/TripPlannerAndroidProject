package iti.jets.mad.tripplannerproject.screens.addtripscreen;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import iti.jets.mad.tripplannerproject.model.Note;

public class AddTripPresenter  implements AddTripContract.IPresnter {

    AddTripContract.IView activity;
    NoteAdapter noteAdapter;

    public AddTripPresenter( AddTripContract.IView activity){
        this.activity = activity;
        noteAdapter = new NoteAdapter((Context) activity);
    }
    @Override
    public void setNotes(ArrayList<Note> note) {
        noteAdapter.setNotesList(note);
        activity.showNotes(noteAdapter);
    }
}
