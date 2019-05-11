package com.android.themoviedb.ui.detail;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.themoviedb.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 Created by twin on Dec 04, 2016
Updated by twin on May 10, 2019
*/

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);


        long id = getIntent().getLongExtra("movie_id", -1);
        String movieData = getIntent().getStringExtra("movie_data");

        getSupportFragmentManager().beginTransaction().replace(R.id.content,
                DetailFragment.newInstance(id,movieData)).commit();

        setupToolbar();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() ==  null) {
            throw new IllegalStateException("Activity must implement toolbar.");
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
