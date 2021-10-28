package com.asi.calculatoradvance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    float num1 = 0.0f;
    float num2 = 0.0f;

    Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0,
    buttonDel, buttonMul,buttonSum, buttonSub, buttonDiv, buttonMod, buttonDot, buttonEq;

    TextView edit;
    boolean Add, Sub, Mul, Div, Rem, Decimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        buttonDel = findViewById(R.id.buttonDel);
        buttonDiv = findViewById(R.id.buttondiv);
        buttonSub = findViewById(R.id.buttonsub);
        buttonSum = findViewById(R.id.buttonadd);
        buttonMod = findViewById(R.id.buttonmod);
        buttonMul = findViewById(R.id.buttonmul);
        buttonDot = findViewById(R.id.buttondot);
        buttonEq = findViewById(R.id.buttonEq);

        edit = (TextView) findViewById(R.id.display);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setText(edit.getText() + "1");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setText(edit.getText() + "2");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setText(edit.getText() + "3");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setText(edit.getText() + "4");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setText(edit.getText() + "5");
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setText(edit.getText() + "6");
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setText(edit.getText() + "7");
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setText(edit.getText() + "8");
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setText(edit.getText() + "9");
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setText(edit.getText() + "0");
            }
        });

        buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setText(edit.getText() + ".");
            }
        });

        buttonSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit.getText().length() != 0){
                    num1 = Float.parseFloat(edit.getText() + "");
                    Add = true;
                    Decimal = false;
                    edit.setText(null);
                }
            }
        });

        buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit.getText().length() != 0){
                    num1 = Float.parseFloat(edit.getText() + "");
                    Sub = true;
                    Decimal = false;
                    edit.setText(null);
                }
            }
        });

        buttonMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit.getText().length() != 0){
                    num1 = Float.parseFloat(edit.getText() + "");
                    Mul = true;
                    Decimal = false;
                    edit.setText(null);
                }
            }
        });

        buttonDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit.getText().length() != 0) {
                    num1 = Float.parseFloat(edit.getText() + "");
                    Div = true;
                    Decimal = false;
                    edit.setText(null);
                }
            }
        });

        buttonMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit.getText().length() != 0) {
                    num1 = Float.parseFloat(edit.getText() + "");
                    Rem = true;
                    Decimal = false;
                    edit.setText(null);
                }
            }
        });


        buttonEq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Add){
                    num2 = Float.parseFloat(edit.getText() + "");
                    edit.setText(num1 + num2 + "");
                    Add = false;
                }
                if(Sub){
                    num2 = Float.parseFloat(edit.getText() + "");
                    edit.setText(num1 - num2 + "");
                    Sub = false;
                }

                if(Mul){
                    num2 = Float.parseFloat(edit.getText() + "");
                    edit.setText(num1 * num2 + "");
                    Mul = false;
                }

                if(Div){
                    num2 = Float.parseFloat(edit.getText() + "");
                    edit.setText(num1 / num2 + "");
                    Div = false;
                }

                if(Rem){
                    num2 = Float.parseFloat(edit.getText() + "");
                    edit.setText(num1 % num2 + "");
                    Rem = false;
                }

            }
        });

        buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Decimal){
                    edit.setText(edit.getText() + ".");
                    Decimal = true;
                }
            }
        });

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setText("");
                num1 = 0.0f;
                num2 = 0.0f;
            }
        });
    }
}