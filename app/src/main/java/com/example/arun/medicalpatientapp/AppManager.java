package com.example.arun.medicalpatientapp;

import android.app.Application;

import com.example.arun.medicalpatientapp.ParseObjects.Doctor;
import com.example.arun.medicalpatientapp.ParseObjects.Medicine;
import com.example.arun.medicalpatientapp.ParseObjects.Patient;
import com.example.arun.medicalpatientapp.ParseObjects.Prescription;
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
        ParseObject.registerSubclass(Doctor.class);
        ParseObject.registerSubclass(Medicine.class);
        ParseObject.registerSubclass(Patient.class);
        ParseObject.registerSubclass(Prescription.class);
        Parse.initialize(this, "cvSyybrwMKzHdVwpvUQ2ftclezIYsunMzz6UtUP7", "szykRYtChmUBK1FhyapdUrHI2lGOECH3dIjUCL1c");
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("username", "123");
        installation.saveInBackground();
    }
}
