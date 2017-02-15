package com.study.eric.studyproject.tab;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentTabHost;
import android.view.Gravity;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.study.eric.studyproject.R;
import com.study.eric.studyproject.base.BaseTabActivity;
import com.study.eric.studyproject.bean.TabState;
import com.study.eric.studyproject.event.MainTabEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by ljl on 2017/1/23.
 */

public class MainTabActivity extends BaseTabActivity {

    @Bind(R.id.layout_appointment)
    LinearLayout layoutAppointment;
    @Bind(R.id.layout_appointment_root)
    LinearLayout layoutAppointmentRoot;
    @Bind(R.id.txt)
    TextView textView;
    @Bind(R.id.imgae)
    ImageView imageView;
    private boolean rotateTag = false;

    public static void actStart(Context context) {
        try {
            actionStart(context, getTabs(), MainTabActivity.class);
        }catch (Exception e) {
            Toast.makeText(context, "e= " + e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    public static void actStart(Context context, int index) {
        actionStart(context, getTabs(), MainTabActivity.class, index);
    }

    private static ArrayList<TabState> getTabs() {
        ArrayList<TabState> tabs = new ArrayList<>();

        tabs.add(new TabState(EmptyFragment.class, R.mipmap.science_selected,
                R.mipmap.science_unselect, R.string.tab_science));
        tabs.add(new TabState(EmptyFragment.class, R.mipmap.ic_community_blue,
                R.mipmap.ic_community_gray, R.string.tab_community));
        tabs.add(new TabState(MainFragment.class, R.mipmap.wash_unselect,
                R.mipmap.wash_unselect, R.string.tab_wash));
        tabs.add(new TabState(EmptyFragment.class, R.mipmap.ic_my_blue,
                R.mipmap.ic_my_gray, R.string.tab_my));
        tabs.add(new TabState(EmptyFragment.class, R.mipmap.ic_my_blue,
                R.mipmap.ic_my_gray, R.string.tab_my));
        return tabs;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_tab;
    }

    @Override
    protected void initView() {
        super.initView();
        FragmentTabHost tabhost = getTabHost();
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if ("2".equals(tabId)){
                    layoutAppointmentRoot.setVisibility(View.VISIBLE);
                    if (rotateTag) {
                        EventBus.getDefault().post(new MainTabEvent(!rotateTag));
                    }
                } else {
                    EventBus.getDefault().post(new MainTabEvent(!rotateTag));
                    layoutAppointmentRoot.setVisibility(View.GONE);
                }
            }
        });
        layoutAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MainTabEvent(!rotateTag));
            }
        });

    }

    private void rotateAction() {
        float pivotX = imageView.getWidth() / 2f;
        float pivotY = imageView.getHeight() / 2f;
        float fromDegrees;
        float toDegrees;
        rotateTag = !rotateTag;
        if (rotateTag) {
            fromDegrees = 0f;
            toDegrees = -45f;
            textView.setText("取消");
            //layoutAppointmentRoot.setClickable(false);
        } else {
            fromDegrees = -45f;
            toDegrees = 0f;
            textView.setText("预约");
            //layoutAppointmentRoot.setClickable(true);
        }
        RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
        animation.setDuration(200);
        animation.setFillAfter(true);
        imageView.startAnimation(animation);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MainTabEvent event) {
        rotateAction();
    }
}
