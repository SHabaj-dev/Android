package com.asi.moviereviewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.asi.moviereviewer.Adapters.HomeRecyclerAdapter;
import com.asi.moviereviewer.Listeners.OnMovieClickListener;
import com.asi.moviereviewer.Listeners.OnSearchAPIListner;
import com.asi.moviereviewer.Models.SearchAPIResponse;

public class MainActivity extends AppCompatActivity implements OnMovieClickListener {

    SearchView searchView;
    RecyclerView recyclerViewHome;
    HomeRecyclerAdapter homeRecyclerAdapter;
    RequestManager requestManager;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchView);
        recyclerViewHome = findViewById(R.id.recyclerViewHome);

        requestManager = new RequestManager(this);
        progressDialog = new ProgressDialog(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                progressDialog.setTitle("Searching, Please Wait...");
                progressDialog.show();
                requestManager.searchMovies(listner, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private final OnSearchAPIListner listner = new OnSearchAPIListner() {
        @Override
        public void onResponse(SearchAPIResponse response) {
            progressDialog.dismiss();
            if(response == null){
                Toast.makeText(MainActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
            }

            showResult(response);
            
        }

        @Override
        public void onError(String message) {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, "An Error Occurred !!", Toast.LENGTH_SHORT).show();
        }
    };

    private void showResult(SearchAPIResponse response) {
        recyclerViewHome.setHasFixedSize(true);
        recyclerViewHome.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        homeRecyclerAdapter = new HomeRecyclerAdapter(this, response.getTitles(), this);
        recyclerViewHome.setAdapter(homeRecyclerAdapter);
    }

    @Override
    public void onMovieClicked(String id) {
//        Toast.makeText(MainActivity.this, id, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, DetailsActivity.class)
            .putExtra("movieId", id));

    }
}