package com.darren.darren.smokewise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Finances extends Drawer implements View.OnClickListener {

  TextView textViewFinances2;
  Button buttonHealth, buttonJobProspects;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LayoutInflater inflater = (LayoutInflater) this
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View contentView = inflater.inflate(R.layout.activity_finances, null, false);

    mDrawer.addView(contentView, 0);

    textViewFinances2 = (TextView) findViewById(R.id.textViewFinances2);

    textViewFinances2.setText(Html.fromHtml("<b>" + "•\tSmoking a pack of 20 cigarettes a day costs approximately $160 a week" + " </b>" + ", this costs around" + "<b>" + " $8,300 per year" + "</b>" + " (based on pack of 20 that costs $22.80 in January 2016" + "<a href=\"http://www.quit.org.nz/21/reasons-to-quit/money-benefits\"> http://www.quit.org.nz/21/reasons-to-quit/money-benefits</a>" + ")."));


    buttonHealth = (Button) findViewById(R.id.buttonHealth);
    buttonJobProspects = (Button) findViewById(R.id.buttonJobProspects);

    buttonHealth.setOnClickListener(this);
    buttonJobProspects.setOnClickListener(this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_finances, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {

      case R.id.buttonHealth:
        startActivity(new Intent(Finances.this, Health.class));
        break;

      case R.id.buttonJobProspects:
        startActivity(new Intent(Finances.this, JobProspects.class));
        break;
    }
  }
}
