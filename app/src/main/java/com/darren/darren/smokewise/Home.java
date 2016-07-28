package com.darren.darren.smokewise;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.darren.darren.smokewise.LifeBenefits.LifeBenefits;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.widget.LikeView;

public class Home extends Drawer implements View.OnClickListener {


  LinearLayout linearLayout1, linearLayout2, linearLayout3;
  ImageButton buttonFacebook, buttonLifeBenefits, buttonExtraHelp, buttonMotivation, buttonVideo, buttonSettings;
  SessionManagement session;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_home);

    FacebookSdk.sdkInitialize(getApplicationContext());
    AppEventsLogger.activateApp(this);


    LayoutInflater inflater = (LayoutInflater) this
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View contentView = inflater.inflate(com.darren.darren.smokewise.R.layout.activity_home, null, false);
    mDrawer.addView(contentView, 0);

    session = new SessionManagement(getApplicationContext());


    new AlertDialog.Builder(this).setTitle("Reminder").setMessage("Don't forget to view your Motivational Video!")
        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            startActivity(new Intent(Home.this, WatchVideo.class));
            finish();
          }
        })
        .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {

          }
        })
        .show();

    linearLayout1 = (LinearLayout) findViewById(com.darren.darren.smokewise.R.id.homeLinearLayout1);
    linearLayout2 = (LinearLayout) findViewById(com.darren.darren.smokewise.R.id.homeLinearLayout2);
    linearLayout3 = (LinearLayout) findViewById(com.darren.darren.smokewise.R.id.homeLinearLayout3);


    buttonFacebook = (ImageButton) findViewById(com.darren.darren.smokewise.R.id.buttonFacebook);
    buttonLifeBenefits = (ImageButton) findViewById(com.darren.darren.smokewise.R.id.buttonLifeBenefits);
    buttonExtraHelp = (ImageButton) findViewById(com.darren.darren.smokewise.R.id.buttonExtraHelp);
    buttonMotivation = (ImageButton) findViewById(com.darren.darren.smokewise.R.id.buttonMotivation);
    buttonVideo = (ImageButton) findViewById(com.darren.darren.smokewise.R.id.buttonVideo);
    buttonSettings = (ImageButton) findViewById(com.darren.darren.smokewise.R.id.buttonSettings);

    buttonFacebook.setOnClickListener(this);
    buttonLifeBenefits.setOnClickListener(this);
    buttonExtraHelp.setOnClickListener(this);
    buttonMotivation.setOnClickListener(this);
    buttonVideo.setOnClickListener(this);
    buttonSettings.setOnClickListener(this);

    LikeView likeView = (LikeView) findViewById(R.id.likeView);
    likeView.setLikeViewStyle(LikeView.Style.STANDARD);
    likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);

    likeView.setObjectIdAndType(
        "https://www.facebook.com/SmokeFree-NZ-796120033855044/",
        LikeView.ObjectType.PAGE);

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case com.darren.darren.smokewise.R.id.buttonSettings:

        startActivity(new Intent(this, Account.class));

        break;


      case com.darren.darren.smokewise.R.id.buttonMotivation:

        startActivity(new Intent(this, Motivation.class));

        break;

      case com.darren.darren.smokewise.R.id.buttonExtraHelp:

        this.startActivity(new Intent(this, ExtraHelp.class));

        break;

      case com.darren.darren.smokewise.R.id.buttonFacebook:

        try {
          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/SmokeFree-NZ-796120033855044/"));
          startActivity(intent);
        } catch (Exception e) {
          startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/SmokeFree-NZ-796120033855044/")));
        }

        break;

      case com.darren.darren.smokewise.R.id.buttonLifeBenefits:

        this.startActivity(new Intent(this, LifeBenefits.class));

        break;

      case com.darren.darren.smokewise.R.id.buttonVideo:

        this.startActivity(new Intent(this, WatchVideo.class));

        break;

    }

  }

  @Override
  protected void onStart() {
    super.onStart();

    if (authenticate()) {

    } else {
      startActivity(new Intent(Home.this, MainActivity.class));
      finish();
    }
  }

  private boolean authenticate() {
    return session.isLoggedIn();
  }


}
