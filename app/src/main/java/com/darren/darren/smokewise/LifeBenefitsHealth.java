package com.darren.darren.smokewise;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;

public class LifeBenefitsHealth extends Drawer {

    long diffInDays, savedDays, todayDate;
    ProgressBar progressBarPulseRate, progressBarOxygenLevel, progressBarCarbonMonoxide, progressBarNicotineExpelled, progressBarBreathing, progressBarTasteSmell,
            progressBarEnergy, progressBarCoughWheezing, progressBarHeartAttack, progressBarLungCancer, progressBarChronicDisease;
    TextView pulseRateProgress, oxygenLevelProgress, carbonMonoxideProgress, nicotineExpelledProgress, breathingProgress, tasteSmellProgress, energyProgress,
            coughWheezingProgress, heartAttackProgress, lungCancerProgress, chronicDiseaseProgress;

    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_life_benefits_health);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(com.darren.darren.smokewise.R.layout.activity_life_benefits_health, null, false);
        mDrawer.addView(contentView, 0);

        session = new SessionManagement(getApplicationContext());

        HashMap<String, Long> days = session.getDays();

        savedDays = days.get(SessionManagement.KEY_DAYS);

        Calendar today = Calendar.getInstance();
        todayDate = today.getTimeInMillis() / (24 * 60 * 60 * 1000);



        progressBarPulseRate = (ProgressBar) findViewById(com.darren.darren.smokewise.R.id.progressBarPulseRate);
        progressBarOxygenLevel = (ProgressBar) findViewById(com.darren.darren.smokewise.R.id.progressBarOxygenLevel);
        progressBarCarbonMonoxide = (ProgressBar) findViewById(com.darren.darren.smokewise.R.id.progressBarCarbonMonoxide);
        progressBarNicotineExpelled = (ProgressBar) findViewById(com.darren.darren.smokewise.R.id.progressBarNicotineExpelled);
        progressBarBreathing = (ProgressBar) findViewById(com.darren.darren.smokewise.R.id.progressBarBreathing);
        progressBarTasteSmell = (ProgressBar) findViewById(com.darren.darren.smokewise.R.id.progressBarTasteSmell);
        progressBarEnergy = (ProgressBar) findViewById(com.darren.darren.smokewise.R.id.progressBarEnergy);
        progressBarCoughWheezing = (ProgressBar) findViewById(com.darren.darren.smokewise.R.id.progressBarCoughWheeze);
        progressBarHeartAttack = (ProgressBar) findViewById(com.darren.darren.smokewise.R.id.progressBarHeartAttack);
        progressBarLungCancer = (ProgressBar) findViewById(com.darren.darren.smokewise.R.id.progressBarLungCancer);
        progressBarChronicDisease = (ProgressBar) findViewById(com.darren.darren.smokewise.R.id.progressBarCOPD);

        pulseRateProgress = (TextView) findViewById(com.darren.darren.smokewise.R.id.textViewPulseRateProgress);
        oxygenLevelProgress = (TextView) findViewById(com.darren.darren.smokewise.R.id.textViewOxygenLevelProgress);
        carbonMonoxideProgress = (TextView) findViewById(com.darren.darren.smokewise.R.id.textViewCarbonMonoxideProgress);
        nicotineExpelledProgress = (TextView) findViewById(com.darren.darren.smokewise.R.id.textViewNicotineExpelledProgress);
        breathingProgress = (TextView) findViewById(com.darren.darren.smokewise.R.id.textViewBreathingProgress);
        energyProgress = (TextView) findViewById(com.darren.darren.smokewise.R.id.textViewEnergyProgress);
        coughWheezingProgress = (TextView) findViewById(com.darren.darren.smokewise.R.id.textViewCoughWheezeProgress);
        heartAttackProgress = (TextView) findViewById(com.darren.darren.smokewise.R.id.textViewHeartAttackProgress);
        lungCancerProgress = (TextView) findViewById(com.darren.darren.smokewise.R.id.textViewLungCancerProgress);
        chronicDiseaseProgress = (TextView) findViewById(com.darren.darren.smokewise.R.id.textViewCOPDProgress);
        tasteSmellProgress = (TextView) findViewById(com.darren.darren.smokewise.R.id.textViewTasteSmellProgress);

        progressBarPulseRate.setScaleY(3f);
        progressBarOxygenLevel.setScaleY(3f);
        progressBarCarbonMonoxide.setScaleY(3f);
        progressBarNicotineExpelled.setScaleY(3f);
        progressBarBreathing.setScaleY(3f);
        progressBarTasteSmell.setScaleY(3f);
        progressBarEnergy.setScaleY(3f);
        progressBarCoughWheezing.setScaleY(3f);
        progressBarHeartAttack.setScaleY(3f);
        progressBarLungCancer.setScaleY(3f);
        progressBarChronicDisease.setScaleY(3f);



        diffInDays = todayDate - savedDays;

        if (Integer.parseInt(Long.toString(diffInDays)) <= 0) {
            pulseRateProgress.setText("0%");
            breathingProgress.setText("0%");
            carbonMonoxideProgress.setText("0%");
            tasteSmellProgress.setText("0%");
            breathingProgress.setText("0%");
            nicotineExpelledProgress.setText("0%");
            oxygenLevelProgress.setText("0%");
            heartAttackProgress.setText("0%");
            lungCancerProgress.setText("0%");
            chronicDiseaseProgress.setText("0%");
            heartAttackProgress.setText("0%");
            lungCancerProgress.setText("0%");
            chronicDiseaseProgress.setText("0%");
            coughWheezingProgress.setText("0%");
            energyProgress.setText("0%");
            progressBarBreathing.setProgress(0);
            progressBarTasteSmell.setProgress(0);

        } else {


            //PulseRate
            progressBarPulseRate.setProgress(Integer.parseInt(Long.toString(diffInDays)) * 20);
            pulseRateProgress.setText(Integer.parseInt(Long.toString(diffInDays))*20 + "%");

            //OxygenLevel
            progressBarOxygenLevel.setProgress(Integer.parseInt(Long.toString(diffInDays)) * 20);
            oxygenLevelProgress.setText(Integer.parseInt(Long.toString(diffInDays)) * 20 + "%");

            //CarbonMonoxide
            progressBarCarbonMonoxide.setProgress((Integer.parseInt(Long.toString(diffInDays)) * 20) - 2);
            carbonMonoxideProgress.setText((Integer.parseInt(Long.toString(diffInDays))*20) - 2 + "%");

            //NicotineExpelled
            progressBarNicotineExpelled.setProgress((Integer.parseInt(Long.toString(diffInDays)) * 20) - 2);
            nicotineExpelledProgress.setText((Integer.parseInt(Long.toString(diffInDays))*20) - 2 + "%");

            //Breathing
            progressBarBreathing.setProgress((Integer.parseInt(Long.toString(diffInDays)) * 20) + 5);
            breathingProgress.setText((Integer.parseInt(Long.toString(diffInDays))*20) + 5 + "%");

            //TasteAndSmell
            progressBarTasteSmell.setProgress((Integer.parseInt(Long.toString(diffInDays)) * 20) + 5);
            tasteSmellProgress.setText((Integer.parseInt(Long.toString(diffInDays))*20) + 5 + "%");

            //Energy
            progressBarEnergy.setProgress(Integer.parseInt(Long.toString(diffInDays)) * 10);
            energyProgress.setText(Integer.parseInt(Long.toString(diffInDays)) * 10 + "%");

            //CoughAndWheeze
            progressBarCoughWheezing.setProgress(Integer.parseInt(Long.toString(diffInDays)));
            coughWheezingProgress.setText(Integer.parseInt(Long.toString(diffInDays)) + "%");

            //HearAttack
            progressBarHeartAttack.setProgress(Integer.parseInt(Long.toString(diffInDays)) /10);
            heartAttackProgress.setText(Double.parseDouble(Long.toString(diffInDays)) / 10 + "%");

            //LungCancer
            progressBarLungCancer.setProgress(Integer.parseInt(Long.toString(diffInDays))/10);
            lungCancerProgress.setText(Double.parseDouble(Long.toString(diffInDays)) / 10 + "%");

            //ChronicDisease
            progressBarChronicDisease.setProgress(Integer.parseInt(Long.toString(diffInDays)) / 10);

            chronicDiseaseProgress.setText(Double.parseDouble(Long.toString(diffInDays)) / 10 + "%");


            if (Integer.parseInt(Long.toString(diffInDays)) >= 5) {
                pulseRateProgress.setText("100%");
                breathingProgress.setText("100%");

                tasteSmellProgress.setText("100%");
                breathingProgress.setText("100%");

                oxygenLevelProgress.setText("100%");
            }

            if (Integer.parseInt(Long.toString(diffInDays)) >= 6) {
                carbonMonoxideProgress.setText("100%");
                nicotineExpelledProgress.setText("100%");
            }

            if (Integer.parseInt(Long.toString(diffInDays)) >= 10) {
                energyProgress.setText("100%");
            }

            if (Integer.parseInt(Long.toString(diffInDays)) >= 100) {
                coughWheezingProgress.setText("100%");
            }

            if (Integer.parseInt(Long.toString(diffInDays)) >= 1000) {
                heartAttackProgress.setText("100%");
                lungCancerProgress.setText("100%");
                chronicDiseaseProgress.setText("100%");
            }
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.darren.darren.smokewise.R.menu.menu_life_benefits_health, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.darren.darren.smokewise.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
