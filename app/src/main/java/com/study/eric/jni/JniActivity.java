package com.study.eric.jni;

import android.widget.TextView;

import com.study.eric.R;
import com.study.eric.base.BaseFragmentActivity;

import butterknife.Bind;

/**
 * Created by ljl on 2017/5/25.
 */

public class JniActivity extends BaseFragmentActivity {

    @Bind(R.id.txt)
    TextView textView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_jni;
    }

    @Override
    protected void initView() {
        JniTest jni = new JniTest();
        textView.setText(jni.getJniString());
    }
}
