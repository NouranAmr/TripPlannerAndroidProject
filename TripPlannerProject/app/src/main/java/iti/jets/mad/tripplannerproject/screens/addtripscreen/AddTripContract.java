package iti.jets.mad.tripplannerproject.screens.addtripscreen;

import java.util.ArrayList;

import iti.jets.mad.tripplannerproject.model.Note;

public interface AddTripContract {

    public interface IModel{

    }
    public interface IPresnter{
        void setNotes(ArrayList<Note> notes);

    }
    public interface IView{
        void showNotes(NoteAdapter adapter);

    }
}
