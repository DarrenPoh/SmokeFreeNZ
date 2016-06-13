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

public class Social extends Drawer implements View.OnClickListener{

    Button buttonOthers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_social, null, false);

        mDrawer.addView(contentView, 0);

        buttonOthers = (Button) findViewById(R.id.buttonOthers);
        buttonOthers.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonOthers:
                startActivity(new Intent(Social.this, Others.class));
                break;
        }

    }
}
