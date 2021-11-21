package com.asi.allchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText getPhoneNumber;
    android.widget.Button send_otpBtn;
    CountryCodePicker countryCodePicker;
    String counterCode;
    String phoneNumber;

    FirebaseAuth firebaseAuth;
    ProgressBar progressBarMain;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks callBacks;
    String codeSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryCodePicker = findViewById(R.id.countryCodePicker);
        send_otpBtn = findViewById(R.id.send_otpBtn);
        getPhoneNumber = findViewById(R.id.getPhoneNumber);
        progressBarMain = findViewById(R.id.progressbarMain);

        firebaseAuth = FirebaseAuth.getInstance();

        counterCode = countryCodePicker.getSelectedCountryCodeWithPlus();

        //If user Changes the default country code.
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                counterCode = countryCodePicker.getSelectedCountryCodeWithPlus();
            }
        });


        //Code to send the OTP to the Given Number.
        send_otpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = getPhoneNumber.getText().toString();

                if(number.isEmpty()){ //This is a check if the number is not empty. If empty Toast a message to user to input the Number.
                    Toast.makeText(getApplicationContext(), "Please Enter the Number.", Toast.LENGTH_SHORT).show();
                }else if(number.length() < 10){ //Check the length of the Phone Number.
                    Toast.makeText(getApplicationContext(), "Invalid Number", Toast.LENGTH_SHORT).show();
                }else{
                    progressBarMain.setVisibility(View.VISIBLE);

                    phoneNumber = counterCode + number;
                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                            .setPhoneNumber(phoneNumber)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(MainActivity.this)
                            .setCallbacks(callBacks)
                            .build();

                    PhoneAuthProvider.verifyPhoneNumber(options);

                }
            }
        });

        callBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(getApplicationContext(), "OTP sent Successfully", Toast.LENGTH_SHORT).show();
                progressBarMain.setVisibility(View.INVISIBLE);
                codeSent = s;
                Intent intent = new Intent(MainActivity.this, OTP_Verification.class);
                intent.putExtra("OTP", codeSent);
                startActivity(intent);
            }
        };
    }


    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent = new Intent(MainActivity.this, MainActivity_Chat.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}