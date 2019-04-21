package iti.jets.mad.tripplannerproject.model;

import java.util.ArrayList;

public class Note {
    private String noteTitle;
    private ArrayList <String> notes;
    private boolean isDone;

    public Note() {
    }

    public Note(String noteTitle, ArrayList<String> notes) {
        this.noteTitle = noteTitle;
        this.notes = notes;
        this.isDone=false;
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

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public String toString() {
        return noteTitle;
    }
}
