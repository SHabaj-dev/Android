package com.asi.covidtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{

    CountryCodePicker countryCodePicker;
    TextView mtodaytotal, mtotal, mactive, mtodayactive, mrecovered, mtodayrecovered, mdeaths, mtodaydeaths;

    String country;

    TextView mfilter;

    Spinner spinner;
    String[] types = {"cases", "deaths", "recoverd", "active"};

    private List<ModelClass> modelClassList;
    private List<ModelClass> modelClassList2;

    PieChart mpiechart;
    private RecyclerView recyclerView;
    com.asi.covidtracker.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().hide();
        countryCodePicker = findViewById(R.id.cpp);
        mactive = findViewById(R.id.activeCase);
        mtotal = findViewById(R.id.totalCase);
        mtodayactive = findViewById(R.id.todayActive);
        mtodaytotal = findViewById(R.id.todayTotal);
        mrecovered = findViewById(R.id.recoveredCase);
        mtodayrecovered = findViewById(R.id.todayRecovered);
        mdeaths = findViewById(R.id.totalDeath);
        mtodaydeaths = findViewById(R.id.todayDeath);
        mfilter = findViewById(R.id.filter);
        spinner = findViewById(R.id.spinner);
        mpiechart = findViewById(R.id.piechart);
        recyclerView = findViewById(R.id.recyclerView);
        modelClassList = new ArrayList<>();
        modelClassList2 = new ArrayList<>();

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

        ApiUtilities.getApiInterface().getcountrydata().enqueue(new Callback<List<ModelClass>>() {
            @Override
            public void onResponse(Call<List<ModelClass>> call, Response<List<ModelClass>> response) {
                modelClassList2.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ModelClass>> call, Throwable t) {

            }
        });

        adapter = new Adapter(getApplicationContext(), modelClassList2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);




        countryCodePicker.setAutoDetectedCountry(true);
        country = countryCodePicker.getSelectedCountryName();
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                country = countryCodePicker.getSelectedCountryName();
                fetchdata();
            }
        });

        fetchdata();

    }

    private void fetchdata() {

        ApiUtilities.getApiInterface().getcountrydata().enqueue(new Callback<List<ModelClass>>() {
            @Override
            public void onResponse(Call<List<ModelClass>> call, Response<List<ModelClass>> response) {
                modelClassList.addAll(response.body());

                for(int i = 0; i < modelClassList.size(); i++){

                    if(modelClassList.get(i).getCountry().equals(country)){
                        mactive.setText(modelClassList.get(i).getActive());
                        mtodaydeaths.setText(modelClassList.get(i).getTodayDeaths());
                        mtodaydeaths.setText(modelClassList.get(i).getDeaths());
                        mtotal.setText(modelClassList.get(i).getCases());
                        mrecovered.setText(modelClassList.get(i).getRecovered());
                        mtodayrecovered.setText(modelClassList.get(i).getTodayRecovered());
                        mtodaytotal.setText(modelClassList.get(i).getTodayCases());


                        int active, recovered, deaths, total;

                        active = Integer.parseInt(modelClassList.get(i).getActive());
                        recovered = Integer.parseInt(modelClassList.get(i).getRecovered());
                        deaths = Integer.parseInt(modelClassList.get(i).getDeaths());
                        total = Integer.parseInt(modelClassList.get(i).getTodayCases());

                        update(active, recovered, deaths, total);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<ModelClass>> call, Throwable t) {

            }
        });

    }

    private void update(int active, int recovered, int deaths, int total) {

        mpiechart.clearChart();

        mpiechart.addPieSlice(new PieModel("confirm", total, Color.parseColor("#FFB701")));
        mpiechart.addPieSlice(new PieModel("Active", total, Color.parseColor("#FF4CAF50")));
        mpiechart.addPieSlice(new PieModel("recovered", total, Color.parseColor("#38ACCD")));
        mpiechart.addPieSlice(new PieModel("death", total, Color.parseColor("#F55C47")));
        mpiechart.startAnimation();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        String item = types[position];
        mfilter.setText(item);
        adapter.filter(item);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}