package com.asi.movieflex.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.asi.movieflex.Listeners.OnMovieClickListener;
import com.asi.movieflex.Models.SearchArrayObjects;
import com.asi.movieflex.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeViewHolder>{

    Context context;
    List<SearchArrayObjects> list;
    OnMovieClickListener movieClickListener;

    public HomeRecyclerAdapter(Context context, List<SearchArrayObjects> list, OnMovieClickListener movieClickListener) {
        this.context = context;
        this.list = list;
        this.movieClickListener = movieClickListener;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(context).inflate(R.layout.home_movies_list, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {

        holder.textViewMovies.setText(list.get(position).getTitle());
        holder.textViewMovies.setSelected(true);
        Picasso.get().load(list.get(position).getImage()).into(holder.imageViewPoster);

        holder.homeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieClickListener.onMovieClicked(list.get(position).getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class HomeViewHolder extends RecyclerView.ViewHolder{

    ImageView imageViewPoster;
    TextView textViewMovies;
    CardView homeContainer;

    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);
        imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
        textViewMovies = itemView.findViewById(R.id.textViewMovies);
        homeContainer = itemView.findViewById(R.id.homeContainer);


    }
}
