package com.darren.darren.smokewise;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class JobProspects extends Drawer implements View.OnClickListener {

    TextView textViewJob3;
    Button buttonFinances, buttonAppearance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_job_prospects, null, false);

        mDrawer.addView(contentView, 0);

        textViewJob3 = (TextView) findViewById(R.id.textViewJob3);

        textViewJob3.setText(Html.fromHtml(""));

        buttonFinances = (Button) findViewById(R.id.buttonFinances);
        buttonAppearance = (Button) findViewById(R.id.buttonAppearance);

        buttonAppearance.setOnClickListener(this);
        buttonFinances.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonFinances:
                startActivity(new Intent(JobProspects.this, Finances.class));

                break;

            case R.id.buttonAppearance:
                startActivity(new Intent(JobProspects.this, Looks.class));

                break;
        }

    }
}
