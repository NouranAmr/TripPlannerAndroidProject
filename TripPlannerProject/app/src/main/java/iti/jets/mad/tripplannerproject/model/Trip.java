package iti.jets.mad.tripplannerproject.model;

import java.util.Calendar;
import java.util.Date;

public class Trip {

    private String tripName;
    Calendar calendar;
    private Note tripNote;
    private String markerColor;
    private TripLocation startLocation, endLocation;

    public Trip(String tripName, TripLocation startLocation,TripLocation endLocation, Calendar calendar , Note tripNote) {
        this.tripName = tripName;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.calendar=calendar;
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

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Note getTripNote() {
        return tripNote;
    }

    public void setTripNote(Note tripNote) {
        this.tripNote = tripNote;
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
}

