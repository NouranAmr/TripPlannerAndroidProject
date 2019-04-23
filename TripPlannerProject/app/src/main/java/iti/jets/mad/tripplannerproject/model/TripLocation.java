package iti.jets.mad.tripplannerproject.model;

import com.google.android.gms.maps.model.LatLng;

public class TripLocation {
    private String PointName;
    private double lat,lng;

    public TripLocation() {
   }

    public String getPointName() {
        return PointName;
    }

    public void setPointName(String pointName) {
        PointName = pointName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
