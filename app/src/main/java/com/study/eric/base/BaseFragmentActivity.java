package com.study.eric.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.study.eric.event.FinishEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

public abstract class BaseFragmentActivity extends AppCompatActivity {

    protected LayoutInflater mInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBeforeSetContentLayout();
        setContentView(getLayoutId());
        mInflater = getLayoutInflater();
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected abstract void initView();

    protected abstract int getLayoutId();


    /**
     * toast提示方法
     *
     * @param msg
     */
    final protected void longToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    final protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    final protected void longToast(int resId) {
        longToast(getString(resId));
    }

    final protected void toast(int resId) {
        toast(getString(resId));
    }

    /**
     * 在oncreate后执行，一般为获取intentata
     */
    protected void onBeforeSetContentLayout() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FinishEvent event) {
        finish();
    }

}
