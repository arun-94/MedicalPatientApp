package com.example.arun.medicalpatientapp;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class AppManager extends Application
{

    public ArrayList<Prescription> currentPatientPrescriptions = new ArrayList<>();
    public AsyncResponse delegate = null;
    public Prescription selectedPrescription;

    ConnectivityManager cm;
    NetworkInfo ni;

    @Override
    public void onCreate()
    {
        super.onCreate();
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();
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

    public void setUpPush(ParseUser user) {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("username", user.getUsername());
        installation.saveInBackground(new SaveCallback()
        {
            @Override
            public void done(ParseException e)
            {
                if(e == null) {
                    Log.d("AppManager", "Push Init Complete");
                }
                else {
                    Log.e("AppManager", e.getMessage());
                }
            }
        });
    }

    public void getAllPrescriptionsFromCurrentPatient()
    {

        if ((ni != null) && (ni.isConnected()))
        {
            ParseQuery<Prescription> query = Prescription.getQuery();

            query.whereEqualTo("patient_id", ParseUser.getCurrentUser());
            query.include("doctor_id");
            query.include("patient_id");
            query.include("medicine_ids");
            query.include("medicine_ids.medicine");
            query.findInBackground(new FindCallback<Prescription>()
            {
                @Override
                public void done(List<Prescription> list, ParseException e)
                {
                    if (e == null)
                    {
                        Log.d("Manager", "Size of list ; " + list.size());
                        currentPatientPrescriptions.clear();
                        currentPatientPrescriptions.addAll(list);
                        delegate.processFinish("manager", Constants.TYPE_RECIEVED_PRESCRIPTIONS);
                    }
                    else
                    {
                        Log.d("AppManager", e.getMessage());
                    }
                }
            });

        }

    }
}
