package com.android.themoviedb.ui.list;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.android.themoviedb.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 Created by twin on Dec 04, 2016
Updated by twin on May 10, 2019
*/

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static final String FRAGMENT_MOVIES_TAG = "FragmentMoviesTag";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner)Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setToolbar();
        setSpinner();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, MainFragment.newInstance(),
                            FRAGMENT_MOVIES_TAG).commit();
        }
    }

    private void setSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(toolbar.getContext(),
                        R.array.sort,
                        R.layout.support_simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Activity must implement toolbar");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedSort = getSortByPosition(position);

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FRAGMENT_MOVIES_TAG);

        ((SortableFragment) fragment).sortMovie(selectedSort);
    }

    private String getSortByPosition(int position) {
        switch (position) {
            case 0:
                return "popular";
            case 1:
                return "top_rated";
            default:
                return "popular";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
