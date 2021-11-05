package com.asi.bcanotes;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String[] item = {"Item1", "Item2", "Item3"};
    ListView listView;
    ArrayList<DataModel> dataModelArrayList;
    FirebaseFirestore db;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        textView = findViewById(R.id.text);

        dataModelArrayList = new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        System.out.println("Enterting the function");
//        loadDataInListView();
        System.out.println("Exit");


        Map<String, String> data = new HashMap<>();
        data.put(
          "name","semester 2"
        );

        DocumentReference newCityRef = db.collection("semester").document();

// Later...
        newCityRef.set(data);

        CollectionReference a = db.collection("semester");

                a.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "asif Error getting documents: ", task.getException());
                        }
                    }
                });

//        ArrayAdapter<String> arr = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, item);
//        listView.setAdapter(arr);
    }

    private void loadDataInListView() {
        db.collection("semester")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("asif", document.getId() + " => " + document.getData());
                                System.out.println("Asif" + document.getId() + " => "  + document.getData());
                            }
                        } else {
                            System.out.println("MaaKiChoo");
                        }
                    }
                });
        System.out.println(dataModelArrayList);
    }
}