package com.study.eric.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;


/**
 * 基础Fragment，所属activity必须为{@link BaseFragmentActivity}
 */
public abstract class BaseFragment extends Fragment {

    /**
     * 给fragment使用的bundle数据
     */
    public static final String EXTRA_BUNDLE_DATA = "EXTRA_BUNDLE_DATA";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isUseEventBus()) {
            EventBus.getDefault().register(this);
        }
        ButterKnife.bind(this, view);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isUseEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        onDestroyViewExtra();
    }

    protected void onDestroyViewExtra() {
        ButterKnife.unbind(this);
    }

    /**
     * 设置布局文件layout，一般都要重写
     */
    protected abstract int getLayoutId();

    protected boolean isUseEventBus() {
        return false;
    }

}
