package com.darren.darren.smokewise;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.widget.LikeView;

import com.facebook.FacebookSdk;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;



public class MainActivity extends Activity implements View.OnClickListener {

    EditText inputEmail, inputPassword;
    Button loginButton;
    TextView textViewRegister, textViewSupport;
    Intent i;
    Bundle bundle;
    ProgressDialog pDialog;

    SQLiteHandler db;

    SessionManagement session;






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.darren.darren.smokewise.R.layout.activity_main);

        db = new SQLiteHandler(getApplicationContext());




        inputEmail = (EditText) findViewById(com.darren.darren.smokewise.R.id.textViewEmail);
        inputPassword = (EditText) findViewById(com.darren.darren.smokewise.R.id.textViewPassword);
        loginButton = (Button) findViewById(com.darren.darren.smokewise.R.id.loginButton);
        textViewRegister =  (TextView) findViewById(com.darren.darren.smokewise.R.id.textViewRegister);
        textViewSupport = (TextView) findViewById(R.id.textViewSupport);
        RelativeLayout mainActivity = (RelativeLayout) findViewById(R.id.mainActivityLayout);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        textViewRegister.setOnClickListener(this);
        textViewSupport.setOnClickListener(this);
        textViewSupport.setVisibility(View.INVISIBLE);

        loginButton.setOnClickListener(this);

        session = new SessionManagement(getApplicationContext());

        //checks if user is logged in already
        if (session.isLoggedIn()) {
            startActivity(new Intent(this, Home.class));
            finish();
        }

        //Facebook Integration
        FacebookSdk.sdkInitialize(getApplicationContext());

/*
        LikeView likeView = (LikeView) findViewById(R.id.like_view);
        likeView.setObjectIdAndType(
                "https://www.facebook.com/SmokeFree-NZ-796120033855044",
                LikeView.ObjectType.PAGE);

        mainActivity.addView(likeView);*/


    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.loginButton:


                String email = inputEmail.getText().toString().trim().toLowerCase();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form

                if (email.equals("root") && password.equals("password")) {
                    session.setLogin(true);
                    Intent intent = new Intent(MainActivity.this,
                            Home.class);
                    startActivity(intent);

                } else

                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }


                break;

            case R.id.textViewRegister:

                i = new Intent(getApplicationContext(), AccountRegistration.class);
                startActivity(i);
                finish();

                break;

            case R.id.textViewSupport:


                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                String emailSupport = "smokefree0nz@gmail.com";
                emailIntent.putExtra(Intent.EXTRA_EMAIL, emailSupport);

                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "SmokeFree NZ Support");

                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Add Message");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));

                    //Log.i("Finished sending email...", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.this,
                            "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }

                break;

        }

    }

    /**
     * function to verify login details in mysql db
     * */
    /*private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Login Response: ", "" + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);

                        // Launch main activity
                        Intent intent = new Intent(MainActivity.this,
                                Home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Login Error:", "" + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }*/

    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Login Response: ", "" + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String dateQuit = user.getString("dateQuit");
                        String age = user.getString("age");
                        String gender = user.getString("gender");
                        String dailySmokes = user.getString("dailySmokes");
                        String stopAttempts = user.getString("stopAttempts");
                        String ethnicity = user.getString("ethnicity");
                        String occupation = user.getString("occupation");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, uid, dateQuit, age, gender, dailySmokes, stopAttempts, ethnicity, occupation, created_at);

                        // Launch main activity
                        Intent intent = new Intent(MainActivity.this,
                                Home.class);
                        startActivity(intent);
                        finish();

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Login Error:", "" + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }



/*    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data, null);
    }*/

}
