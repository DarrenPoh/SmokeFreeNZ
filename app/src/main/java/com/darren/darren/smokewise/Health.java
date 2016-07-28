package com.darren.darren.smokewise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Health extends Drawer implements View.OnClickListener{

    TextView textViewHealth12, textViewHealth13, textViewHealth14, textViewHealth15, textViewHealth16, textViewHealth17, textViewHealth18, textViewHealth19, textViewHealth20, textViewHealth21;

    Button buttonFinances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_health, null, false);

        mDrawer.addView(contentView, 0);

        textViewHealth12 = (TextView) findViewById(R.id.textViewHealth12);
        textViewHealth13 = (TextView) findViewById(R.id.textViewHealth13);
        textViewHealth14 = (TextView) findViewById(R.id.textViewHealth14);
        textViewHealth15 = (TextView) findViewById(R.id.textViewHealth15);
        textViewHealth16 = (TextView) findViewById(R.id.textViewHealth16);
        textViewHealth17 = (TextView) findViewById(R.id.textViewHealth17);
        textViewHealth18 = (TextView) findViewById(R.id.textViewHealth18);
        textViewHealth19 = (TextView) findViewById(R.id.textViewHealth19);
        textViewHealth20 = (TextView) findViewById(R.id.textViewHealth20);
        textViewHealth21 = (TextView) findViewById(R.id.textViewHealth21);


        textViewHealth12.setText(Html.fromHtml("<b>" + "•\tWithin 24 hours:" + " </b>" + " Your chance of a heart attack decreases."));
        textViewHealth13.setText(Html.fromHtml("<b>" + "•\tWithin 48 hours:" + " </b>" + " Your nerve endings start to regroup. Your ability to taste and smell improves."));
        textViewHealth14.setText(Html.fromHtml("<b>" + "•\tWithin 3 days:" + " </b>" + " Breathing is easier for you."));
        textViewHealth15.setText(Html.fromHtml("<b>" + "•\tWithin 2 to 3 months:" + " </b>" + " Your circulation improves. Walking becomes easier. Your lung capacity increases up to 30 percent."));
        textViewHealth16.setText(Html.fromHtml("<b>" + "•\tWithin 1 to 9 months:" + " </b>" + " Sinus congestion and shortness of breath decrease. Cilia that sweep debris from your lungs grow back, increasing your lungs’ ability to handle mucus, clean the lungs, and reduce infection. Your energy increases."));
        textViewHealth17.setText(Html.fromHtml("<b>" + "•\tWithin 1 year:" + " </b>" + " Your excess risk of coronary disease is half that of a person who smokes."));
        textViewHealth18.setText(Html.fromHtml("<b>" + "•\tWithin 2 years" + " </b>" + " Your heart attack risk drops to near normal."));
        textViewHealth19.setText(Html.fromHtml("<b>" + "•\tWithin 5 years" + " </b>" + " Lung cancer death rate for the average former pack-a-day smoker decreases by almost half. Your risk of having a stroke reduces. Your risk of developing mouth, throat, and esophageal cancer is half that of a smoker.."));
        textViewHealth20.setText(Html.fromHtml("<b>" + "•\tWithin 10 years" + " </b>" + " Lung cancer death rate is similar to that of a person who does not smoke. The precancerous cells are replaced."));
        textViewHealth21.setText(Html.fromHtml("<b>" + "•\tWithin 15 years" + " </b>" + " Your risk of coronary heart disease is the same as a person who has never smoked."));

        buttonFinances = (Button) findViewById(R.id.buttonFinances);
        buttonFinances.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonFinances:
                startActivity(new Intent(Health.this, Finances.class));
                break;
        }
    }
}
