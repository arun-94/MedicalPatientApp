package com.example.arun.medicalpatientapp.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.arun.medicalpatientapp.R;
import com.example.arun.medicalpatientapp.UI.ParseObjects.User;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.OnClick;

public class FormActivity extends BaseActivity
{
    //AppManager appManager;


    @Bind(R.id.editText_patient_phone_number)
    EditText mobileNumber;

    @Bind(R.id.editText_patient_age)
    EditText age;

    @OnClick(R.id.saveDetails)
    void saveDetails() {

        User currentUser = (User) ParseUser.getCurrentUser();

        currentUser.setMobileNumber(mobileNumber.getText().toString().trim());
        currentUser.setAge(age.getText().toString().trim());
        currentUser.saveInBackground();

        gotoMainActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_form;
    }

    @Override
    protected void setupToolbar()
    {

    }

    @Override
    protected void setupLayout()
    {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void gotoMainActivity()
    {
        // manager.fetchDataFromParse();

        Intent intent = new Intent(FormActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
