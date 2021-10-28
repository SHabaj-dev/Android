package com.asi.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText e1, e2;
    TextView tv;
    int num1, num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean isnumber(){
        e1 = (EditText) findViewById(R.id.num1);
        e2 = (EditText) findViewById(R.id.num2);
        tv = findViewById(R.id.result);

        String s1 = e1.getText().toString();
        String s2 = e2.getText().toString();

        if(s1.equals("null") && s2.equals("null") || s1.equals("") && s2.equals("")){
            String txt = "Please Enter a Number";
            tv.setText(txt);
            return false;
        }
        else{
            num1 = Integer.parseInt(s1);
            num2 = Integer.parseInt(s2);

        }
        return true;
    }

    public void doSum(View v){
        if(isnumber()){
            int sum = num1 + num2;
            tv.setText(Integer.toString(sum));
        }
    }

    public void doSub(View v){
        if(isnumber()){
            int sub = num1 - num2;
            tv.setText(Integer.toString(sub));

        }
    }

    public void doMul(View v){
        if(isnumber()){
            int mul = num1 * num2;
            tv.setText(Integer.toString(mul));

        }
    }

    public void doDiv(View v){
        if(isnumber()){
            double div = num1 / num2;
            tv.setText(Double.toString(div));

        }
    }

    public void doMod(View v){
        if(isnumber()){
            int mod = num1 % num2;
            tv.setText(Integer.toString(mod));

        }
    }

    public void doPow(View v){
        if(isnumber()){
            double power = Math.pow(num1, num2);
            tv.setText(Double.toString(power));

        }
    }

}