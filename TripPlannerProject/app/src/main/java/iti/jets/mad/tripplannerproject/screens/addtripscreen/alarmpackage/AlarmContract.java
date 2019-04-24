package iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmpackage;

import iti.jets.mad.tripplannerproject.model.Trip;

public class AlarmContract {
    public interface IMode{

    }
    public interface IView{

    }
    public interface IPresenter{

        void startAlarmRing();

        void startTrip();
        void snoozeTrip();
    }
}
