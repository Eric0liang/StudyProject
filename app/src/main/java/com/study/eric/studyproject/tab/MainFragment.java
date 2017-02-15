package com.study.eric.studyproject.tab;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.study.eric.studyproject.DensityUtil;
import com.study.eric.studyproject.R;
import com.study.eric.studyproject.base.BaseFragment;
import com.study.eric.studyproject.event.MainTabEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

public class MainFragment extends BaseFragment {

    @Bind(R.id.hello)
    TextView hello;
    @Bind(R.id.iv_reserve)
    ImageView ivReserve;
    @Bind(R.id.iv_reserve2)
    ImageView ivReserve2;
    @Bind(R.id.root)
    LinearLayout root;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_main;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String text = " Main Fragment ";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 800; i++) {
            sb.append(text);
        }
        hello.setText(sb.toString());
        ivReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "reserve click", Toast.LENGTH_LONG).show();
            }
        });
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MainTabEvent(false));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MainTabEvent event) {
        if (event.getTag()) {
            AnimatorSet set = new AnimatorSet();
            AnimatorSet set2 = new AnimatorSet();
            ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(ivReserve, "scaleX", 0f, 1.0f);
            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(ivReserve, "scaleY", 0f, 1.0f);
            set.play(objectAnimatorX).with(objectAnimatorY);

            objectAnimatorX = ObjectAnimator.ofFloat(ivReserve2, "scaleX", 0f, 1.0f);
            objectAnimatorY = ObjectAnimator.ofFloat(ivReserve2, "scaleY", 0f, 1.0f);
            set2.play(objectAnimatorX).with(objectAnimatorY);

            objectAnimatorX = ObjectAnimator.ofFloat(ivReserve, "translationX",
                    DensityUtil.dip2px(getActivity(), 47), 0f);
            objectAnimatorY = ObjectAnimator.ofFloat(ivReserve, "translationY", DensityUtil.dip2px(getActivity(), 65), 0f);
            set.playTogether(objectAnimatorX);
            set.playTogether(objectAnimatorY);

            objectAnimatorX = ObjectAnimator.ofFloat(ivReserve2, "translationX",
                    DensityUtil.dip2px(getActivity(), -47), 0f);
            objectAnimatorY = ObjectAnimator.ofFloat(ivReserve2, "translationY", DensityUtil.dip2px(getActivity(), 65), 0f);
            set2.playTogether(objectAnimatorX);
            set2.playTogether(objectAnimatorY);

            set.setDuration(300);
            set2.setDuration(300);
            set.start();
            set2.start();
            root.setVisibility(View.VISIBLE);
            ivReserve.setVisibility(View.VISIBLE);
            ivReserve2.setVisibility(View.VISIBLE);
        } else {
            ivReserve.clearAnimation();
            ivReserve2.clearAnimation();
            AnimatorSet set = new AnimatorSet();
            AnimatorSet set2 = new AnimatorSet();
            ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(ivReserve, "scaleX", 1.0f, 0f);
            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(ivReserve, "scaleY", 1.0f, 0f);
            set.play(objectAnimatorX).with(objectAnimatorY);

            objectAnimatorX = ObjectAnimator.ofFloat(ivReserve2, "scaleX", 1.0f, 0f);
            objectAnimatorY = ObjectAnimator.ofFloat(ivReserve2, "scaleY", 1.0f, 0f);
            set2.play(objectAnimatorX).with(objectAnimatorY);

            objectAnimatorX = ObjectAnimator.ofFloat(ivReserve, "translationX",
                    0f, DensityUtil.dip2px(getActivity(), 47));
            objectAnimatorY = ObjectAnimator.ofFloat(ivReserve, "translationY", 0f, DensityUtil.dip2px(getActivity(), 65));
            set.playTogether(objectAnimatorX);
            set.playTogether(objectAnimatorY);

            objectAnimatorX = ObjectAnimator.ofFloat(ivReserve2, "translationX", 0f,
                    DensityUtil.dip2px(getActivity(), -47));
            objectAnimatorY = ObjectAnimator.ofFloat(ivReserve2, "translationY", 0f, DensityUtil.dip2px(getActivity(), 65));
            set2.playTogether(objectAnimatorX);
            set2.playTogether(objectAnimatorY);
            set.setDuration(300);
            set2.setDuration(300);
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (ivReserve != null)
                        ivReserve.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            set2.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (ivReserve2 != null)
                        ivReserve2.setVisibility(View.GONE);
                    if (root != null)
                        root.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            set.start();
            set2.start();

        }
    }

    @Override
    protected boolean isUseEventBus() {
        return true;
    }

    @OnClick({R.id.btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                Toast.makeText(getActivity(), "btn click", Toast.LENGTH_LONG).show();
                break;
            case R.id.iv_reserve:
                Toast.makeText(getActivity(), "reserve click", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
