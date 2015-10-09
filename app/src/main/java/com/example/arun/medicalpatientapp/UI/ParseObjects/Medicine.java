package com.example.arun.medicalpatientapp.UI.ParseObjects;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Medicine")
public class Medicine extends ParseObject
{
    public Medicine()
    {
    }

    public String getMedicineName()
    {
        return getString("medicine_name");
    }

    public String getMedicineUrl() {
        return "www.drugs.com/" + getMedicineName() + ".html";
    }

    public static ParseQuery<Medicine> getQuery()
    {
        return ParseQuery.getQuery(Medicine.class);
    }
}
