package com.example.arun.medicalpatientapp.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.arun.medicalpatientapp.Constants;
import com.example.arun.medicalpatientapp.R;
import com.example.arun.medicalpatientapp.UI.ParseObjects.User;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity
{
    private String LOG_TAG = "LoginActivity";
    @Bind(R.id.progress_view) ProgressBar progressLoading;
    @Bind (R.id.loginButton) Button loginButton;

    ConnectivityManager cm;
    NetworkInfo ni;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();
        ParseFacebookUtils.initialize(this);
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_login;
    }

    @Override
    protected void setupToolbar()
    {

    }

    @Override
    protected void setupLayout()
    {
        progressLoading.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.fbLoginButton)
    void onFBLoginClick()
    {
        if ((ni != null) && (ni.isConnected()))
        {
            List<String> permissions = Arrays.asList("public_profile", "user_friends", "user_birthday");

            //Run code to get hash key in log
        /*try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.arun.medicalpatientapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("Your Tag", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("error", "" + e);
        } catch (NoSuchAlgorithmException e) {
            Log.d("error", "" + e);
        }*/

            ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, new LogInCallback()
            {
                @Override
                public void done(ParseUser user, ParseException err)
                {
                    //Log.d("ho", "Here");
                    if (user == null)
                    {
                        Log.e(LOG_TAG, "Uh oh. The user cancelled the Facebook login.");
                        Toast.makeText(LoginActivity.this, "Login failed. Facebook Not working.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if (user.isNew())
                        {
                            Log.d(LOG_TAG, "User signed up and logged in through Facebook!");
                            manager.setUpPush(user);
                            fetchDataFromFB();
                        }
                        else
                        {
                            Log.d("ho", "User logged in through Facebook!");
                            manager.setUpPush(user);
                            manager.getAllPrescriptionsFromCurrentPatient();
                            fetchDataFromFB();
                        }
                    }
                }
            });
        }
        else {
            Toast.makeText(LoginActivity.this, "No Internet Access", Toast.LENGTH_SHORT);
        }
    }

    @OnClick(R.id.loginButton)
    void onLoginClick()
    {
        if ((ni != null) && (ni.isConnected()))
        {
            progressLoading.setVisibility(View.VISIBLE);
            loginButton.setText("");
            Log.d("LOGIN", "Clicked Test Login");
            ParseUser.logInInBackground("TestPatient", "test123", new LogInCallback()
            {
                public void done(ParseUser user, ParseException e)
                {
                    if (user != null)
                    {
                        Log.d("LOGIN", "Got test user");

                        manager.setUpPush(user);
                        manager.getAllPrescriptionsFromCurrentPatient();
                        gotoMainActivity();
                    }
                    else
                    {
                        Log.d(LOG_TAG, "Login Failed" + e.getMessage());
                        Toast.makeText(LoginActivity.this, "No Internet. Login Failed.", Toast.LENGTH_SHORT).show();
                        // Signup failed. Look at the ParseException to see what happened.
                        gotoMainActivityOffline();
                    }
                }
            });
        }
        else {
            gotoMainActivityOffline();
        }
    }


    private void fetchDataFromFB()
    {
        final ParseUser currentUser = User.getCurrentUser();
        if (!currentUser.getUsername().equals(""))
        {
            gotoNextActivity();
        }

        if (currentUser.isAuthenticated())
        {
            makeMeRequest();
        }

    }

    private void makeMeRequest()
    {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback()
        {
            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse)
            {
                if (jsonObject != null)
                {
                    User currentUser = (User) ParseUser.getCurrentUser();
                    try
                    {
                        currentUser.put("facebookId", jsonObject.getLong("id"));
                        currentUser.setName(jsonObject.getString("name"));

                        if (jsonObject.getString("gender") != null)
                        {
                            if (jsonObject.getString("gender").equals("male"))
                            {
                                currentUser.setIsMale(true);
                            }
                            else
                            {
                                currentUser.setIsMale(false);
                            }

                        }

                        currentUser.setIsDoctor(false);

                        currentUser.saveInBackground();
                    } catch (JSONException e)
                    {
                        Log.d(LOG_TAG, "Error parsing returned user data. " + e);
                    }

                    gotoNextActivity();
                }
                else
                {
                    if (graphResponse.getError() != null)
                    {
                        switch (graphResponse.getError().getCategory())
                        {
                            case LOGIN_RECOVERABLE:
                                Log.d(LOG_TAG, "Authentication error: " + graphResponse.getError());
                                break;

                            case TRANSIENT:
                                Log.d(LOG_TAG, "Transient error. Try again. " + graphResponse.getError());
                                break;

                            case OTHER:
                                Log.d(LOG_TAG, "Some other error: " + graphResponse.getError());
                                break;
                        }
                    }
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,gender,birthday");
        request.setParameters(parameters);

        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    private void gotoNextActivity()
    {
        // manager.fetchDataFromParse();
        User patient = (User) ParseUser.getCurrentUser();
        if(patient.getPhone().isEmpty())
        {
            Intent intent = new Intent(LoginActivity.this, FormActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else {
            gotoMainActivity();
        }
    }

    private void gotoMainActivity()
    {
        // manager.fetchDataFromParse();
        progressLoading.setVisibility(View.INVISIBLE);
        loginButton.setText("Test Patient Login");
        Intent intent = new Intent(LoginActivity.this, PrescriptionListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Log.d("LOGIN", "Going to main activity");
    }

    private void gotoMainActivityOffline()
    {
        // manager.fetchDataFromParse();
        progressLoading.setVisibility(View.INVISIBLE);
        loginButton.setText("Test Patient Login");
        Intent intent = new Intent(LoginActivity.this, PrescriptionListActivityOffline.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Log.d("LOGIN", "Going to main activity");
    }

    @Override
    public void processFinish(String result, int type)
    {
        if(type == Constants.TYPE_RECIEVED_PRESCRIPTIONS)
        {
        }
    }

}
