package com.study.eric.studyproject

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

import com.study.eric.studyproject.base.BaseFragmentActivity
import com.study.eric.studyproject.widget.EmojiFlowLayout

import butterknife.Bind

class KotlinMainActivity : BaseFragmentActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    @Bind(R.id.emojiFlowLayout)
    internal var emojiFlowLayout: EmojiFlowLayout? = null

    private val item = arrayOf("Kotlin", "Dagger2")

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton?
        fab!!.setOnClickListener { view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show() }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer!!.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView?
        navigationView!!.setNavigationItemSelectedListener(this)

        for (i in item.indices) {
            val text = item[i]
            val addTextView = TextView(this)
            addTextView.text = text
            addTextView.id = i
            addTextView.setSingleLine(true)
            addTextView.setBackgroundResource(R.drawable.text_flag)
            addTextView.setTextAppearance(this, R.style.text_flag)
            addTextView.gravity = Gravity.CENTER

            val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            params.setMargins(4, 4, 20, 20)
            addTextView.layoutParams = params
            addTextView.setOnClickListener(this)
            emojiFlowLayout!!.addView(addTextView)
        }

    }

    override fun onClick(v: View) {
        val tv = v as TextView
        when (tv.id) {
            0 -> {
                val intent = Intent(this, KotlinActivity::class.java)
                startActivity(intent)
            }
            1 -> toast(item[1])
        }
    }


    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    @SuppressWarnings("StatementWithEmptyBody")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        drawer!!.closeDrawer(GravityCompat.START)
        return true
    }
}
