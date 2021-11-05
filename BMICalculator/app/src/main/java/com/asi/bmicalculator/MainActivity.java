package com.asi.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    android.widget.Button calculate;
    TextView currentWeight, currentAge, currentHeight;
    SeekBar seekBar;
    RelativeLayout male, female;
    ImageView incWeight, decWeight, incAge, decAge;

    int intWeight = 55, intAge = 22;
    int currentProgress;
    String mintProgress = "170";
    String typeofUser = "0";
    String weight2 = "55";
    String age2 = "22";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        calculate = findViewById(R.id.btnCalculate);
        currentAge = findViewById(R.id.currentAge);
        currentHeight = findViewById(R.id.currentHeight);
        currentWeight = findViewById(R.id.currentWeight);
        incAge = findViewById(R.id.ageIncrease);
        decAge = findViewById(R.id.ageDecrease);
        incWeight = findViewById(R.id.weightIncrease);
        decWeight = findViewById(R.id.weightDecrease);

        seekBar = findViewById(R.id.seekBarForHeight);

        male = findViewById(R.id.male);
        female = findViewById(R.id.female);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.malefemalefocus));
                female.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.malefemalenotfocus));
                typeofUser = "Male";
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                female.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.malefemalefocus));
                male.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.malefemalenotfocus));
                typeofUser = "Female";
            }
        });


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, bmiactivity.class);
                startActivity(intent);
            }
        });


        seekBar.setMax(300);
        seekBar.setProgress(170);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentProgress = progress;
                mintProgress = String.valueOf(currentProgress);
                currentHeight.setText(mintProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        incAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intAge = intAge + 1;
                age2 = String.valueOf(intAge);
                currentAge.setText(age2);
            }
        });

        decAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intAge = intAge - 1;
                age2 = String.valueOf(intAge);
                currentAge.setText(age2);

            }
        });

        incWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intWeight = intWeight + 1;
                weight2 = String.valueOf(intWeight);
                currentWeight.setText(weight2);
            }
        });

        decWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intWeight -= 1;
                weight2 = String.valueOf(intWeight);
                currentWeight.setText(weight2);
            }
        });




        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(typeofUser.equals("0")){
                    Toast.makeText(getApplicationContext(), "Please Select Your Gender First", Toast.LENGTH_SHORT).show();
                }else if(mintProgress.equals("0")){
                    Toast.makeText(getApplicationContext(), "Please Select Your Age First", Toast.LENGTH_SHORT).show();
                }else if(intAge <= 0){
                    Toast.makeText(getApplicationContext(), "Age Is Incorrect", Toast.LENGTH_SHORT).show();
                }else if(intWeight <= 0){
                    Toast.makeText(getApplicationContext(), "Weight Is Incorrect", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(MainActivity.this, bmiactivity.class);
                    intent.putExtra("gender", typeofUser);
                    intent.putExtra("height", mintProgress);
                    intent.putExtra("weight", weight2);
                    intent.putExtra("age", age2);


                    startActivity(intent);
                }
            }
        });

    }
}