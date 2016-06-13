package com.darren.darren.smokewise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.rzlts.appinbox.AppInbox;
import com.rzlts.appinbox.model.Gender;
import com.rzlts.appinbox.views.InboxView;

import java.util.Calendar;
import java.util.HashMap;

public class Account extends Drawer implements View.OnClickListener {

    TextView editTextName, editTextEmail;
    Button buttonLogout;

    ImageView imageView1Month, imageView6Month, imageView1Year, imageView1000, imageView5000, imageView10000;
    int savedTotalPref;
    SessionManagement session;
    long savedDays, todayDate, daysSaved;


    private SQLiteHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_account, null, false);

        mDrawer.addView(contentView, 0);

        session = new SessionManagement(getApplicationContext());

        imageView1Month = (ImageView) findViewById(R.id.imageView1Month);
        imageView6Month = (ImageView) findViewById(R.id.imageView6Month);
        imageView1Year = (ImageView) findViewById(R.id.imageView1Year);
        imageView1000 = (ImageView) findViewById(R.id.imageView1000);
        imageView5000 = (ImageView) findViewById(R.id.imageView5000);
        imageView10000 = (ImageView) findViewById(R.id.imageView10000);



        //RZLTS PUSH SERVICE
        AppInbox.startInbox(this, "6medyGEK9mCn68cTjBm06n6FkvNNtxL0a1n95o8B-Fs", "207149040029", "null", "null", "null", "null", "", "dpoh89@gmail.com", Gender.MALE, 0);
        RelativeLayout rlLayout = (RelativeLayout) this.findViewById(R.id.relativeLayout3);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);

        final InboxView inbox = new InboxView(this);
        rlLayout.addView(inbox, params);


        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        editTextEmail = (TextView) findViewById(com.darren.darren.smokewise.R.id.editTextEmail);
        editTextName = (TextView) findViewById(R.id.editTextName);
        buttonLogout = (Button) findViewById(com.darren.darren.smokewise.R.id.buttonLogOut);

        buttonLogout.setOnClickListener(this);

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        editTextName.setText(name);
        editTextEmail.setText(email);

        HashMap<String, Integer> totalSavedPref = session.getTotalSaved();
        savedTotalPref = totalSavedPref.get(SessionManagement.KEY_TOTAL_SAVED);

        HashMap<String, Long> days = session.getDays();
        savedDays = days.get(SessionManagement.KEY_DAYS);


        Log.d("saved total", "" + savedTotalPref);

        //Checking for saved $$ and sets trophy accordingly

        if (savedTotalPref > 1000) {
            imageView1000.setImageResource(R.drawable.trophy_complete);
        } else {
            imageView1000.setImageResource(R.drawable.trophy);
        }
        if (savedTotalPref > 5000) {
            imageView5000.setImageResource(R.drawable.trophy_complete);
        } else {
            imageView5000.setImageResource(R.drawable.trophy);
        }
        if (savedTotalPref > 10000) {
            imageView10000.setImageResource(R.drawable.trophy_complete);
        } else {
            imageView10000.setImageResource(R.drawable.trophy);
        }


        Calendar today = Calendar.getInstance();
        todayDate = today.getTimeInMillis() / (24 * 60 * 60 * 1000);

        daysSaved = todayDate - savedDays;

        //checking for total days not smoked and sets trophy accordingly

        if ((daysSaved) > 31) {
            imageView1Month.setImageResource(R.drawable.trophy_complete);
        } else {
            imageView1Month.setImageResource(R.drawable.trophy);
        }

        if ((daysSaved) > 365) {
            imageView1Year.setImageResource(R.drawable.trophy_complete);
        } else {
            imageView1Year.setImageResource(R.drawable.trophy);
        }

        if ((daysSaved) > 182) {
            imageView6Month.setImageResource(R.drawable.trophy_complete);
        } else {
            imageView6Month.setImageResource(R.drawable.trophy);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (authenticate()) {

        } else {
            startActivity(new Intent(Account.this, MainActivity.class));
            finish();
        }
    }

    private boolean authenticate(){
        return session.isLoggedIn();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogOut:

                /**
                 * Logging out the user. Will set isLoggedIn flag to false in shared
                 * preferences Clears the user data from sqlite users table
                 * */

                session.setLogin(false);
                db.deleteUsers();
                startActivity(new Intent(Account.this, MainActivity.class));
                finish();
                break;


        }
    }
}
