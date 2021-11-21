package com.asi.allchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OTP_Verification extends AppCompatActivity {

    TextView changeNumber;
    EditText getOtp;
    android.widget.Button verifyOtpBtn;
    ProgressBar progressBarOTP_verification;

    String entered_otp;

    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        changeNumber = findViewById(R.id.changeNumber);
        getOtp = findViewById(R.id.getOtp);
        progressBarOTP_verification = findViewById(R.id.progressBarOTP_Verification);
        verifyOtpBtn = findViewById(R.id.verifyOTPBtn);

        firebaseAuth = FirebaseAuth.getInstance();

        changeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OTP_Verification.this, MainActivity.class);
                startActivity(intent);
            }
        });

        verifyOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entered_otp = getOtp.getText().toString();

                if(entered_otp. isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter the OTP", Toast.LENGTH_SHORT).show();
                }else{
                    progressBarOTP_verification.setVisibility(View.VISIBLE);
                    String codeReceived = getIntent().getStringExtra("OTP");

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeReceived, entered_otp);
                    signInWithAuthCredential(credential);
                }
            }
        });
    }

    private void signInWithAuthCredential(PhoneAuthCredential credential){

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    progressBarOTP_verification.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OTP_Verification.this, SetProfile.class);
                    startActivity(intent);
                    finish();
            }else{
                    if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        progressBarOTP_verification.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Login Failed! Please Try Again.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}