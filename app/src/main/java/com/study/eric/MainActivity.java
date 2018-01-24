package com.study.eric;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.eric.base.BaseFragmentActivity;
import com.study.eric.design.DesignActivity;
import com.study.eric.jni.JniActivity;
import com.study.eric.tab.MainTabActivity;
import com.study.eric.video.VideoCaptureActivity;
import com.study.eric.widget.EmojiFlowLayout;

import butterknife.Bind;
import cn.com.bluemoon.cardocr.lib.CaptureActivity;
import cn.com.bluemoon.cardocr.lib.bean.BankInfo;
import cn.com.bluemoon.cardocr.lib.bean.IdCardInfo;
import cn.com.bluemoon.cardocr.lib.common.CardType;

public class MainActivity extends BaseFragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    @Bind(R.id.emojiFlowLayout)
    EmojiFlowLayout emojiFlowLayout;

    private String[] item = new String[]{"Kotlin", "Tab", "Dagger2","design", "jni", "video", "card"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        for (int i = 0; i < item.length; i ++) {
            String text = item[i];
            TextView addTextView = new TextView(this);
            addTextView.setText(text);
            addTextView.setId(i);
            addTextView.setSingleLine(true);
            addTextView.setBackgroundResource(R.drawable.text_flag);
            addTextView.setTextAppearance(this, R.style.text_flag);
            addTextView.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            params.setMargins(4,4,20,20);
            addTextView.setLayoutParams(params);
            addTextView.setOnClickListener(this);
            emojiFlowLayout.addView(addTextView);
        }

    }

    @Override
    public void onClick(View v) {
        TextView tv = (TextView)v;
        switch (tv.getId()) {
            case 0:
                Intent intent = new Intent(this, KotlinActivity.class);
                startActivity(intent);
                break;
            case 1:
                MainTabActivity.actStart(this);
                break;
            case 2:
                toast(item[2]);
                break;
            case 3:
                intent = new Intent(this, DesignActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(this, JniActivity.class);
                startActivity(intent);
                break;
            case 5:
                intent = new Intent(this, VideoCaptureActivity.class);
                startActivity(intent);
                break;
            case 6:
                CaptureActivity.startAction(this, CardType.TYPE_ID_CARD_FRONT, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            IdCardInfo info = (IdCardInfo)data.getSerializableExtra(CaptureActivity.BUNDLE_DATA);
            toast(info.toString());
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
}
