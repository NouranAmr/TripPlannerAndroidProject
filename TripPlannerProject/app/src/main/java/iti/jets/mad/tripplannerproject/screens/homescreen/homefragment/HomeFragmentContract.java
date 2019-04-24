package iti.jets.mad.tripplannerproject.screens.homescreen.homefragment;

import java.util.ArrayList;

import iti.jets.mad.tripplannerproject.model.Trip;

public interface HomeFragmentContract {

    public interface IModel{

    }
    public interface IPresnter{
        public ArrayList<Trip> getDataList();
    }
    public interface IView{
        public ArrayList<Trip> getTripArrayList();
    }
}
