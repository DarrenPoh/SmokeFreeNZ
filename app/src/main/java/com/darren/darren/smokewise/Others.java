package com.darren.darren.smokewise;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Others extends Drawer implements View.OnClickListener {

    Button buttonAppearance, buttonSocial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_others, null, false);

        mDrawer.addView(contentView, 0);

        buttonSocial = (Button) findViewById(R.id.buttonSocial);
        buttonAppearance = (Button) findViewById(R.id.buttonAppearance);

        buttonAppearance.setOnClickListener(this);
        buttonSocial.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSocial:
                startActivity(new Intent(Others.this, Social.class));

                break;

            case R.id.buttonAppearance:
                startActivity(new Intent(Others.this, Looks.class));

                break;
        }
    }
}
