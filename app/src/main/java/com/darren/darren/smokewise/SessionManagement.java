package com.darren.darren.smokewise;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Darren on 24-Dec-15.
 */
public class SessionManagement {
  // Shared Preferences
  SharedPreferences pref;

  // Editor for Shared preferences
  SharedPreferences.Editor editor;

  // Context
  Context _context;

  // Shared pref mode
  int PRIVATE_MODE = 0;

  // Sharedpref file name
  private static final String PREF_NAME = "SmokeFreePref";

  // All Shared Preferences Keys
  private static final String KEY_IS_LOGGEDIN = "IsLoggedIn";

  // User name (make variable public to access from outside)
  public static final String KEY_NAME = "name";

  // Email address (make variable public to access from outside)
  public static final String KEY_EMAIL = "email";

  // Days difference calculated by calendar
  public static final String KEY_DAYS = "days";

  public static final String KEY_AVERAGE = "average";

  public static final String KEY_GOAL_TITLE = "goalTitle";

  public static final String KEY_GOAL_COST = "goalCost";

  public static final String KEY_HAS_GOAL = "hasGoal";

  public static final String KEY_TOTAL_SAVED = "totalSaved";

  public static final String KEY_TOTAL_SAVED_DAY = "totalSavedDay";

  public static final String KEY_VIDEO_PATH = "videoPath";

  public static final String KEY_SAVED_DATE = "savedDate";

  public static final String KEY_PUSH = "push";

  public static final String KEY_NICOTINE_TYPE = "nicotineType";

  public static final String KEY_NICOTINE_FREQUENCY = "nicotineFreq";

  public static final String KEY_HAS_NICOTINE = "hasNicotine";

  // Constructor
  public SessionManagement(Context context) {
    this._context = context;
    pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    editor = pref.edit();
  }

  /**
   * Create login session
   */
  public void createLoginSession(String name, String email) {
    // Storing login value as TRUE
    editor.putBoolean(KEY_IS_LOGGEDIN, true);

    // Storing name in pref
    editor.putString(KEY_NAME, name);

    // Storing email in pref
    editor.putString(KEY_EMAIL, email);

    // commit changes
    editor.commit();
  }

  /**
   * Get stored session data
   */
  public HashMap<String, String> getUserDetails() {
    HashMap<String, String> user = new HashMap<String, String>();
    // user name
    user.put(KEY_NAME, pref.getString(KEY_NAME, null));

    // user email id
    user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

    // return user
    return user;
  }

  /**
   * Check login method wil check user login status
   * If false it will redirect user to login page
   * Else won't do anything
   */
  public void checkLogin() {
    // Check login status
    if (!this.isLoggedIn()) {
      // user is not logged in redirect him to Login Activity
      Intent i = new Intent(_context, MainActivity.class);
      // Closing all the Activities
      i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

      // Add new Flag to start new Activity
      i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

      // Staring Login Activity
      _context.startActivity(i);
    }
  }

  /**
   * Clear session details
   */
  public void logoutUser() {
    // Clearing all data from Shared Preferences
    editor.putBoolean(KEY_IS_LOGGEDIN, false);
    editor.commit();

    // After logout redirect user to Login Activity
    Intent i = new Intent(_context, MainActivity.class);
    // Closing all the Activities
    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

    // Add new Flag to start new Activity
    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    // Staring Login Activity
    _context.startActivity(i);
  }

  /**
   * Quick check for login
   **/
  // Get Login State
  public boolean isLoggedIn() {
    return pref.getBoolean(KEY_IS_LOGGEDIN, false);
  }

  public void setDays(long days) {
    editor.putLong(KEY_DAYS, days);
    editor.commit();
  }

  public HashMap<String, Long> getDays() {
    HashMap<String, Long> days = new HashMap<String, Long>();
    days.put(KEY_DAYS, pref.getLong(KEY_DAYS, 0));
    return days;
  }

  public void setAverageSmokes(int average) {
    editor.putInt(KEY_AVERAGE, average);
    editor.commit();
  }

  public HashMap<String, Integer> getAverageSmokes() {
    HashMap<String, Integer> average = new HashMap<String, Integer>();
    average.put(KEY_AVERAGE, pref.getInt(KEY_AVERAGE, 0));
    return average;
  }

  public void setGoalTitle(String title) {
    editor.putBoolean(KEY_HAS_GOAL, true);
    editor.putString(KEY_GOAL_TITLE, title);
    editor.commit();
  }

  public HashMap<String, String> getGoalTitle() {
    HashMap<String, String> title = new HashMap<String, String>();
    title.put(KEY_GOAL_TITLE, pref.getString(KEY_GOAL_TITLE, null));
    return title;
  }

  public void setGoalCost(int cost) {
    editor.putInt(KEY_GOAL_COST, cost);
    editor.commit();
  }

  public HashMap<String, Integer> getGoalCost() {
    HashMap<String, Integer> cost = new HashMap<String, Integer>();
    cost.put(KEY_GOAL_COST, pref.getInt(KEY_GOAL_COST, 0));
    return cost;
  }

  public boolean hasGoal() {
    return pref.getBoolean(KEY_HAS_GOAL, false);
  }

  public void removeGoal() {
    editor.putBoolean(KEY_HAS_GOAL, false);
    editor.remove(KEY_GOAL_COST);
    editor.remove(KEY_GOAL_TITLE);
    editor.commit();

  }

  public void setTotalSaved(int saved) {
    editor.putInt(KEY_TOTAL_SAVED, saved);
    editor.commit();
  }

  public HashMap<String, Integer> getTotalSaved() {
    HashMap<String, Integer> saved = new HashMap<String, Integer>();
    saved.put(KEY_TOTAL_SAVED, pref.getInt(KEY_TOTAL_SAVED, 0));
    return saved;

  }

  public void setTotalSavedDay(long totalSavedDay) {
    editor.putLong(KEY_TOTAL_SAVED_DAY, totalSavedDay);
    editor.commit();
  }

  public HashMap<String, Long> getTotalSavedDay() {
    HashMap<String, Long> totalSavedDay = new HashMap<String, Long>();
    totalSavedDay.put(KEY_TOTAL_SAVED_DAY, pref.getLong(KEY_TOTAL_SAVED_DAY, 0));
    return totalSavedDay;
  }

  public void setVideoPath(String path) {
    editor.putString(KEY_VIDEO_PATH, path);
    editor.commit();
  }

  public HashMap<String, String> getVideoPath() {
    HashMap<String, String> path = new HashMap<String, String>();
    path.put(KEY_VIDEO_PATH, pref.getString(KEY_VIDEO_PATH, null));
    return path;
  }

  public void removeVideoPath() {
    editor.remove(KEY_VIDEO_PATH);
    editor.commit();
  }

  public void setSavedDate(String date) {
    editor.putString(KEY_SAVED_DATE, date);
    editor.commit();
  }

  public HashMap<String, String> getSavedDate() {
    HashMap<String, String> date = new HashMap<String, String>();

    date.put(KEY_SAVED_DATE, pref.getString(KEY_SAVED_DATE, null));

    return date;
  }

  public void setLogin(boolean isLoggedIn) {
    editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
    editor.commit();
    Log.d("Login", "User login session modified!");
  }

  public void setNicotineType(String type) {
    editor.putBoolean(KEY_HAS_NICOTINE, true);
    editor.putString(KEY_NICOTINE_TYPE, type);
    editor.commit();
  }

  public HashMap<String, String> getNicotineType() {
    HashMap<String, String> type = new HashMap<String, String>();
    type.put(KEY_NICOTINE_TYPE, pref.getString(KEY_NICOTINE_TYPE, null));
    return type;
  }

  public void setNicotineFrequency(int frequency) {
    editor.putInt(KEY_NICOTINE_FREQUENCY, frequency);
    editor.commit();
  }

  public HashMap<String, Integer> getNicotineFrequency() {
    HashMap<String, Integer> frequency = new HashMap<String, Integer>();
    frequency.put(KEY_NICOTINE_FREQUENCY, pref.getInt(KEY_NICOTINE_FREQUENCY, 0));
    return frequency;
  }

  public boolean hasNicotine() {
    return pref.getBoolean(KEY_HAS_NICOTINE, false);
  }

}
