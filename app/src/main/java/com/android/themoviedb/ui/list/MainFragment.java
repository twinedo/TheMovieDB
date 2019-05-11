package com.android.themoviedb.ui.list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.themoviedb.R;
import com.android.themoviedb.api.service.MovieService;
import com.android.themoviedb.api.service.RestApi;
import com.android.themoviedb.api.service.ServiceConfig;
import com.android.themoviedb.model.Movie;
import com.android.themoviedb.model.MovieResponse;
import com.android.themoviedb.ui.detail.DetailActivity;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 Created by twin on Dec 04, 2016
Updated by twin on May 10, 2019
*/

public class MainFragment extends Fragment implements SortableFragment{

    private final String TAG = MainFragment.class.getSimpleName();

    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.loading)ProgressBar loadingView;

    private static final String DEFAULT_SORT = "popular";
    public static final String KEY_SORT = "key_sort";

    private MoviesAdapter adapter;
    private String sort;

    public static Fragment newInstance(){
        return new MainFragment();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                getActivity(),
                2,
                RecyclerView.VERTICAL,
                false
        );

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new MoviesAdapter();
        adapter.setOnClickListener(new MoviesAdapter
                .OnClickListener() {
            @Override
            public void onItemClickListener(long id,
                                            Movie movie) {
                openMovieDetail(id, movie);
            }
        });

        recyclerView.setAdapter(adapter);

        if (savedInstanceState == null){
            sort = DEFAULT_SORT;
        }else {
            sort = savedInstanceState.getString(KEY_SORT);
        }
        getListMovie(sort);

    }

    private void openMovieDetail(long id, Movie movie) {
        String movieJson = new Gson().toJson(movie);

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("movie_id", id);
        intent.putExtra("movie_data", movieJson);
        getActivity().startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_SORT, sort);
        super.onSaveInstanceState(outState);
    }

    public void getListMovie(String sort) {
        loadingView.setVisibility(View.VISIBLE);

        MovieService apiService =
                RestApi.getClient().create(MovieService.class);

        Call<MovieResponse> call = apiService.listOfMovie(sort, ServiceConfig.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                loadingView.setVisibility(View.GONE);
                Log.e(TAG, "Status Code = " + response.code());
                Log.e(TAG, "Data received: " + new Gson().toJson(response.body().results));

                if (response.code() == 200 && response.isSuccessful()) {
                    adapter.setMoviesData(response.body().results);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Symptopm Tidak Ada");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog.show();
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                loadingView.setVisibility(View.GONE);

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Kesalahan Jaringan " + t.getMessage());
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();
            }
        });
    }


    @Override
    public void sortMovie(String sort) {
        this.sort = sort;
        getListMovie(sort);
    }


}
