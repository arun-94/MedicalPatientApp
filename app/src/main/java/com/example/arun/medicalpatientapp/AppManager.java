package com.example.arun.medicalpatientapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class AppManager extends Application
{

    @Override
    public void onCreate()
    {
        super.onCreate();
        Parse.initialize(this, "cvSyybrwMKzHdVwpvUQ2ftclezIYsunMzz6UtUP7", "szykRYtChmUBK1FhyapdUrHI2lGOECH3dIjUCL1c");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
