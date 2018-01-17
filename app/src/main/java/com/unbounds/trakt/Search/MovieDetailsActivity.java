package com.unbounds.trakt.Search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.unbounds.trakt.BuildConfig;
import com.unbounds.trakt.R;
import com.unbounds.trakt.api.model.Movie;

/**
 * Created by lydts on 1/15/2018.
 */

public class MovieDetailsActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private YouTubePlayerView youTubeView;

    private TextView tv_tagline,tv_overview,tv_rating,tv_runtime,tv_year;
    private ImageView iv_homepage,iv_imdb,iv_tmdb;

    Movie movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getIntentData();

        init();


        setData();
    }

    private void init() {

        youTubeView = (YouTubePlayerView) findViewById(R.id.player_view);
        youTubeView.initialize(BuildConfig.YOUTUBE_API_KEY, this);

        tv_overview = (TextView) findViewById(R.id.tv_det_overview);
        tv_rating = (TextView) findViewById(R.id.tv_det_rating);
        tv_runtime = (TextView) findViewById(R.id.tv_det_time);
        tv_tagline = (TextView) findViewById(R.id.tv_det_tagline);
        tv_year = (TextView) findViewById(R.id.tv_det_year);

    }

    private void setData() {

        tv_overview.setText(movie.getOverview());
        tv_year.setText(movie.getYear()+"");
        tv_tagline.setText(movie.getTagline());
        tv_runtime.setText(movie.getRuntime()+"'");
        tv_rating.setText(String.valueOf(movie.getRating()).substring(0,4));

    }

    private void getIntentData() {

        movie = new Movie();

        movie.setTitle(getIntent().getStringExtra("title"));
        movie.setYear(getIntent().getIntExtra("year",2013));
        movie.setTagline(getIntent().getStringExtra("tagline"));
        movie.setOverview(getIntent().getStringExtra("overview"));
        movie.setGenres(getIntent().getStringExtra("genres"));
        movie.setHomepage(getIntent().getStringExtra("homepage"));
        movie.setImdb(getIntent().getStringExtra("imdb"));
        movie.setTmdb(getIntent().getStringExtra("tmdb"));
        movie.setTrailer(getIntent().getStringExtra("trailer"));
        movie.setRating(getIntent().getDoubleExtra("rating",8.3));
        movie.setRuntime(getIntent().getIntExtra("runtime",152));
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(movie.getTrailer().split("=")[1]);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format("There was an error initializing the YouTubePlayer", errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

}
