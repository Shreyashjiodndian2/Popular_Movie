package com.example.popularmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Detail_Activity extends AppCompatActivity {
    private ImageView mImageView;
    private TextView mTitleTv;
    private TextView mPlotTv;
    private TextView mVoteTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_);
        mImageView = (ImageView) findViewById(R.id.poster_Iv);
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mPlotTv = (TextView) findViewById(R.id.plot_tv);
        mVoteTv = (TextView) findViewById(R.id.vote_tv);
        Intent intent = getIntent();
        Glide.with(this).load(NetworkUtils.buildUrl_image(intent.getStringExtra("img"))).into(mImageView);
        mTitleTv.setText(intent.getStringExtra("title"));
        mPlotTv.setText(intent.getStringExtra("plot"));
        mVoteTv.setText(String.valueOf(intent.getFloatExtra("vote",5)));
    }
}
