package iti.jets.mad.tripplannerproject.model;

import java.util.ArrayList;

public class Note {
    private String noteTitle;
    private boolean isDone;

    public Note() {
    }


    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
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
