package com.asi.allchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SetProfile extends AppCompatActivity {

    private CardView getUserImage;
    private ImageView userImageInImageView;
    private static int Pick_Image = 123;
    private Uri imagePath;

    private EditText getUserName;

    private android.widget.Button saveProfileBtn;


    private FirebaseAuth firebaseAuth;
    private String name;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private String imageUriAccessToken;

    private FirebaseFirestore firebaseFirestore;

    ProgressBar progressBarSetProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getUserName = findViewById(R.id.getUserName);
        getUserImage = findViewById(R.id.getUserImage);
        userImageInImageView = findViewById(R.id.getUserImageInImageView);
        saveProfileBtn = findViewById(R.id.saveProfileBtn);
        progressBarSetProfile = findViewById(R.id.progressBarSetProfile);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        getUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, Pick_Image);
            }
        });

        saveProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name  = getUserName.getText().toString();

                if(name.isEmpty()){
                    Toast.makeText(getApplicationContext(), "User Name is Empty.", Toast.LENGTH_SHORT).show();
                }else if(imagePath == null){
                    Toast.makeText(getApplicationContext(), "Please Select Profile Picture.", Toast.LENGTH_SHORT).show();
                }else{

                    progressBarSetProfile.setVisibility(View.VISIBLE);

                    sendDataForNewUser();
                    progressBarSetProfile.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(SetProfile.this, MainActivity_Chat.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == Pick_Image && resultCode == RESULT_OK){
            imagePath = data.getData();
            userImageInImageView.setImageURI(imagePath);
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void sendDataForNewUser(){

        sendDataToRealTimeDatabase();

    }

    private void sendDataToRealTimeDatabase(){

        name = getUserName.getText().toString().trim();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String userUID = firebaseAuth.getUid();
        DatabaseReference databaseReference = firebaseDatabase.getReference(userUID);

        UserProfile userProfile = new UserProfile(name, userUID);
        databaseReference.setValue(userProfile);
        Toast.makeText(getApplicationContext(), "Profile Created Successfully.", Toast.LENGTH_SHORT).show();
        sendImageToStorage();

    }

    private void sendImageToStorage(){

        StorageReference imageReference = storageReference.child("Image").child(firebaseAuth.getUid()).child("Profile Picture");

        //Image Compression Technique.

        Bitmap bitmap =  null;

        try{
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
        byte[] imageData = byteArrayOutputStream.toByteArray();

        UploadTask uploadTask = imageReference.putBytes(imageData);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageUriAccessToken = uri.toString();
                        Toast.makeText(getApplicationContext(), "Uri Successful", Toast.LENGTH_SHORT).show();
                        sendDAtaToCloudFireStore();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Uri Failed", Toast.LENGTH_SHORT).show();
                    }
                });

                Toast.makeText(getApplicationContext(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Image Upload Failed", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void sendDAtaToCloudFireStore() {

        DocumentReference documentReference = firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        Map<String , Object> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("image", imageUriAccessToken);
        userData.put("uid",firebaseAuth.getUid());
        userData.put("status", "Online");

        documentReference.set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Data on Fire Cloud sent successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }
}