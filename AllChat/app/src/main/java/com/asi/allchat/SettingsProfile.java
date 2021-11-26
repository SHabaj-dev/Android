package com.asi.allchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class SettingsProfile extends AppCompatActivity {

    EditText viewUserName;
    TextView updateProfile;
    ImageView viewUserImageInImageView;
    ImageButton backBtnMyProfile;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    private String imageUriAccessToken;

    androidx.appcompat.widget.Toolbar toolbarOfMyProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_profile);

        viewUserName = findViewById(R.id.viewUserName);
        updateProfile = findViewById(R.id.updateProfile);
        viewUserImageInImageView = findViewById(R.id.viewUserImageInImageView);
        backBtnMyProfile = findViewById(R.id.backBtnOfProfileSettings);
        toolbarOfMyProfile = findViewById(R.id.myProfileToolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        setSupportActionBar(toolbarOfMyProfile);

        backBtnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        storageReference = firebaseStorage.getReference();
        storageReference.child("Image").child(firebaseAuth.getUid()).child("Profile Picture")
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageUriAccessToken = uri.toString();
                Picasso.get().load(uri).into(viewUserImageInImageView);
            }
        });

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                viewUserName.setText(userProfile.getUserName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}