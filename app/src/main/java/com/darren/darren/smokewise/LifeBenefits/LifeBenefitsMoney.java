package com.darren.darren.smokewise.LifeBenefits;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.darren.darren.smokewise.Drawer;
import com.darren.darren.smokewise.R;
import com.darren.darren.smokewise.SessionManagement;

import java.util.Calendar;
import java.util.HashMap;

public class LifeBenefitsMoney extends Drawer implements View.OnClickListener {

  TextView moneySavedText, annualSavedText, weeklySavedText, textViewGoalProgress, textViewTotalSaved;
  long diffInDays, savedDays, todayDate;
  double moneySaved, annualSaved, weeklySaved, progressPercentage;
  EditText averageSmoked, goalTitle, goalCost;
  int savedAverageSmokes, savedGoalCost, savedTotalPref;
  Button buttonEditSmokes, buttonAddGoal, buttonSetGoal, buttonRemoveGoal;
  LinearLayout goalLayout, goalCostLayout;
  String savedGoalTitle;
  ProgressBar goalProgress;

  float totalSaved;
  long totalSavedDay, savedDatePref;

  InputMethodManager inputManager;

  SessionManagement session;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LayoutInflater inflater = (LayoutInflater) this
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View contentView = inflater.inflate(com.darren.darren.smokewise.R.layout.activity_life_benefits_money, null, false);
    mDrawer.addView(contentView, 0);

    session = new SessionManagement(getApplicationContext());

    HashMap<String, Long> days = session.getDays();
    savedDays = days.get(SessionManagement.KEY_DAYS);

    HashMap<String, Integer> average = session.getAverageSmokes();
    savedAverageSmokes = average.get(SessionManagement.KEY_AVERAGE);

    HashMap<String, String> goalTitlePref = session.getGoalTitle();
    savedGoalTitle = goalTitlePref.get(SessionManagement.KEY_GOAL_TITLE);

    HashMap<String, Integer> goalCostPref = session.getGoalCost();
    savedGoalCost = goalCostPref.get(SessionManagement.KEY_GOAL_COST);

    HashMap<String, Integer> totalSavedPref = session.getTotalSaved();
    savedTotalPref = totalSavedPref.get(SessionManagement.KEY_TOTAL_SAVED);

    HashMap<String, Long> totalSavedDatePref = session.getTotalSavedDay();
    savedDatePref = totalSavedDatePref.get(SessionManagement.KEY_TOTAL_SAVED_DAY);


    Calendar today = Calendar.getInstance();
    todayDate = today.getTimeInMillis() / (24 * 60 * 60 * 1000);
    totalSavedDay = today.getTimeInMillis() / (24 * 60 * 60 * 1000);

    session.setTotalSavedDay(totalSavedDay);

    averageSmoked = (EditText) findViewById(R.id.editTextAverageSmoked);
    moneySavedText = (TextView) findViewById(R.id.moneySavedText2);
    annualSavedText = (TextView) findViewById(R.id.textViewAnnualSaved);
    weeklySavedText = (TextView) findViewById(R.id.textViewWeeklySaved);
    textViewTotalSaved = (TextView) findViewById(R.id.textViewTotalSaved2);
    buttonEditSmokes = (Button) findViewById(R.id.buttonEditSmokes);
    buttonAddGoal = (Button) findViewById(R.id.buttonAddGoal);
    buttonSetGoal = (Button) findViewById(R.id.buttonSetGoal);
    goalTitle = (EditText) findViewById(R.id.editTextGoalTitle);
    goalCost = (EditText) findViewById(R.id.editTextGoalCost);
    buttonRemoveGoal = (Button) findViewById(R.id.buttonRemoveGoal);
    textViewGoalProgress = (TextView) findViewById(R.id.textViewGoalProgress);
    goalProgress = (ProgressBar) findViewById(R.id.progressBarGoalProgress);

    goalProgress.setScaleY(3f);

    goalLayout = (LinearLayout) findViewById(R.id.linearLayoutGoal);
    goalCostLayout = (LinearLayout) findViewById(R.id.linearLayoutGoalCost);

    goalCost.setOnClickListener(this);

    goalCostLayout.setVisibility(View.INVISIBLE);
    goalLayout.setVisibility(View.INVISIBLE);


    buttonSetGoal.setOnClickListener(this);
    buttonEditSmokes.setOnClickListener(this);
    buttonAddGoal.setOnClickListener(this);
    buttonRemoveGoal.setOnClickListener(this);

    buttonRemoveGoal.setVisibility(View.INVISIBLE);

    averageSmoked.setText("" + savedAverageSmokes);

    //checks if date is in future
    if ((todayDate - savedDays) <= 0) {
      moneySaved = 0;
      moneySavedText.setText("$" + Double.toString(moneySaved));
    } else {

      moneySaved = ((((todayDate - savedDays)) * savedAverageSmokes) * .95);
      String sMoneySaved = String.format("%.2f", moneySaved);
      moneySavedText.setText("$" + sMoneySaved);
    }

    Log.d("Today Date Pref", "" + todayDate);
    Log.d("Saved Date Pref", "" + savedDatePref);

    if ((todayDate - savedDatePref) > 0) {
      // checks if todays date is greater than the date of saved amount.
      // new total = current total + (difference in days * smokes)
      // does not recalculate if opened multiple times in the same day
      savedTotalPref = (savedTotalPref + (int) Math.round((((todayDate - savedDatePref)) * savedAverageSmokes) * .95));
      //savedTotalPref = (savedTotalPref) + (int) Math.round(savedAverageSmokes * .95);
      session.setTotalSaved(savedTotalPref);
      //sets saved date as current date if opened on a new day
      session.setTotalSavedDay(todayDate);
    }

    weeklySaved = ((savedAverageSmokes * .95) * 7);
    annualSaved = ((savedAverageSmokes * .95) * 365);
    String sWeeklySaved = String.format("%.2f", weeklySaved);
    String sAnnualSaved = String.format("%.2f", annualSaved);
    weeklySavedText.setText("$" + sWeeklySaved);
    annualSavedText.setText("$" + sAnnualSaved);

    //Goal
    if (session.hasGoal()) {
      goalLayout.setVisibility(View.VISIBLE);
      buttonRemoveGoal.setVisibility(View.VISIBLE);
      buttonSetGoal.setVisibility(View.VISIBLE);
      buttonAddGoal.setVisibility(View.GONE);
      goalCostLayout.setVisibility(View.VISIBLE);
      //progressPercentage = (int) Math.round((((todayDate - savedDays)) * savedAverageSmokes) * .95);
    } else {
      goalLayout.setVisibility(View.GONE);
    }
    Log.d("Total Saved", "" + savedTotalPref);
    Log.d("Saved Goal Cost", "" + savedGoalCost);
    goalTitle.setText(savedGoalTitle);
    goalCost.setText(Integer.toString(savedGoalCost));

    progressPercentage = (((double) savedTotalPref / (double) savedGoalCost) * 100);
    goalProgress.setProgress((int) Math.round(progressPercentage));

    String sProgressPercentage = String.format("%.2f", progressPercentage);
    textViewGoalProgress.setText(sProgressPercentage + "%");

    if (moneySaved > 0 && savedTotalPref == 0) {
      savedTotalPref = (int) Math.round(moneySaved);
      session.setTotalSaved(savedTotalPref);
      if (session.hasGoal()) {
        progressPercentage = (((double) savedTotalPref / (double) savedGoalCost) * 100);
        goalProgress.setProgress((int) Math.round(progressPercentage));

        textViewGoalProgress.setText(sProgressPercentage + "%");
        session.setTotalSaved(savedTotalPref);
        if ((todayDate - savedDays) <= 0) {
          progressPercentage = 0;
          textViewGoalProgress.setText("0");
        }
      }
    }

    textViewTotalSaved.setText("$" + savedTotalPref);

    goalCost.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
          goalCost.setText("");
        }
      }
    });
    goalTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
          goalTitle.setText("");
        }
      }
    });

    inputManager = (InputMethodManager)
        getSystemService(Context.INPUT_METHOD_SERVICE);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.buttonEditSmokes:
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS);

        savedAverageSmokes = Integer.parseInt(averageSmoked.getText().toString());
        session.setAverageSmokes(savedAverageSmokes);
        averageSmoked.setText("" + savedAverageSmokes);

        Log.d("Money", (todayDate - savedDays) + "");

        if ((todayDate - savedDays) <= 0) {
          moneySaved = 0;
          moneySavedText.setText("$" + Double.toString(moneySaved));
          session.setTotalSaved(0);

        } else {

          moneySaved = ((((todayDate - savedDays)) * savedAverageSmokes) * .95);
          String sMoneySaved = String.format("%.2f", moneySaved);
          moneySavedText.setText("$" + sMoneySaved);
          weeklySaved = ((savedAverageSmokes * .95) * 7);
          annualSaved = ((savedAverageSmokes * .95) * 365);
          String sWeeklySaved = String.format("%.2f", weeklySaved);
          String sAnnualSaved = String.format("%.2f", annualSaved);
          weeklySavedText.setText("$" + sWeeklySaved);
          annualSavedText.setText("$" + sAnnualSaved);

          if (savedTotalPref == 0) {
            savedTotalPref = (int) Math.round((((todayDate - savedDays)) * savedAverageSmokes) * .95);
            session.setTotalSaved(savedTotalPref);
          } else {
            progressPercentage = (((double) savedTotalPref / (double) savedGoalCost) * 100);
            goalProgress.setProgress((int) Math.round(progressPercentage));
            String sProgressPercentage = String.format("%.2f", progressPercentage);
            textViewGoalProgress.setText(sProgressPercentage + "%");
          }
        }
        break;

      case R.id.buttonAddGoal:
        goalLayout.setVisibility(View.VISIBLE);
        buttonAddGoal.setVisibility(View.GONE);
        buttonRemoveGoal.setVisibility(View.VISIBLE);
        goalCostLayout.setVisibility(View.VISIBLE);
        break;

      case R.id.buttonSetGoal:
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS);

        savedGoalTitle = goalTitle.getText().toString().trim();
        session.setGoalTitle(savedGoalTitle);

        savedGoalCost = Integer.parseInt(goalCost.getText().toString());
        session.setGoalCost(savedGoalCost);

        if (savedTotalPref == 0) {
          progressPercentage = (int) Math.round((((todayDate - savedDays)) * savedAverageSmokes) * .95);
          goalProgress.setProgress((int) Math.round(progressPercentage));
          String sProgressPercentage = String.format("%.2f", progressPercentage);
          textViewGoalProgress.setText(sProgressPercentage + "%");
          goalCost.setText(Integer.toString(savedGoalCost));
        } else {
          progressPercentage = (((double) savedTotalPref / (double) savedGoalCost) * 100);
          goalProgress.setProgress((int) Math.round(progressPercentage));
          String sProgressPercentage = String.format("%.2f", progressPercentage);
          textViewGoalProgress.setText(sProgressPercentage + "%");
          goalCost.setText(Integer.toString(savedGoalCost));
        }
        if ((todayDate - savedDays) <= 0) {
          progressPercentage = 0;
          textViewGoalProgress.setText("0");
        }

        break;

      case R.id.buttonRemoveGoal:
        session.removeGoal();
        goalLayout.setVisibility(View.GONE);
        buttonAddGoal.setVisibility(View.VISIBLE);
        buttonRemoveGoal.setVisibility(View.GONE);
        goalCostLayout.setVisibility(View.GONE);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        break;
    }
  }
}
