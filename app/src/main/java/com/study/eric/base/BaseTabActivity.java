package com.study.eric.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.study.eric.R;
import com.study.eric.bean.TabState;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 基础FragmentActivity，用于各集合的界面
 */
public abstract class BaseTabActivity extends BaseFragmentActivity {
    @Bind(android.R.id.tabhost)
    FragmentTabHost tabhost;

    private List<TabState> tabs;
    private int index = 0;

    protected static void actionStart(Context context, ArrayList<TabState> tabs, Class clazz) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra(BaseFragment.EXTRA_BUNDLE_DATA, tabs);
        context.startActivity(intent);
    }

    protected static void actionStart(Context context, ArrayList<TabState> tabs, Class clazz, int index) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra(BaseFragment.EXTRA_BUNDLE_DATA, tabs);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        index = intent.getIntExtra("index", -1);
        if (tabhost != null && index >= 0) {
            tabhost.setCurrentTab(index);
        }
    }

    @Override
    protected void onBeforeSetContentLayout() {
        tabs = (List<TabState>) getIntent().getSerializableExtra(BaseFragment.EXTRA_BUNDLE_DATA);
        if (tabs == null) {
            tabs = new ArrayList<>();
        }
    }

    public int getCurrentTab() {
        return tabhost.getCurrentTab();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.base_tab;
    }
    @Override
    protected void initView() {
        tabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        tabhost.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < tabs.size(); i++) {
            TabState ts = tabs.get(i);
            TabHost.TabSpec tabSpec = tabhost.newTabSpec(String.valueOf(i))
                    .setIndicator(getTabItemView(ts.getImgNormal(), ts.getImgSelected(),
                            getResources().getString(ts.getContent())));

            Bundle bundle = new Bundle();
            if (ts.getBundleData() != null) {
                bundle.putSerializable(BaseFragment.EXTRA_BUNDLE_DATA,
                        ts.getBundleData());
            }
            tabhost.addTab(tabSpec, ts.getClazz(), bundle);
        }
    }

    protected FragmentTabHost getTabHost() {
        return tabhost;
    }


    private View getTabItemView(int normal, int selected, String content) {
        View view = mInflater.inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_selected},
                getResources().getDrawable(selected));
        drawable.addState(new int[]{},
                getResources().getDrawable(normal));
        imageView.setImageDrawable(drawable);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(content);
        return view;
    }

    protected View getSpecialView(LayoutInflater mInflater, TabState ts) {
        return null;
    }

    ///////////// 工具方法 ////////////////

    /**
     * 显示底部tab
     */
    final public void showTabHost() {
        tabhost.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏底部tab
     */
    final public void hideTabHost() {
        tabhost.setVisibility(View.GONE);
    }

}
