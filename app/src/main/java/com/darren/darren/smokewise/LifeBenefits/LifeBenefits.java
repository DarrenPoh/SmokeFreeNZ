package com.darren.darren.smokewise.LifeBenefits;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;

import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.darren.darren.smokewise.Drawer;
import com.darren.darren.smokewise.R;
import com.darren.darren.smokewise.SessionManagement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class LifeBenefits extends Drawer implements View.OnClickListener {

  Long diffInDays, todayDate;
  Intent i;

  LinearLayout calendarLayout, linearLayout4, linearLayout3, linearLayoutTimeMoney, linearLayoutHealthCalendar;
  ImageButton moneyButton, healthButton, timeButton, calendarButton;
  Button buttonDone;
  CalendarView calendar;
  SessionManagement session;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_life_benefits);

    LayoutInflater inflater = (LayoutInflater) this
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View contentView = inflater.inflate(com.darren.darren.smokewise.R.layout.activity_life_benefits, null, false);
    mDrawer.addView(contentView, 0);

    //RelativeLayout
    calendarLayout = (LinearLayout) findViewById(com.darren.darren.smokewise.R.id.calendarLayout);
    linearLayout3 = (LinearLayout) findViewById(com.darren.darren.smokewise.R.id.linearLayout3);
    linearLayout4 = (LinearLayout) findViewById(com.darren.darren.smokewise.R.id.linearLayout4);
    linearLayoutHealthCalendar = (LinearLayout) findViewById(com.darren.darren.smokewise.R.id.linearLayoutHealthCalendar);
    linearLayoutTimeMoney = (LinearLayout) findViewById(com.darren.darren.smokewise.R.id.linearLayoutTimeMoney);
    buttonDone = (Button) findViewById(R.id.buttonDone);

    moneyButton = (ImageButton) findViewById(com.darren.darren.smokewise.R.id.moneyButton);
    healthButton = (ImageButton) findViewById(com.darren.darren.smokewise.R.id.healthButton);
    timeButton = (ImageButton) findViewById(com.darren.darren.smokewise.R.id.timeButton);
    calendarButton = (ImageButton) findViewById(com.darren.darren.smokewise.R.id.calendarButton);

    session = new SessionManagement(getApplicationContext());
    HashMap<String, Long> days = session.getDays();

    Calendar today = Calendar.getInstance();
    todayDate = today.getTimeInMillis();

    final Long savedDays = days.get(SessionManagement.KEY_DAYS);

    Log.d("Saved Days", "" + savedDays);
    if (savedDays == 0) {
      linearLayout3.setVisibility(View.GONE);
      linearLayout4.setVisibility(View.GONE);
      linearLayoutTimeMoney.setVisibility(View.GONE);
      linearLayoutHealthCalendar.setVisibility(View.GONE);
    } else {
      linearLayout3.setVisibility(View.VISIBLE);
      linearLayout4.setVisibility(View.VISIBLE);
      calendarLayout.setVisibility(View.GONE);
      linearLayoutHealthCalendar.setVisibility(View.VISIBLE);
      linearLayoutTimeMoney.setVisibility(View.VISIBLE);
    }

    moneyButton.setOnClickListener(this);
    healthButton.setOnClickListener(this);
    timeButton.setOnClickListener(this);
    calendarButton.setOnClickListener(this);
    buttonDone.setOnClickListener(this);

    calendar = (CalendarView) findViewById(com.darren.darren.smokewise.R.id.calendarView2);

    calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
      @Override
      public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

        Calendar day = Calendar.getInstance();
        day.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        day.set(Calendar.MONTH, month);
        day.set(Calendar.YEAR, year);

        Calendar today = Calendar.getInstance();
        long diff = today.getTimeInMillis() - day.getTimeInMillis();
        diffInDays = diff / (24 * 60 * 60 * 1000);


        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date1 = format.format(day.getTime());

        session.setDays(day.getTimeInMillis() / (24 * 60 * 60 * 1000));
        session.setSavedDate(date1);
        session.setTotalSaved(0);

        Log.d("Saved Days", "" + savedDays);
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(com.darren.darren.smokewise.R.menu.menu_life_benefits, menu);
    return true;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case com.darren.darren.smokewise.R.id.healthButton:
        i = new Intent(this, LifeBenefitsHealth.class);
        startActivity(i);
        break;

      case com.darren.darren.smokewise.R.id.moneyButton:
        i = new Intent(this, LifeBenefitsMoney.class);
        startActivity(i);
        break;

      case com.darren.darren.smokewise.R.id.timeButton:
        i = new Intent(this, LifeBenefitsTime.class);
        startActivity(i);
        break;

      case com.darren.darren.smokewise.R.id.calendarButton:
        new AlertDialog.Builder(this).setTitle("Warning").setMessage("Choosing a new date will reset savings")
            .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                linearLayout3.setVisibility(View.GONE);
                linearLayout4.setVisibility(View.GONE);
                calendar.setVisibility(View.VISIBLE);
                calendarLayout.setVisibility(View.VISIBLE);
                linearLayoutTimeMoney.setVisibility(View.GONE);
                linearLayoutHealthCalendar.setVisibility(View.GONE);
              }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {

              }
            })
            .show();
        break;

      case R.id.buttonDone:
        linearLayout3.setVisibility(View.VISIBLE);
        linearLayout4.setVisibility(View.VISIBLE);
        calendarLayout.setVisibility(View.GONE);
        linearLayoutHealthCalendar.setVisibility(View.VISIBLE);
        linearLayoutTimeMoney.setVisibility(View.VISIBLE);
        break;
    }
  }
}
