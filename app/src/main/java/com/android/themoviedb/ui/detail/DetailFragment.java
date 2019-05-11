package com.android.themoviedb.ui.detail;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.themoviedb.R;
import com.android.themoviedb.api.service.MovieService;
import com.android.themoviedb.api.service.RestApi;
import com.android.themoviedb.api.service.ServiceConfig;
import com.android.themoviedb.model.Movie;
import com.android.themoviedb.model.ReviewsResponse;
import com.android.themoviedb.model.Trailer;
import com.android.themoviedb.model.TrailersResponse;
import com.android.themoviedb.utils.ImageSize;
import com.android.themoviedb.utils.ImageUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 Created by twin on Dec 06 2016
Updated by twin on May 10, 2019
*/

public class DetailFragment extends Fragment {

    public final String TAG = DetailFragment.class.getSimpleName();

    public static final String ARG_MOVIE_ID = "movie_id";
    public static final String ARG_MOVIE_DATA = "movie_data";
    public static final String EXTRA_REVIEWS = "EXTRA_REVIEWS";
    private TrailersAdapter adapter;
    private ReviewsAdapter mReviewListAdapter;

    @BindView(R.id.mov_poster)ImageView posters;
    @BindView(R.id.mov_title)TextView titles;
    @BindView(R.id.mov_date)TextView releaseDates;
    @BindView(R.id.mov_rate)TextView ratingAverageView;
    @BindView(R.id.mov_ratebar)RatingBar ratingBarView;
    @BindView(R.id.mov_overview)TextView overview;
    @BindView(R.id.recyclerview_trailer) RecyclerView recyclerViewTrailer;
    @BindView(R.id.review_list) RecyclerView mRecyclerViewForReviews;

    public static Fragment newInstance(long movieId, String movieData){
        Bundle args = new Bundle();
        args.putLong(ARG_MOVIE_ID, movieId);
        args.putString(ARG_MOVIE_DATA, movieData);

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(args);

        return detailFragment;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Movie movie = new Gson().fromJson(getArguments().getString(ARG_MOVIE_DATA),
                Movie.class);

        Uri uri = ImageUtils.movieUrl(ImageSize.w342.getValue(),
                movie.posterPath);
        Picasso.get().load(uri.toString()).into(posters);

        titles.setText(movie.title);
        releaseDates.setText(movie.releaseDate);
        ratingAverageView.setText(String.valueOf(movie.voteAverage));
        ratingBarView.setRating((float) movie.voteAverage);
        overview.setText(movie.overview);

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(
                getActivity(),
                RecyclerView.VERTICAL,
                false);

        recyclerViewTrailer.setLayoutManager(gridLayoutManager);
        recyclerViewTrailer.setHasFixedSize(true);

        adapter = new TrailersAdapter();

        adapter.setOnClickListener(new TrailersAdapter.OnClickListener() {
            @Override
            public void onItemClickListener(String key,
                                            Trailer movie) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("vnd.youtube:" + key));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + key));
                try {
                    startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }
            }
        });

        mReviewListAdapter = new ReviewsAdapter();
        mRecyclerViewForReviews.setAdapter(mReviewListAdapter);
        recyclerViewTrailer.setAdapter(adapter);

        getTrailer(movie.id);
        getReviews(movie.id);
    }

    private void getReviews(long id) {
        MovieService apiService =
                RestApi.getClient().create(MovieService.class);
        Call<ReviewsResponse> call = apiService.reviewsMovie(id, ServiceConfig.API_KEY);
        call.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                Log.e(TAG, "Status Code = " + response.code());
                Log.e(TAG, "Data received: " + new Gson().toJson(response.body().results));

                if (response.code() == 200 && response.isSuccessful()){
                    mReviewListAdapter.setMoviesReview(response.body().results);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Error" );
                    alertDialog.setMessage("Symptom Tidak Ada" );
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Error" );
                alertDialog.setMessage("Kesalahan Jaringan" + t.getMessage());
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.show();
            }
        });
    }

    private void getTrailer(long id) {
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "loading...");

        MovieService apiService =
                RestApi.getClient().create(MovieService.class);

        Call<TrailersResponse> call = apiService.trailerMovie(id, ServiceConfig.API_KEY);
        call.enqueue(new Callback<TrailersResponse>() {
            @Override
            public void onResponse(Call<TrailersResponse>call, Response<TrailersResponse> response) {
                dialog.dismiss();

                Log.e(TAG, "Status Code = " + response.code());
                Log.e(TAG, "Data received: " + new Gson().toJson(response.body().results));

                if (response.code() == 200 && response.isSuccessful()){
                    adapter.setMoviesData(response.body().results);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Symptom Tidak Ada");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<TrailersResponse>call, Throwable t) {
                dialog.dismiss();

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Kesalahan Jaringan");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.show();
            }
        });
    }
}
