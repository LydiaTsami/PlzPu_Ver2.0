package com.unbounds.trakt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.unbounds.trakt.login.LoginActivity;
import com.unbounds.trakt.login.LoginManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "";
    private static final int LOGIN_REQUEST = 1;
    private static final String APP_SHARE_HASHTAG = " #PlzPUApp";

    private FragmentManager fragmentManager;
    private Fragment fragment = null;
    private SectionsPageAdapter mSectionsPageAdapter;
    private ShareActionProvider mShareActionProvider;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

//        if (LoginManager.getInstance().isLoggedIn()) {
//            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.menu.activity_navigation_drawer);
//            remoteViews.setTextViewText(R.id.sign_in, "Sign out");
//        }

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        if(LoginManager.getInstance().isLoggedIn()){
//            Button button = (Button) findViewById(R.id.sign_in);
//            button.setVisibility();
//            button.setText("Log out");
//        }


        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_movie) {
            MainActivity.this.setTitle("Movies");
            fragment = new MovieFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_content, fragment);
            transaction.commit();
            //setupViewPager(mViewPager,"Movies");

        } else if (id == R.id.nav_show) {
            MainActivity.this.setTitle("Shows");
            fragment = new ShowFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_content, fragment);
            transaction.commit();
            //setupViewPager(mViewPager, "Shows");

        } else if (id == R.id.nav_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, APP_SHARE_HASHTAG);
            startActivity(Intent.createChooser(shareIntent,"Share using"));

        } else if (id == R.id.nav_send) {

        }
        else if( id == R.id.sign_in){
            if(!LoginManager.getInstance().isLoggedIn())
                startActivityForResult(LoginActivity.createIntent(MainActivity.this), LOGIN_REQUEST);
            else {
                Toast.makeText(MainActivity.this, "Already signed in!",
                        Toast.LENGTH_LONG).show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
