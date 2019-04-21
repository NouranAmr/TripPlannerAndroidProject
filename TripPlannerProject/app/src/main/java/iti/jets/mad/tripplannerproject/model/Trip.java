package iti.jets.mad.tripplannerproject.model;

import java.util.Date;

public class Trip {

    private String tripName,startPoint, endPoint;
    private Date tripDate;
    private Note tripNote;

    public Trip(String tripName, String startPoint, String endPoint, Date tripDate , Note tripNote) {
        this.tripName = tripName;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.tripDate = tripDate;
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

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public Date getTripDate() {
        return tripDate;
    }

    public void setTripDate(Date tripDate) {
        this.tripDate = tripDate;
    }

    public Note getTripNote() {
        return tripNote;
    }

    public void setTripNote(Note tripNote) {
        this.tripNote = tripNote;
    }

    @Override
    public String toString() {
        return tripName;
    }
}

