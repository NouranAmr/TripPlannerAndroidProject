package iti.jets.mad.tripplannerproject.screens.addtripscreen.datepicker;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {

    int hour= -1,minute;
    String dayOrNight="f";

    public TimePickerFragment(){

    }
    @SuppressLint("ValidFragment")
    public TimePickerFragment(int hour, int minute,String dayOrNight){
        this.hour = hour;
        this.minute = minute;
        this.dayOrNight = dayOrNight;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar calendar=Calendar.getInstance();
        if(hour==-1){
            hour=calendar.get(Calendar.HOUR_OF_DAY);
            minute=calendar.get(Calendar.MINUTE);
        }
        if (dayOrNight.equals("PM")){
            hour+=12;
        }
        return new TimePickerDialog(getActivity(),(TimePickerDialog.OnTimeSetListener) getActivity(),hour,minute,false);

    }
}
