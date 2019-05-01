package iti.jets.mad.tripplannerproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class Trip implements Parcelable {

    private String tripName;
    private long timeStamp;
    private ArrayList<Note> tripNote;
    private String markerColor;


    private String tripKey;


    private TripLocation startLocation, endLocation;

    public Trip(String tripName, TripLocation startLocation, TripLocation endLocation, long timeStamp , ArrayList<Note> tripNote) {
        this.tripName = tripName;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.timeStamp=timeStamp;
        this.tripNote=tripNote;
    }

    public Trip(){
    }

    public String getTripKey() {
        return tripKey;
    }

    public void setTripKey(String tripKey) {
        this.tripKey = tripKey;
    }


    public long getTimeStamp() {
        return timeStamp;
    }
    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getDateTimeStamp() {
        Date date=new Date(timeStamp);
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
        sdf2.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        return sdf2.format(date);
    }

    public String getTimeTimeStamp() {
        Date date=new Date(timeStamp);
        SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");
        sdf2.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        return sdf2.format(date);
    }


    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public TripLocation getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(TripLocation startLocation) {
        this.startLocation = startLocation;
    }

    public TripLocation getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(TripLocation endLocation) {
        this.endLocation = endLocation;
    }

    public String getMarkerColor() {
        return markerColor;
    }

    public void setMarkerColor(String markerColor) {
        this.markerColor = markerColor;
    }

    @Override
    public String toString() {
        return tripName;
    }

    public ArrayList<Note> getTripNote() {
        return tripNote;
    }

    public void setTripNote(ArrayList<Note> tripNote) {
        this.tripNote = tripNote;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tripName);
        dest.writeLong(this.timeStamp);
        dest.writeTypedList(this.tripNote);
        dest.writeString(this.markerColor);
        dest.writeString(this.tripKey);
        dest.writeParcelable(this.startLocation, flags);
        dest.writeParcelable(this.endLocation, flags);
    }

    protected Trip(Parcel in) {
        this.tripName = in.readString();
        this.timeStamp = in.readLong();
        this.tripNote = in.createTypedArrayList(Note.CREATOR);
        this.markerColor = in.readString();
        this.tripKey = in.readString();
        this.startLocation = in.readParcelable(TripLocation.class.getClassLoader());
        this.endLocation = in.readParcelable(TripLocation.class.getClassLoader());
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel source) {
            return new Trip(source);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };
}

