package iti.jets.mad.tripplannerproject.model;

import com.google.android.gms.maps.model.LatLng;

public class TripLocation {
    private String PointName;
    private LatLng PointLatLng;

    public TripLocation() {
    }

    public TripLocation(String pointName, LatLng pointLatLng) {
        PointName = pointName;
        PointLatLng = pointLatLng;
    }

    public String getPointName() {
        return PointName;
    }

    public void setPointName(String pointName) {
        PointName = pointName;
    }

    public LatLng getPointLatLng() {
        return PointLatLng;
    }

    public void setPointLatLng(LatLng pointLatLng) {
        PointLatLng = pointLatLng;
    }
}
