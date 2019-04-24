package iti.jets.mad.tripplannerproject.screens.homescreen.homefragment;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

import iti.jets.mad.tripplannerproject.model.Note;
import iti.jets.mad.tripplannerproject.model.Trip;
import iti.jets.mad.tripplannerproject.screens.addtripscreen.NoteAdapter;

public class HomeFragmentPresenterImpl  implements HomeFragmentContract.IPresnter {

    HomeFragmentContract.IView activity;

    public HomeFragmentPresenterImpl(HomeFragmentContract.IView activity) {
        this.activity = activity;
    }

    public ArrayList<Trip> getDataList(){
        return activity.getTripArrayList();
    }
}
