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
import com.unbounds.trakt.api.model.Show;

/**
 * Created by lydts on 1/15/2018.
 */

public class ShowDetailsActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private YouTubePlayerView youTubeView;

    private TextView tv_title,tv_overview,tv_rating,tv_runtime,tv_year;
    private ImageView iv_homepage,iv_imdb,iv_tmdb;

    Show show;

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
        tv_title = (TextView) findViewById(R.id.tv_det_title);
        tv_year = (TextView) findViewById(R.id.tv_det_year);

    }

    private void setData() {

        tv_overview.setText(show.getOverview());
        tv_year.setText(show.getYear()+"");
        tv_title.setText(show.getTitle());
        tv_runtime.setText(show.getRuntime()+"'");
        tv_rating.setText(String.valueOf(show.getRating()).substring(0,4));

    }

    private void getIntentData() {

        show = new Show();

        show.setTitle(getIntent().getStringExtra("title"));
        show.setYear(getIntent().getIntExtra("year",2013));
        show.setOverview(getIntent().getStringExtra("overview"));
        show.setImdb(getIntent().getStringExtra("imdb"));
        show.setTmdb(getIntent().getStringExtra("tmdb"));
        show.setTrailer(getIntent().getStringExtra("trailer"));
        show.setRating(getIntent().getDoubleExtra("rating",8.3));
        show.setRuntime(getIntent().getIntExtra("runtime",152));
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            System.out.println("trailer: "+show.getTrailer());
            player.cueVideo(show.getTrailer().split("=")[1]);
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
