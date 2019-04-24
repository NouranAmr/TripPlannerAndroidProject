package iti.jets.mad.tripplannerproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class TripLocation implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.PointName);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
    }

    protected TripLocation(Parcel in) {
        this.PointName = in.readString();
        this.lat = in.readDouble();
        this.lng = in.readDouble();
    }

    public static final Parcelable.Creator<TripLocation> CREATOR = new Parcelable.Creator<TripLocation>() {
        @Override
        public TripLocation createFromParcel(Parcel source) {
            return new TripLocation(source);
        }

        @Override
        public TripLocation[] newArray(int size) {
            return new TripLocation[size];
        }
    };
}
