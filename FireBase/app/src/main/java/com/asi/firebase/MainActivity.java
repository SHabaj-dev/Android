package com.asi.firebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<DataModel> userArrrayList;
    Myadapter myadapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    DataModel dataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        userArrrayList = new ArrayList<DataModel>();
        myadapter = new Myadapter(MainActivity.this, userArrrayList);

        EventChangeListner();

        System.out.println("This size is" + userArrrayList + "..................................");
    }

    private void EventChangeListner() {

        db.collection("semester").orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {


                        if(e != null){

                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }

                            Log.e("FireStoreError", e.getMessage());
                            return;
                        }

                        for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){

                            if(dc.getType() == DocumentChange.Type.ADDED){
                                System.out.println("We are Here");
                                userArrrayList.add(dc.getDocument().toObject(DataModel.class));
                                System.out.println(dc.getDocument().toObject(DataModel.class));
                            }




                            myadapter.notifyDataSetChanged();
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }
                    }
                });



    }
}