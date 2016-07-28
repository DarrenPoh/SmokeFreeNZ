package com.darren.darren.smokewise.LifeBenefits;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.darren.darren.smokewise.Drawer;
import com.darren.darren.smokewise.R;
import com.darren.darren.smokewise.SessionManagement;

import java.util.Calendar;
import java.util.HashMap;

public class LifeBenefitsTime extends Drawer implements View.OnClickListener{
    TextView lifeRegainedText, daysQuitText, quitDateText, textViewDaysQuit1;
    Long diffInDays, todayDate, savedDays;
    int lifeRegained, hours, day, minutes;
    String savedDate;


    SessionManagement session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(com.darren.darren.smokewise.R.layout.activity_life_benefits_time, null, false);
        mDrawer.addView(contentView, 0);

        session = new SessionManagement(getApplicationContext());

        HashMap<String, Long> days = session.getDays();
        savedDays = days.get(SessionManagement.KEY_DAYS);

        HashMap<String, String> date = session.getSavedDate();
        savedDate = date.get(SessionManagement.KEY_SAVED_DATE);

        Calendar today = Calendar.getInstance();
        todayDate = today.getTimeInMillis() / (24 * 60 * 60 * 1000);

        diffInDays = todayDate - savedDays;


        lifeRegainedText = (TextView) findViewById(com.darren.darren.smokewise.R.id.textViewLifeRegained2);
        daysQuitText = (TextView) findViewById(com.darren.darren.smokewise.R.id.textViewDaysQuit2);
        quitDateText = (TextView) findViewById(R.id.textViewSmokingQuit2);
        textViewDaysQuit1 = (TextView) findViewById(R.id.textViewDaysQuit1);



        //need to change average smokes
        lifeRegained = ((Integer.parseInt(Long.toString(diffInDays)) * 20) * 11);
        day = lifeRegained / (60 * 24);
        hours = (lifeRegained / 60) % 24;
        minutes = lifeRegained % 60;



        lifeRegainedText.setText(day + " days, " + hours + " hours and " + minutes +" minutes");
        daysQuitText.setText(Long.toString(diffInDays));
        quitDateText.setText(savedDate);


        Log.d("Saved Quit Date", "" + savedDays);
        Log.d("Todays Date", "" + todayDate);

        if (diffInDays <= 0) {
            lifeRegainedText.setText("0");
            daysQuitText.setText(Long.toString(diffInDays * -1));
            textViewDaysQuit1.setText("Days until quit smoking");
        }

    }

    @Override
    public void onClick(View v) {

    }


}
