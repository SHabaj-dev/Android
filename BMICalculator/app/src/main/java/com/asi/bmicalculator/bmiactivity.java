package com.asi.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class bmiactivity extends AppCompatActivity {

    android.widget.Button recalculate;

    TextView displaybmi, catogoryBmi, gender;
    Intent intent;
    ImageView imageView;
    String bmi;
    float intbmi;

    String height, weight;
    float intheight, intweight;
    RelativeLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiactivity);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"white\"></font>"));
        getSupportActionBar().setTitle("Result");

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#1E1D1D"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        intent = getIntent();

        displaybmi = findViewById(R.id.displaybmi);
        catogoryBmi = findViewById(R.id.category);
        gender = findViewById(R.id.genderDisplay);
        imageView = findViewById(R.id.iconCheck);
        recalculate = findViewById(R.id.btnReCalculate);
        background = findViewById(R.id.showDataLayout);


        height = intent.getStringExtra("height");
        weight = intent.getStringExtra("weight");

        intheight = Float.parseFloat(height);
        intweight = Float.parseFloat(weight);

        intheight /= 100;

        intbmi = intweight / (intheight * intheight);

        bmi = Float.toString(intbmi);

        if(intbmi < 16){
            catogoryBmi.setText("Severe Thinness");
            background.setBackgroundColor(Color.RED);
            imageView.setImageResource(R.drawable.wrong);
        }else if(intbmi < 16.9 && intbmi > 16){
            catogoryBmi.setText("Moderate Thinness");
            background.setBackgroundColor(Color.RED);
            imageView.setImageResource(R.drawable.warning);
        }else if(intbmi < 18.4 && intbmi > 17){
            catogoryBmi.setText("Mild Thinness");
            background.setBackgroundColor(Color.RED);
            imageView.setImageResource(R.drawable.warning);
        }else if(intbmi < 25 && intbmi > 18.4){
            catogoryBmi.setText("Normal");
//            background.setBackgroundColor(Color.GREEN);
            imageView.setImageResource(R.drawable.right);
        }else if(intbmi < 29.4 && intbmi > 25){
            catogoryBmi.setText("OverWeight");
            background.setBackgroundColor(Color.YELLOW);
            imageView.setImageResource(R.drawable.warning);
        }else{
            catogoryBmi.setText("Obese Class 1");
            background.setBackgroundColor(Color.RED);
            imageView.setImageResource(R.drawable.warning);
        }


        gender.setText(intent.getStringExtra("gender"));
        displaybmi.setText(bmi);






        recalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bmiactivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}