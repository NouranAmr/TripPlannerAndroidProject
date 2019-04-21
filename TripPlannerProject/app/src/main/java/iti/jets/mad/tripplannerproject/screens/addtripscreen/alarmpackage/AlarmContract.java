package iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmpackage;

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
