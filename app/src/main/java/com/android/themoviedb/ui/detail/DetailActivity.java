package com.android.themoviedb.ui.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.android.themoviedb.R;
import com.google.gson.Gson;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Inject
    Gson gson;

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
