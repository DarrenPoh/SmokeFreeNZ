package com.darren.darren.smokewise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class Looks extends Drawer implements View.OnClickListener {

  Button buttonJobProspects, buttonOthers;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LayoutInflater inflater = (LayoutInflater) this
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View contentView = inflater.inflate(R.layout.activity_looks, null, false);

    mDrawer.addView(contentView, 0);

    buttonJobProspects = (Button) findViewById(R.id.buttonJobProspects);
    buttonOthers = (Button) findViewById(R.id.buttonOthers);

    buttonOthers.setOnClickListener(this);
    buttonJobProspects.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.buttonJobProspects:
        startActivity(new Intent(Looks.this, JobProspects.class));
        break;

      case R.id.buttonOthers:
        startActivity(new Intent(Looks.this, Others.class));
        break;
    }
  }
}
