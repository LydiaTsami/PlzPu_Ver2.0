package com.unbounds.trakt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.unbounds.trakt.login.LoginActivity;
import com.unbounds.trakt.login.LoginManager;
import com.unbounds.trakt.progress.ProgressFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "";
    private static final int LOGIN_REQUEST = 1;
    private static final String APP_SHARE_HASHTAG = " #PlzPUApp";

    private SectionsPageAdapter mSectionsPageAdapter;
    private ShareActionProvider mShareActionProvider;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Add", null).show();
//            }
//        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
//
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);
//        navigationView.getMenu().getItem(0).setChecked(true);
//        onNavigationItemSelected(navigationView.getMenu().getItem(0));

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
            setupViewPager(mViewPager,"Movies");
            //        Client client = ClientBuilder.newClient();
            //Response for popular movies
//        Response response = client.target("https://api.trakt.tv/movies/popular")
//                .request(MediaType.APPLICATION_JSON_TYPE)
//                .header("trakt-api-version", "2")
//                .header("trakt-api-key", "440f3de55f18c4ee68ebf7ac8329f1ebc6ab5a5e9729edaa36eb1bfe4324bb86")
//                .get();
//        System.out.println("status: " + response.getStatus());
//        System.out.println("headers: " + response.getHeaders());
//        System.out.println("body:" + response.readEntity(String.class));
        } else if (id == R.id.nav_show) {
            MainActivity.this.setTitle("Shows");
            setupViewPager(mViewPager,"Shows");
//        Client client = ClientBuilder.newClient();
//        //Response for popular shows
//        Response response = client.target("https://api.trakt.tv/shows/popular")
//                .request(MediaType.APPLICATION_JSON_TYPE)
//                .header("trakt-api-version", "2")
//                .header("trakt-api-key", "440f3de55f18c4ee68ebf7ac8329f1ebc6ab5a5e9729edaa36eb1bfe4324bb86")
//                .get();
            //        System.out.println("status: " + response.getStatus());
//        System.out.println("headers: " + response.getHeaders());
//        System.out.println("body:" + response.readEntity(String.class));

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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == LOGIN_REQUEST) {
            if (resultCode == RESULT_OK) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_content, new ProgressFragment()).commit();
            }
        }
    }

    private void setupViewPager(ViewPager viewPager,String type) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        if(type=="Movies"){
            //adapter.addFragment(new PopularMoviesFragment(), "Popular Movies");
        }
        else if(type=="Shows"){
            //adapter.addFragment(new PopularShowsFragment(), "Popular Shows");
//        adapter.addFragment(new Tab2Fragment(), "TAB2");
//        adapter.addFragment(new Tab3Fragment(), "TAB3");
        }
        viewPager.setAdapter(adapter);
    }


}
