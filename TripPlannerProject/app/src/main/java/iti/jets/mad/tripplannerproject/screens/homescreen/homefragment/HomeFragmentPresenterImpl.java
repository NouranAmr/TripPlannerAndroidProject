package iti.jets.mad.tripplannerproject.screens.homescreen.homefragment;

import java.util.ArrayList;

import iti.jets.mad.tripplannerproject.model.Trip;

public class HomeFragmentPresenterImpl  implements HomeFragmentContract.IPresnter {

    HomeFragmentContract.IView activity;

    public HomeFragmentPresenterImpl(HomeFragmentContract.IView activity) {
        this.activity = activity;
    }

    public ArrayList<Trip> getDataList(){
        return activity.getTripArrayList();
    }
}
