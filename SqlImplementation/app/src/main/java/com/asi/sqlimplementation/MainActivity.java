package com.asi.sqlimplementation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText et_Name, et_Age;
    ListView listView;
    Button buttonAll, buttonAdd;
    Switch isActiveSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_Name = findViewById(R.id.tvName);
        et_Age = findViewById(R.id.tvAge);
        listView = findViewById(R.id.listViewData);
        buttonAdd = findViewById(R.id.btnAdd);
        buttonAll = findViewById(R.id.addAllBtn);
        isActiveSwitch = findViewById(R.id.switchbtn);
        
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerModel customerModel;
                        try{

                            customerModel = new CustomerModel(-1, et_Name.getText().toString(), Integer.parseInt(et_Age.getText().toString()), isActiveSwitch.isChecked());
                            Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();

                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Error In Creating", Toast.LENGTH_SHORT).show();
                            customerModel = new CustomerModel(-1, "Error", 0 , false);
                        }

                        dataBaseHelper dataBaseHelper = new dataBaseHelper(MainActivity.this);
                        boolean success = dataBaseHelper.addOne(customerModel);
                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();

            }
        });
        
        buttonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseHelper dataBaseHelper = new dataBaseHelper(MainActivity.this);
                List<CustomerModel> dataAll = dataBaseHelper.getData();

//                Toast.makeText(getApplicationContext(), dataAll.toString(), Toast.LENGTH_SHORT).show();

                ArrayAdapter customAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataAll);
                listView.setAdapter(customAdapter);
            }
        });
    }
}