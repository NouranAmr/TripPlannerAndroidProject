package iti.jets.mad.tripplannerproject.screens.addtripscreen.alarmpackage;

import android.content.Intent;

public class AlarmContract {
    public interface IMode{

    }
    public interface IView{

    }
    public interface IPresenter{

        void startAlarmRing();
        public void setData(Intent intent);
        void startTrip();
        void snoozeTrip();
    }
}
