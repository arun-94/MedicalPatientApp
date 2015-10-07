package com.example.arun.medicalpatientapp.ParseObjects;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.ArrayList;

@ParseClassName("_User")
public class Doctor extends ParseUser
{
    public ParseFile getSignature() {
        return (ParseFile) get("signature");
    }

    public boolean isMale() {
        return getBoolean("is_male");
    }

    public String getPhone() {
        return getString("phone");
    }

    public String getAddress() {
        return getString("address");
    }

    public int getAge() {
        return getInt("age");
    }

    public boolean isDoctor() {
        return true;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Prescription> getPrescriptionList() {
        return (ArrayList<Prescription>) get("prescription_list");
    }


    public void setSignature( ParseFile signature) {
        put("signature", signature);
    }
}
