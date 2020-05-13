package com.example.popularmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener {
    private RecyclerView mRecyclerView;
    private TextView mErrorMsg;
    private MoviesAdapter moviesAdapter;
    private ProgressBar mProgressbar;
    private Movie[] movies;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moviesAdapter = new MoviesAdapter(getApplicationContext(),this, MainActivity.this);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        mErrorMsg = findViewById(R.id.tv_error);
        mProgressbar = findViewById(R.id.progressBar);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(v.getId());
            }
        });
        mRecyclerView.setAdapter(moviesAdapter);
        loadMovieData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onListItemClick(int item) {
        Intent intent = new Intent(this, Detail_Activity.class);
        intent.putExtra("position", item);
        intent.putExtra("Title", movies[item].getTitle());
        startActivity(intent);
        if (mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(this, item, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public class FetchMovieTask extends AsyncTask<Void, Movie[], Movie[]>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(Void... voids) {
            Movie[] movies1 = new Movie[10];
            for (int i=600;i<610;i++){
                try {
                    movies1[i-600] = movieJsonReader.getMovieDetail(getBaseContext(),NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrl(String.valueOf(i))));
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
            return movies1;
        }

        @Override
        protected void onPostExecute(Movie[] movie) {
            movies = movie;
            if (movies != null){
                showMovieData();
                moviesAdapter.setMovieData(movies);
            }
            else {
                hideMovieData();
            }
        }
    }
    private void loadMovieData(){
        new FetchMovieTask().execute();
    }
    private void showMovieData(){
        mProgressbar.setVisibility(View.INVISIBLE);
        mErrorMsg.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
    private void hideMovieData(){
        mErrorMsg.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mProgressbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                    mRecyclerView.setAdapter(null);
                    loadMovieData();
                    return true;
            case R.id.action_sort_popular:
                Arrays.sort(movies, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie o1, Movie o2) {
                        if (o1.getmVote_rating() > o2.getmVote_rating()){
                            return 1;
                        } else if (o1.getmVote_rating() < o2.getmVote_rating()){
                            return -1;
                        }
                        return 0;
                    }
                });
                moviesAdapter.setMovieData(movies);
                System.out.println(movies);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
