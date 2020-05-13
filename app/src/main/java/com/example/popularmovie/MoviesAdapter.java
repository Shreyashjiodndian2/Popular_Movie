package com.example.popularmovie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieAdapterViewHolder> {
    private Movie[] movies;
    private ListItemClickListener listener;
    private Activity activity;
    private Context imgContext;
    public interface ListItemClickListener {
        void onListItemClick(int item);
    }
    MoviesAdapter(Context context, ListItemClickListener listItemClickListener, Activity activity1){
        this.imgContext = context;
        this.activity = activity1;
        this.listener = listItemClickListener;
    }
    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int resId = R.layout.movie;
        View view = layoutInflater.inflate(resId,parent,false);
        MovieAdapterViewHolder movieAdapterViewHolder = new MovieAdapterViewHolder(view);
        return movieAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAdapterViewHolder holder, final int position) {
        holder.bind(position);
        holder.mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(imgContext, Detail_Activity.class);
                intent.putExtra("title", movies[position].getTitle());
                intent.putExtra("plot", movies[position].getmPlot());
                intent.putExtra("img", movies[position].getmImgUrl());
                intent.putExtra("vote", movies[position].getmVote_rating());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (movies != null) return movies.length;
        return 0;
    }
    class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;
        private ProgressBar mProgressbar_movie;
        MovieAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = (ImageView)itemView.findViewById(R.id.movie_data);
            mProgressbar_movie = (ProgressBar) itemView.findViewById(R.id.pb_movie);
        }
        void bind(int listIndex) {
            mImageView.setVisibility(View.VISIBLE);
            mProgressbar_movie.setVisibility(View.INVISIBLE);
            if (movies[listIndex] != null) {
                Glide.with(imgContext).load(NetworkUtils.buildUrl_image(movies[listIndex].getmImgUrl())).diskCacheStrategy(DiskCacheStrategy.ALL).into(mImageView);
            }
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onListItemClick(pos);
        }
    }
    void setMovieData(Movie[] movieData){
        movies = movieData;
        notifyDataSetChanged();
    }


}
