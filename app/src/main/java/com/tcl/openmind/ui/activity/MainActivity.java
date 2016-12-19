package com.tcl.openmind.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.util.SimpleArrayMap;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;
import com.tcl.openmind.R;
import com.tcl.openmind.ui.fragment.BaseFragment;
import com.tcl.openmind.ui.fragment.LookFragment;
import com.tcl.openmind.ui.fragment.NeteaseFragment;
import com.tcl.openmind.ui.fragment.ZhihuFragment;
import com.tcl.openmind.util.SharePreferenceUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FrameLayout mFragmentContainer;
    private DrawerLayout mDrawer;

    private MenuItem mCurrentMenuItem;
    private BaseFragment mCurrentFragment;
    private int mCurrNavigationId;

    SimpleArrayMap<Integer, String> mTitleArrayMap = new SimpleArrayMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        addFragmentAndTitle();

        if (savedInstanceState == null) {
            mCurrNavigationId = SharePreferenceUtil.getNevigationItem(this);
            if (mCurrNavigationId != -1) {
                mCurrentMenuItem = navigationView.getMenu().findItem(mCurrNavigationId);
            }
            if (mCurrentMenuItem == null){
                mCurrentMenuItem = navigationView.getMenu().findItem(R.id.nav_zhihu);
            }
            if (mCurrentMenuItem != null) {
                mCurrentMenuItem.setChecked(true);
                // TODO: 16/8/17 add a fragment and set toolbar title
                BaseFragment fragment = getFragmentById(mCurrentMenuItem.getItemId());
                String title = mTitleArrayMap.get((Integer) mCurrentMenuItem.getItemId());
                if (fragment != null) {
                    switchFragment(fragment, title);
                }
            }
        }
        else {
            if (mCurrentMenuItem != null){
                BaseFragment fragment = getFragmentById(mCurrentMenuItem.getItemId());
                String title = mTitleArrayMap.get((Integer) mCurrentMenuItem.getItemId());
                if (fragment != null) {
                    switchFragment(fragment, title);
                }
            }else {
                switchFragment(new ZhihuFragment(), " ");
                mCurrentMenuItem = navigationView.getMenu().findItem(R.id.nav_zhihu);

            }
        }

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

        LogUtils.d("onNavigationItemSelected");
        if (mCurrentMenuItem != item && mCurrentMenuItem != null) {
            mCurrentMenuItem.setChecked(false);
            int id = item.getItemId();
            SharePreferenceUtil.putNevigationItem(MainActivity.this, id);
            mCurrentMenuItem = item;
            mCurrentMenuItem.setChecked(true);
            switchFragment(getFragmentById(mCurrentMenuItem.getItemId()),mTitleArrayMap.get(mCurrentMenuItem.getItemId()));
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;

//        int id = item.getItemId();
//
//        if (id == R.id.nav_zhihu) {
//            // Handle the camera action
//        } else if (id == R.id.nav_wangyi) {
//
//        } else if (id == R.id.nav_kankan) {
//
//        }
//
//        mDrawer.closeDrawer(GravityCompat.START);
//        return true;
    }

    private void addFragmentAndTitle() {
        mTitleArrayMap.put(R.id.nav_zhihu, getResources().getString(R.string.zhihu));
        mTitleArrayMap.put(R.id.nav_netease, getResources().getString(R.string.netease));
        mTitleArrayMap.put(R.id.nav_kankan, getResources().getString(R.string.gallery));

    }

    private void switchFragment(BaseFragment fragment, String title) {

        if (mCurrentFragment == null || !mCurrentFragment.getClass().getName().equals(fragment.getClass().getName()))
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                    .commit();
        mCurrentFragment = fragment;
        Toast.makeText(this, "switch to " + fragment.getClass().getName(), Toast.LENGTH_SHORT).show();
    }

    private BaseFragment getFragmentById(int id) {
        BaseFragment fragment = null;
        switch (id) {
            case R.id.nav_zhihu:
                fragment = new ZhihuFragment();
                break;
            case R.id.nav_netease:
                fragment=new NeteaseFragment();
                break;
            case R.id.nav_kankan:
                fragment=new LookFragment();
                break;

        }
        return fragment;
    }

}
