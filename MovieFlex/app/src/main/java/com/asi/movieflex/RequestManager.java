package com.asi.movieflex;

import android.content.Context;
import android.widget.Toast;


import com.asi.movieflex.Listeners.OnDetailsAPIListener;
import com.asi.movieflex.Listeners.OnSearchAPIListner;
import com.asi.movieflex.Models.DetailAPIResponse;
import com.asi.movieflex.Models.SearchAPIResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://imdb-internet-movie-database-unofficial.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }


    public void searchMovies(OnSearchAPIListner listener, String movie_name){
        getMovies getMovies = retrofit.create(RequestManager.getMovies.class);
        Call<SearchAPIResponse> call = getMovies.callMovies(movie_name);


        call.enqueue(new Callback<SearchAPIResponse>() {
            @Override
            public void onResponse(Call<SearchAPIResponse> call, Response<SearchAPIResponse> response) {
                if( !response.isSuccessful()){
                    Toast.makeText(context, "Couldn't Load Data.", Toast.LENGTH_SHORT).show();
                    return;
                }

                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<SearchAPIResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void searchMovieDetails(OnDetailsAPIListener listener, String movie_id){
        getMovieDetails getMovieDetails = retrofit.create(RequestManager.getMovieDetails.class);
        Call<DetailAPIResponse> call = getMovieDetails.callMovieDetails(movie_id);


        call.enqueue(new Callback<DetailAPIResponse>() {
            @Override
            public void onResponse(Call<DetailAPIResponse> call, Response<DetailAPIResponse> response) {
                if( !response.isSuccessful()){
                    Toast.makeText(context, "Couldn't Load Data.", Toast.LENGTH_SHORT).show();
                    return;
                }

                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<DetailAPIResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public interface getMovies{

        @Headers({
                "Accept: application/json",
                "x-rapidapi-host: imdb-internet-movie-database-unofficial.p.rapidapi.com",
                "x-rapidapi-key: bfcc4d261bmsh54974d00c3093eap19aa68jsndb73c41fff51"
        })
        @GET("search/{movie_name}")
        Call<SearchAPIResponse> callMovies(
                @Path("movie_name") String movie_name
        );

    }

    public interface getMovieDetails{

        @Headers({
                "Accept: application/json",
                "x-rapidapi-host: imdb-internet-movie-database-unofficial.p.rapidapi.com",
                "x-rapidapi-key: bfcc4d261bmsh54974d00c3093eap19aa68jsndb73c41fff51"
        })
        @GET("film/{movie_id}")
        Call<DetailAPIResponse> callMovieDetails(
                @Path("movie_id") String movie_id
        );

    }
}
