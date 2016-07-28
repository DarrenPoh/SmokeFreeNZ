package com.darren.darren.smokewise;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;

public class ExtraHelp extends Drawer implements View.OnClickListener {

  EditText editTextTreatment, editTextFrequency;
  Button buttonSet;
  String nicotineType, savedNicotineType;
  int nicotineFrequency, savedNicotineFreq;
  LinearLayout linearLayoutExtraHelp;
  SessionManagement session;
  InputMethodManager inputManager;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_extra_help);
    LayoutInflater inflater = (LayoutInflater) this
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View contentView = inflater.inflate(com.darren.darren.smokewise.R.layout.activity_extra_help, null, false);
    mDrawer.addView(contentView, 0);

    session = new SessionManagement(getApplicationContext());

    HashMap<String, String> nicotineTypePref = session.getNicotineType();
    savedNicotineType = nicotineTypePref.get(SessionManagement.KEY_NICOTINE_TYPE);

    HashMap<String, Integer> nicotineFreqPref = session.getNicotineFrequency();
    savedNicotineFreq = nicotineFreqPref.get(SessionManagement.KEY_NICOTINE_FREQUENCY);

    editTextTreatment = (EditText) findViewById(R.id.editTextNicotineType);
    editTextFrequency = (EditText) findViewById(R.id.editTextNicotineFrequency);
    linearLayoutExtraHelp = (LinearLayout) findViewById(R.id.linearLayoutExtraHelp);

    buttonSet = (Button) findViewById(R.id.buttonSet);
    buttonSet.setOnClickListener(this);

    editTextTreatment.setText(savedNicotineType);
    editTextFrequency.setText(Integer.toString(savedNicotineFreq));

    inputManager = (InputMethodManager)
        getSystemService(Context.INPUT_METHOD_SERVICE);

    final ImageButton quitlineImage = (ImageButton) findViewById(com.darren.darren.smokewise.R.id.quitlineImage);
    quitlineImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.quit.org.nz/"));
          startActivity(intent);

        } catch (Exception e) {
          startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.quit.org.nz/")));

        }
      }
    });

    final ImageButton heartFoundationImage = (ImageButton) findViewById(com.darren.darren.smokewise.R.id.heartFoundationImage);
    heartFoundationImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.quit.org.nz/"));
          startActivity(intent);

        } catch (Exception e) {
          startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.quit.org.nz/")));

        }
      }
    });

    final ImageButton ashImage = (ImageButton) findViewById(com.darren.darren.smokewise.R.id.ashImage);
    ashImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ash.org.nz/"));
          startActivity(intent);

        } catch (Exception e) {
          startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ash.org.nz/")));

        }
      }
    });

    editTextFrequency.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
          editTextFrequency.setText("");
        }
      }
    });

    this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(com.darren.darren.smokewise.R.menu.menu_extra_help, menu);
    return true;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.buttonSet:

        nicotineType = editTextTreatment.getText().toString().trim();
        nicotineFrequency = Integer.parseInt(editTextFrequency.getText().toString().trim());

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS);

        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (!nicotineType.isEmpty()) {

          session.setNicotineType(nicotineType);
          session.setNicotineFrequency(nicotineFrequency);
          editTextFrequency.setText(Integer.toString(nicotineFrequency));

          if (nicotineFrequency > 0) {

            Calendar alarmStartTime = Calendar.getInstance();
            Calendar now = Calendar.getInstance();
            alarmStartTime.set(Calendar.HOUR_OF_DAY, 10);
            alarmStartTime.set(Calendar.MINUTE, 30);
            alarmStartTime.set(Calendar.SECOND, 0);
            if (now.after(alarmStartTime)) {
              alarmStartTime.add(Calendar.DATE, 1);
            }

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY * nicotineFrequency, broadcast);

            //For minute testing
                        /*alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), (AlarmManager.INTERVAL_DAY * nicotineFrequency) / (24 * 60), broadcast);*/

            Toast.makeText(getApplicationContext(), "Reminder set!", Toast.LENGTH_LONG).show();

          } else {

            alarmManager.cancel(broadcast);
            Toast.makeText(getApplicationContext(), "Reminder stopped!", Toast.LENGTH_LONG).show();

          }

        } else {
          Toast.makeText(getApplicationContext(), "Please fill in treatment", Toast.LENGTH_LONG).show();
        }

        linearLayoutExtraHelp.requestFocus();
        break;

    }


  }
}
