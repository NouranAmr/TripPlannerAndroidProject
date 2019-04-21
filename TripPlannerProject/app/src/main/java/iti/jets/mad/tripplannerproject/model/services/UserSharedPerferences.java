package iti.jets.mad.tripplannerproject.model.services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import iti.jets.mad.tripplannerproject.screens.homescreen.HomeActivity;

public class UserSharedPerferences {

    private static final String SETTING_INFOS = "User_Info";
    private static final String EMAIL = "Email";
    private static final String PASSWORD = "PASSWORD";
    private SharedPreferences settings;
    private Boolean flag=false;
    public void sharedPreferences(String email, String password, boolean flag, Context context) {
        settings = context.getSharedPreferences(SETTING_INFOS, 0);
        if(!flag) {
            settings.edit()
                    .putString(EMAIL, email)
                    .putString(PASSWORD, password)
                    .commit();
        }
        else
        {
            settings.edit()
                    .putString(EMAIL, null)
                    .putString(PASSWORD, null)
                    .commit();
        }
    }
    public boolean getSharedPreferences(Context context) {
        settings = context.getSharedPreferences(SETTING_INFOS, 0);
        if(settings.contains(EMAIL))
        {
            String name = settings.getString(EMAIL, "");
            String Password = settings.getString(PASSWORD, "");
            Toast.makeText(context, name+"   "+Password, Toast.LENGTH_SHORT).show();
            flag=true;
        }
        else
            flag=false;
        return  flag;
    }
}
