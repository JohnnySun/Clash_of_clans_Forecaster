package moe.johnny.clashofclansforecaster;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import moe.johnny.clashofclansforecaster.stats.COCJsonFormat;
import moe.johnny.clashofclansforecaster.stats.GetStats;
import moe.johnny.clashofclansforecaster.stats.JsonStruct;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import moe.johnny.tools.getBarInfo;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    public PublishSubject<String> get_coc_status_Observable = PublishSubject.create();
    public RecyclerView mRecyclerView;
    public RecyclerView.LayoutManager mLayoutManager;
    public ContentMainAdapter mAdapter;
    public ArrayList<RecyclerViewDataset> mDataset = new ArrayList<>();
    private Toolbar toolbar;
    private AppBarLayout mAppBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_coc_status_Observable.onNext("Next");
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.main_content_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ContentMainAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /**
         * set up a padding for toolbar
         */
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        mAppBarLayout.setPadding(0, getBarInfo.getStatusBarHeight(this), 0, 0);

        initSubscribe();
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean initSubscribe() {
        /**
         * Init Subscribe
         */
        get_coc_status_Observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        GetStats.GetCOCStats();
                    }
                });

        COCJsonFormat.COCJson_Observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonStruct>() {
                    @Override
                    public void call(JsonStruct mJsonStruct) {
                       updateDataset(mJsonStruct);
                    }
                });

        /**
         * When we first init subscribe, we should get coc status,
         * to make sure we have data show to users.
         */
        get_coc_status_Observable.onNext("Init");

        return true;
    }

    public void updateDataset(JsonStruct mJsonStruct) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //getWindow().setStatusBarColor(Color.parseColor(mJsonStruct.bgColor));
            getWindow().setNavigationBarColor(Color.parseColor(mJsonStruct.mainColorShadeNow));
            toolbar.setBackgroundColor(Color.parseColor(mJsonStruct.mainColorShadeNow));
            mAppBarLayout.setBackgroundColor(Color.parseColor(mJsonStruct.mainColorShadeNow));

        }
        String info = "totalPlayers:" + mJsonStruct.totalPlayers + "\n"
                + "playersOnline: " + mJsonStruct.playersOnline + "\n"
                + "shieldedPlayers: " + mJsonStruct.shieldedPlayers + "\n"
                + "attackablePlayers: " + mJsonStruct.attackablePlayers;
        String index = "Loot Index: " + mJsonStruct.LootIndex;

        RecyclerViewDataset DataIndex = new RecyclerViewDataset();
        DataIndex.setText(index);
        DataIndex.setBgColor(mJsonStruct.bgColor);
        RecyclerViewDataset DataInfo = new RecyclerViewDataset();
        DataInfo.setText(info);
        DataInfo.setBgColor(mJsonStruct.bgColor);
        //if mDataset is null, we should first addd them, otherwise we should keep them update,not add them again.
        if(mDataset.size() != 0) {
            mDataset.set(0, DataIndex);
            mDataset.set(1, DataInfo);
            mAdapter.notifyItemChanged(0);
            mAdapter.notifyItemChanged(1);
            mAdapter.notifyDataSetChanged();
        } else {
            mDataset.add(DataIndex);
            mDataset.add(DataInfo);
            mAdapter.notifyItemInserted(mDataset.size());
            mAdapter.notifyDataSetChanged();
        }
    }
}
