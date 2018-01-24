package com.study.eric.jni;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.study.eric.R;
import com.study.eric.base.BaseFragmentActivity;

import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ljl on 2017/5/25.
 */

public class JniActivity extends BaseFragmentActivity {
    private JniTest jni;
    @Bind(R.id.txt)
    TextView textView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_jni;
    }

    @Override
    protected void initView() {
        jni = new JniTest();
        textView.setText(jni.getJniString());
    }

    @OnClick(R.id.btn_debugger)
    public void onClick(View v)  {
        switch (v.getId()) {
            case R.id.btn_debugger :
                Button  btn = (Button) v;

                break;
        }
    }
}
