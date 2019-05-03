package iti.jets.mad.tripplannerproject.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.noteTitle);
        dest.writeByte(this.isDone ? (byte) 1 : (byte) 0);
    }

    protected Note(Parcel in) {
        this.noteTitle = in.readString();
        this.isDone = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
