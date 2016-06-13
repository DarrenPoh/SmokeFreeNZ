package com.darren.darren.smokewise;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Survey extends Activity implements View.OnClickListener{

    CheckBox cBHealth, cBAppearance, cBFamily, cBRole, cBEmployment, cBFinancial, cBSocial, cBNicotine, cBOther;
    Button buttonComplete;
    SQLiteHandler db;
    ProgressDialog pDialog;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        cBHealth = (CheckBox) findViewById(R.id.checkBoxHealth);
        cBAppearance = (CheckBox) findViewById(R.id.checkBoxAppearance);
        cBFamily = (CheckBox) findViewById(R.id.checkBoxFamily);
        cBRole = (CheckBox) findViewById(R.id.checkBoxRole);
        cBEmployment = (CheckBox) findViewById(R.id.checkBoxEmployment);
        cBFinancial = (CheckBox) findViewById(R.id.checkBoxFinancial);
        cBSocial = (CheckBox) findViewById(R.id.checkBoxSocial);
        cBNicotine = (CheckBox) findViewById(R.id.checkBoxNicotine);
        cBOther = (CheckBox) findViewById(R.id.checkBoxOther);

        buttonComplete = (Button) findViewById(R.id.buttonComplete);
        buttonComplete.setOnClickListener(this);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManagement(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonComplete:

                HashMap<String, String> user = db.getUserDetails();
                String email = user.get("email");

                String health = String.valueOf(cBHealth.isChecked());
                String appearance = String.valueOf(cBAppearance.isChecked());
                String family = String.valueOf(cBFamily.isChecked());
                String role = String.valueOf(cBRole.isChecked());
                String employment = String.valueOf(cBEmployment.isChecked());
                String financial = String.valueOf(cBFinancial.isChecked());
                String social = String.valueOf(cBSocial.isChecked());
                String nicotine = String.valueOf(cBNicotine.isChecked());
                String other = String.valueOf(cBOther.isChecked());


                registerUser(email, health, appearance, family, role, employment, financial, social, nicotine, other);

                break;
        }


    }

    private void registerUser(final String email, final String health, final String appearance,
                              final String family, final String role, final String employment, final String financial, final String social, final String nicotine, final String other ) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        pDialog.setMessage("Registering ...");
        showDialog();

//        StringRequest strReq = new StringRequest(Request.Method.POST,
//                AppConfig.URL_REGISTER, new Response.Listener<String>() {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER2, new Response.Listener<String>() {


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

                        //String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");

                        String email = user.getString("email");
                        String health = user.getString("health");
                        String appearance = user.getString("appearance");
                        String family = user.getString("family");
                        String role = user.getString("role");
                        String employment = user.getString("employment");
                        String financial = user.getString("financial");
                        String social = user.getString("social");
                        String nicotine = user.getString("nicotine");
                        String other = user.getString("other");

                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        db.addUserCategory(email, health, appearance, family, role, employment, financial, social, nicotine, other, created_at);

                        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                Survey.this,
                                Home.class);
                        session.setLogin(true);
                        startActivity(intent);
                        finish();
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
                params.put("email", email);
                params.put("health", health);
                params.put("appearance", appearance);
                params.put("family", family);
                params.put("role", role);
                params.put("employment", employment);
                params.put("financial", financial);
                params.put("social", social);
                params.put("nicotine", nicotine);
                params.put("other", other);

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

    /*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_survey, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
