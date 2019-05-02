package iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmpackage;

import android.content.Intent;

import iti.jets.mad.tripplannerproject.model.Trip;

public class AlarmContract {
    public interface IMode{

    }
    public interface IView{

    }
    public interface IPresenter{

        void startAlarmRing();
        public void setData(Intent intent);
        public void setTrip(Trip trip);
        void startTrip();
        void snoozeTrip();
    }
}
