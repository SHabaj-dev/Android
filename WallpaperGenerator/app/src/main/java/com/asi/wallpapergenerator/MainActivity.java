package com.asi.wallpapergenerator;

import static android.content.ContentValues.TAG;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton infoBtn, downloadBtn, refreshBtn, closeBtn;
    ImageView catImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        infoBtn = findViewById(R.id.infoBtn);
        downloadBtn = findViewById(R.id.downloadBtn);
        refreshBtn = findViewById(R.id.refreshBtn);
        closeBtn = findViewById(R.id.closeBtn);
        catImage = findViewById(R.id.catimage);

        getImage("https://api.thecatapi.com/v1/images/search");

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Refresh Btn is clicked", Toast.LENGTH_SHORT).show();
//                System.out.println("entring the ");

                getImage("https://api.thecatapi.com/v1/images/search");
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

    public void getImage(String url){
        RequestQueue queue = Volley.newRequestQueue(this);
        System.out.println("Inside the function");

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                System.out.println("after request");
//                System.out.println("onResponse: " + response.toString());
//                Log.d("Asif", "onResponse: " + response.toString());


                try {
                    JSONObject kittyData = response.getJSONObject(0);
                    System.out.println(kittyData.toString());
//                    Log.d(TAG, "onResponse: " + kittyData.toString());
                    String caturl = kittyData.getString("url");
                    Log.d(TAG, "onResponse: " + caturl);
                    Picasso.get().load(caturl).into(catImage);

                    downloadBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                      Toast.makeText(MainActivity.this, "Download Btn is Clicked", Toast.LENGTH_SHORT).show();
                            Uri catUri = Uri.parse(caturl);
                            Intent browser = new Intent(Intent.ACTION_VIEW, catUri);
                            startActivity(browser);
                        }
                    });

                    infoBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(MainActivity.this, "Info Btn is Clicked", Toast.LENGTH_SHORT).show();
                            try {
                                JSONArray breedInfo = kittyData.getJSONArray("breeds");
                                if(breedInfo.isNull(0)){
                                    Toast.makeText(MainActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();

                                }else{
                                    JSONObject breedsData = breedInfo.getJSONObject(0);
                                    Intent i = new Intent(getApplicationContext(), info.class);
                                    i.putExtra("name", breedsData.getString("name"));
                                    i.putExtra("origin" , breedsData.getString("origin"));
                                    i.putExtra("desc" , breedsData.getString("description"));
                                    i.putExtra("temp" , breedsData.getString("temperament"));
                                    i.putExtra("wikiUrl", breedsData.getString("wikipedia_url"));
                                    i.putExtra("moreLink", breedsData.getString("vcahospitals_url"));
                                    i.putExtra("imageUrl", caturl);
                                    startActivity(i);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(arrayRequest);
    }
}