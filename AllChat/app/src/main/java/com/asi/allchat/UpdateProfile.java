package com.asi.allchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateProfile extends AppCompatActivity {

    private EditText getNewUserName;
    private TextView updateProfile;
    private ImageView getNewUserImageInImageView;
    private ImageButton backBtnUpdateProfile;
    private androidx.appcompat.widget.Toolbar toolbarOfUpdateProfile;
    private android.widget.Button saveUpdatedProfile;
    private ProgressBar updateProfileProgressbar;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private String imageUriAccessToken;
    Intent intent;
    private String newUserName;
    private Uri imagePath;
    private static int PICK_IMAGE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);


        getNewUserName = findViewById(R.id.getNewUserName);
        getNewUserImageInImageView = findViewById(R.id.getNewUserImageInImageView);
        backBtnUpdateProfile = findViewById(R.id.backBtnOfUpdateProfile);
        saveUpdatedProfile = findViewById(R.id.saveNewInfoBtn);
        updateProfileProgressbar = findViewById(R.id.progressBarUpdateProfile);
        toolbarOfUpdateProfile = findViewById(R.id.updateProfileToolbar);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        intent = getIntent();

        setSupportActionBar(toolbarOfUpdateProfile);

        backBtnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getNewUserName.setText(intent.getStringExtra("userName"));

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        saveUpdatedProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newUserName = getNewUserName.getText().toString();

                if(newUserName.isEmpty()){
                    Toast.makeText(getApplicationContext(), "User Name is Empty", Toast.LENGTH_SHORT).show();
                }
                else if(imagePath != null){
                    updateProfileProgressbar.setVisibility(View.VISIBLE);
                    UserProfile userProfile = new UserProfile(newUserName, firebaseAuth.getUid());
                    databaseReference.setValue(userProfile);

                    updateImageToStorage();

                    Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                    updateProfileProgressbar.setVisibility(View.INVISIBLE);
                    Intent intent1 = new Intent(UpdateProfile.this, MainActivity_Chat.class);
                    startActivity(intent1);
                    finish();
                }
                else
                {
                    updateProfileProgressbar.setVisibility(View.VISIBLE);
                    UserProfile userProfile = new UserProfile(newUserName, firebaseAuth.getUid());
                    databaseReference.setValue(userProfile);
                    updateNameOnCloudFirebase();
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    updateProfileProgressbar.setVisibility(View.INVISIBLE);
                    Intent intent1 = new Intent(UpdateProfile.this, MainActivity_Chat.class);
                    startActivity(intent1);
                    finish();
                }
            }
        });

        getNewUserImageInImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent1, PICK_IMAGE);
            }
        });

        storageReference = firebaseStorage.getReference();

        storageReference.child("Images").child(firebaseAuth.getUid()).child("Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageUriAccessToken=uri.toString();
                Picasso.get().load(uri).into(getNewUserImageInImageView);
            }
        });

    }

    private void updateImageToStorage(){

        StorageReference imageReference = storageReference.child("Image").child(firebaseAuth.getUid()).child("Profile Picture");

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();

        UploadTask uploadTask = imageReference.putBytes(data);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageUriAccessToken = uri.toString();
                        Toast.makeText(getApplicationContext(), "Uri get Success", Toast.LENGTH_SHORT).show();
                        updateNameOnCloudFirebase();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Uri get Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                Toast.makeText(getApplicationContext(), "Image is Updated", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Image Not Update", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateNameOnCloudFirebase() {

        DocumentReference documentReference = firebaseFirestore.collection("User").document(firebaseAuth.getUid());
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", newUserName);
        userData.put("image", imageUriAccessToken);
        userData.put("uid", firebaseAuth.getUid());
        userData.put("status", "Online");


        documentReference.set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imagePath = data.getData();
            getNewUserImageInImageView.setImageURI(imagePath);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();
        DocumentReference documentReference = firebaseFirestore.collection("User").document(firebaseAuth.getUid());
        documentReference.update("status", "Offline").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Now User Is Offline", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        DocumentReference documentReference=firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        documentReference.update("status","Online").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Now User is Online",Toast.LENGTH_SHORT).show();
            }
        });
    }
}