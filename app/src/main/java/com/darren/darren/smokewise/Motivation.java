package com.darren.darren.smokewise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Motivation extends Drawer implements View.OnClickListener{

    Button buttonHealth, buttonFinances, buttonJob, buttonAppearance, buttonSocial, buttonOthers;
    Spinner spinner;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(com.darren.darren.smokewise.R.layout.activity_motivation, null, false);

        mDrawer.addView(contentView, 0);

        buttonHealth = (Button) findViewById(R.id.buttonHealth);
        buttonFinances = (Button) findViewById(R.id.buttonFinances);
        buttonJob = (Button) findViewById(R.id.buttonJobProspects);
        buttonAppearance = (Button) findViewById(R.id.buttonAppearance);
        buttonSocial = (Button) findViewById(R.id.buttonSocial);
        buttonOthers = (Button) findViewById(R.id.buttonOthers);

        buttonHealth.setOnClickListener(this);
        buttonFinances.setOnClickListener(this);
        buttonJob.setOnClickListener(this);
        buttonAppearance.setOnClickListener(this);
        buttonSocial.setOnClickListener(this);
        buttonOthers.setOnClickListener(this);

        /*spinner = (Spinner) findViewById(com.darren.darren.smokewise.R.id.motivationSpinner);

        String[] motivations = new String[]{
                "Select item below:",
                "Present/Future Health",
                "Financial Needs",
                "Job Prospects",
                "Appearance",
                "Social Needs",
                "Others",
        };


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,motivations
        );
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, com.darren.darren.smokewise.R.array.motivation_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                final Intent intent;
                switch (position) {
                    case 1:
                        intent = new Intent(Motivation.this, Health.class);
                        startActivity(intent);

                        break;

                    case 2:
                        intent = new Intent(Motivation.this, Finances.class);
                        startActivity(intent);

                        break;

                    case 3:
                        intent = new Intent(Motivation.this, JobProspects.class);
                        startActivity(intent);

                        break;

                    case 4:
                        intent = new Intent(Motivation.this, Looks.class);
                        startActivity(intent);

                        break;

                    case 5:
                        intent = new Intent(Motivation.this, Social.class);
                        startActivity(intent);

                        break;

                    case 6:
                        intent = new Intent(Motivation.this, Others.class);
                        startActivity(intent);

                        break;


                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.darren.darren.smokewise.R.menu.menu_motivation, menu);
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonHealth:
                i = new Intent(this, Health.class);
                startActivity(i);
                break;
            case R.id.buttonFinances:
                i = new Intent(this, Finances.class);
                startActivity(i);
                break;
            case R.id.buttonJobProspects:
                i = new Intent(this, JobProspects.class);
                startActivity(i);
                break;
            case R.id.buttonAppearance:
                i = new Intent(this, Looks.class);
                startActivity(i);
                break;
            case R.id.buttonSocial:
                i = new Intent(this, Social.class);
                startActivity(i);
                break;
            case R.id.buttonOthers:
                i = new Intent(this, Others.class);
                startActivity(i);
                break;
        }

    }
}
