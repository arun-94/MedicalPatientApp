package com.example.arun.medicalpatientapp.UI.ParseObjects;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.ArrayList;

@ParseClassName("_User")
public class User extends ParseUser
{

    public void setName(String name) {
        put("name", name);
    }

    public void setIsDoctor(Boolean val) {
        put("is_doctor", val);
    }

    public void setIsMale(Boolean val) {
        put("is_male", val);
    }

    public ParseFile getSignature() {
        return (ParseFile) get("signature");
    }

    public boolean isMale() {
        return getBoolean("is_male");
    }

    public String getPhone() {
        if(getString("phone") != null)
            return getString("phone");
        else
            return "";
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

    public void addPrescriptionToList(Prescription prescription) {
        addUnique("prescription_list", prescription);
    }

    public void setSignature( ParseFile signature) {
        put("signature", signature);
    }

    public void setMobileNumber(String mobileNumber)
    {
        put("phone", mobileNumber);
    }

    public void setAge(String age)
    {
        put("age", Integer.parseInt(age));

    }
}
