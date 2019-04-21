package iti.jets.mad.tripplannerproject.model;

import java.util.ArrayList;

public class Note {
    private String noteTitle;
    private ArrayList <String> notes;

    public Note() {
    }

    public Note(String noteTitle, ArrayList<String> notes) {
        this.noteTitle = noteTitle;
        this.notes = notes;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public ArrayList<String> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }


    @Override
    public String toString() {
        return noteTitle;
    }
}
