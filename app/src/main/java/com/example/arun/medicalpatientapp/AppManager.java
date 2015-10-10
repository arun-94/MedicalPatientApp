package com.example.arun.medicalpatientapp;

import android.app.Application;
import android.util.Log;

import com.example.arun.medicalpatientapp.UI.ParseObjects.Medicine;
import com.example.arun.medicalpatientapp.UI.ParseObjects.PrescribedMedicine;
import com.example.arun.medicalpatientapp.UI.ParseObjects.Prescription;
import com.example.arun.medicalpatientapp.UI.ParseObjects.User;
import com.facebook.FacebookSdk;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class AppManager extends Application
{

    public ArrayList<Prescription> currentPatientPrescriptions = new ArrayList<>();

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

    }

    public void setUpPush() {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("username", ParseUser.getCurrentUser().getUsername());
        installation.saveInBackground();
    }

    public void getAllPrescriptionsFromCurrentPatient() {
        ParseQuery<Prescription> query = Prescription.getQuery();

        query.whereEqualTo("patient_id", ParseUser.getCurrentUser());

        query.findInBackground(new FindCallback<Prescription>()
        {
            @Override
            public void done(List<Prescription> list, ParseException e)
            {
                if(e == null) {
                    currentPatientPrescriptions.addAll(list);
                }
                else {
                    Log.d("AppManager", e.getMessage());
                }
            }
        });

    }
}
