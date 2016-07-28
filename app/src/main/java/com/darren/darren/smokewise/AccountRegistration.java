package com.darren.darren.smokewise;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.darren.darren.smokewise.app.AppConfig;
import com.darren.darren.smokewise.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AccountRegistration extends Activity implements View.OnClickListener {
  EditText editTextEmail, editTextPassword, editTextConfirmPassword, editTextName, editTextDateQuit,
      editTextAge, editTextGender, editTextDailySmokes, editTextStopAttempts, editTextEthnicity, editTextOccupation;  // These are needed to add to the database
  Button buttonRegister;
  SQLiteHandler db;
  ProgressDialog pDialog;
  SessionManagement session;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(com.darren.darren.smokewise.R.layout.activity_account_registration);


    //Text Fields
    editTextEmail = (EditText) findViewById(com.darren.darren.smokewise.R.id.editTextEmail);
    editTextName = (EditText) findViewById(R.id.editTextName);
    editTextPassword = (EditText) findViewById(com.darren.darren.smokewise.R.id.editTextPassword);
    editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirm);

    editTextDateQuit = (EditText) findViewById(R.id.editTextDateQuit);

    editTextAge = (EditText) findViewById(R.id.editTextAge);
    editTextGender = (EditText) findViewById(R.id.editTextGender);
    editTextDailySmokes = (EditText) findViewById(R.id.editTextDailySmokes);
    editTextStopAttempts = (EditText) findViewById(R.id.editTextStopAttempts);
    editTextEthnicity = (EditText) findViewById(R.id.editTextEthnicity);
    editTextOccupation = (EditText) findViewById(R.id.editTextOccupation);


    buttonRegister = (Button) findViewById(com.darren.darren.smokewise.R.id.buttonRegister);
    buttonRegister.setOnClickListener(this);


    // Progress dialog
    pDialog = new ProgressDialog(this);
    pDialog.setCancelable(false);

    // Session manager
    session = new SessionManagement(getApplicationContext());

    // SQLite database handler
    db = new SQLiteHandler(getApplicationContext());

    // Check if user is already logged in or not
    if (session.isLoggedIn()) {
      // User is already logged in. Take him to main activity
      Intent intent = new Intent(AccountRegistration.this,
          MainActivity.class);
      startActivity(intent);
      finish();
    }


  }


  @Override
  public void onClick(View v) {
    switch (v.getId()) {

      case com.darren.darren.smokewise.R.id.buttonRegister:

        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim().toLowerCase();
        String password = editTextPassword.getText().toString().trim();
        String cPassword = editTextConfirmPassword.getText().toString().trim();

        String dateQuit = editTextDateQuit.getText().toString();
        String age = editTextAge.getText().toString().trim();
        String gender = editTextGender.getText().toString().trim().toLowerCase();
        String dailySmokes = editTextDailySmokes.getText().toString().trim();
        String stopAttempts = editTextStopAttempts.getText().toString().trim();
        String ethnicity = editTextEthnicity.getText().toString().trim();
        String occupation = editTextOccupation.getText().toString().trim();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !cPassword.isEmpty() && !dateQuit.isEmpty() && !age.isEmpty() && !gender.isEmpty()
            && !dailySmokes.isEmpty() && !stopAttempts.isEmpty() && !ethnicity.isEmpty() && !occupation.isEmpty()) {
          if (password.equals(cPassword)) {
            if (gender.equals("male") || gender.equals("female")) {
              registerUser(name, email, password, dateQuit, age, gender, dailySmokes, stopAttempts, ethnicity, occupation);
            } else {
              Toast.makeText(getApplicationContext(),
                  "Incorrect Gender", Toast.LENGTH_LONG)
                  .show();
              editTextGender.setText("");
              editTextPassword.setText("");
              editTextConfirmPassword.setText("");
            }
          } else {
            Toast.makeText(getApplicationContext(),
                "Passwords do not match!", Toast.LENGTH_LONG)
                .show();
            editTextPassword.setText("");
            editTextConfirmPassword.setText("");
          }
        } else {
          Toast.makeText(getApplicationContext(),
              "Please fill out all fields!", Toast.LENGTH_LONG)
              .show();
        }
        break;
    }
  }

  private void registerUser(final String name, final String email,
                            final String password, final String dateQuit, final String age, final String gender, final String dailySmokes, final String stopAttempts, final String ethnicity, final String occupation) {
    // Tag used to cancel the request
    String tag_string_req = "req_register";
    pDialog.setMessage("Registering ...");
    showDialog();

    StringRequest strReq = new StringRequest(Request.Method.POST,
        AppConfig.URL_REGISTER, new Response.Listener<String>() {

      @Override
      public void onResponse(String response) {
        Log.d("Register Response: ", response);
        hideDialog();

        try {
          JSONObject jObj = new JSONObject(response);
          boolean error = jObj.getBoolean("error");
          if (!error) {
            // User successfully stored in MySQL
            // Now store the user in sqlite
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

            Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

            session.editor.clear();
            session.editor.commit();

            // Launch login activity
            Intent intent = new Intent(
                AccountRegistration.this,
                Survey.class);
            session.setLogin(true);
            startActivity(intent);


          } else {

            // Error occurred in registration. Get the error
            // message
            String errorMsg = jObj.getString("error_msg");
            Toast.makeText(getApplicationContext(),
                errorMsg, Toast.LENGTH_LONG).show();
          }
        } catch (JSONException e) {
          e.printStackTrace();
        }

      }
    }, new Response.ErrorListener() {

      @Override
      public void onErrorResponse(VolleyError error) {
        Log.d("Registration Error: ", "" + error.getMessage());
        Toast.makeText(getApplicationContext(),
            error.getMessage(), Toast.LENGTH_LONG).show();
        hideDialog();

      }
    }) {

      @Override
      protected Map<String, String> getParams() {
        // Posting params to register url
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
        params.put("dateQuit", dateQuit);
        params.put("age", age);
        params.put("gender", gender);
        params.put("dailySmokes", dailySmokes);
        params.put("stopAttempts", stopAttempts);
        params.put("ethnicity", ethnicity);
        params.put("occupation", occupation);

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
}
