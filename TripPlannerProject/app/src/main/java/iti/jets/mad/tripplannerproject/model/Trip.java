package iti.jets.mad.tripplannerproject.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Trip {

    private String tripName;
    long timeStamp;
    private List<Note> tripNote;
    private String markerColor;
    private TripLocation startLocation, endLocation;

    public Trip(String tripName, TripLocation startLocation, TripLocation endLocation, long timeStamp , List<Note> tripNote) {
        this.tripName = tripName;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.timeStamp=timeStamp;
        this.tripNote=tripNote;
    }

    public Trip(){
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public long getTimeStamp() {
        return timeStamp;
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

    public List<Note> getTripNote() {
        return tripNote;
    }

    public void setTripNote(List<Note> tripNote) {
        this.tripNote = tripNote;
    }
}

