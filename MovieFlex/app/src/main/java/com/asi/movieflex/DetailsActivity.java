package com.asi.movieflex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asi.movieflex.Adapters.CastRecyclerAdapter;
import com.asi.movieflex.Listeners.OnDetailsAPIListener;
import com.asi.movieflex.Models.DetailAPIResponse;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    TextView textViewMovieTitle, textViewMovieReleased, textViewMovieRunTime, textViewMovieRating,textViewMovieVotes, textViewMoviePlot;
    ImageView imageViewMoviePoster;
    RecyclerView movieCast;
    CastRecyclerAdapter castRecyclerAdapter;
    RequestManager requestManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        textViewMovieTitle = findViewById(R.id.textViewMovieTitle);
        textViewMovieReleased = findViewById(R.id.textViewMovieReleased);
        textViewMovieRunTime = findViewById(R.id.textViewMovieRunTime);
        textViewMovieRating = findViewById(R.id.textViewMovieRating);
        textViewMovieVotes = findViewById(R.id.textViewMovieVotes);
        textViewMoviePlot = findViewById(R.id.textViewMoviePlot);

        imageViewMoviePoster = findViewById(R.id.imageViewMoviePoster);
        movieCast = findViewById(R.id.movieCast);



        requestManager = new RequestManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.show();

        String movie_id = getIntent().getStringExtra("movieId");

        requestManager.searchMovieDetails(listener, movie_id);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private OnDetailsAPIListener listener = new OnDetailsAPIListener() {
        @Override
        public void onResponse(DetailAPIResponse response) {

            progressDialog.dismiss();
            if(response.equals(null)){
                Toast.makeText(DetailsActivity.this, "Error in Finding Data!!", Toast.LENGTH_SHORT).show();
                return;
            }

            showResults(response);

        }

        @Override
        public void onError(String message) {
            progressDialog.dismiss();
            Toast.makeText(DetailsActivity.this, "Error in Finding DAta", Toast.LENGTH_SHORT).show();

        }
    };

    private void showResults(DetailAPIResponse response) {
        textViewMovieTitle.setText(response.getTitle());
        textViewMovieReleased.setText("Releasing Year: " + response.getYear());
        textViewMovieRunTime.setText("Duration: " + response.getLength());
        textViewMovieRating.setText("Rating: " + response.getRating());
        textViewMovieVotes.setText(response.getRating_votes() + " Votes");
        textViewMoviePlot.setText(response.getPlot());

        try {
            Picasso.get().load(response.getPoster()).into(imageViewMoviePoster);
        }catch (Exception e){
            e.printStackTrace();
        }

        movieCast.setHasFixedSize(true);
        movieCast.setLayoutManager(new GridLayoutManager(this, 1));
        castRecyclerAdapter = new CastRecyclerAdapter(this, response.getCast());
        movieCast.setAdapter(castRecyclerAdapter);

    }
}