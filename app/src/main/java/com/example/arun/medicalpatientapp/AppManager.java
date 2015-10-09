package com.example.arun.medicalpatientapp;

import android.app.Application;

import com.example.arun.medicalpatientapp.UI.ParseObjects.Medicine;
import com.example.arun.medicalpatientapp.UI.ParseObjects.PrescribedMedicine;
import com.example.arun.medicalpatientapp.UI.ParseObjects.Prescription;
import com.example.arun.medicalpatientapp.UI.ParseObjects.User;
import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

public class AppManager extends Application
{

    @Override
    public void onCreate()
    {
        super.onCreate();
        parseInit();
    }

    private void parseInit() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Medicine.class);
        ParseObject.registerSubclass(Prescription.class);
        ParseObject.registerSubclass(PrescribedMedicine.class);
        Parse.initialize(this, "cvSyybrwMKzHdVwpvUQ2ftclezIYsunMzz6UtUP7", "szykRYtChmUBK1FhyapdUrHI2lGOECH3dIjUCL1c");
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("username", "123");
        installation.saveInBackground();
    }
}
