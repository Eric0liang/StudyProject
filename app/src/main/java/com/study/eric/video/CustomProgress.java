package com.study.eric.video;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.study.eric.R;

/**
 * 自定义按下进度同心圆
 * Created by liangjiangli on 2017/6/6.
 */

public class CustomProgress extends View {
    // 画实心圆的画笔
    private Paint mCirclePaint;
    // 画圆环的画笔
    private Paint mRingPaint;
    // 圆形颜色
    private int mCircleColor;
    // 圆环颜色
    private int mRingColor;
    // 半径
    private float mRadius;
    // 圆环半径
    private float mRingRadius;
    //进度条宽度
    private float circleWidth;
    //外圈宽度
    private float mStrokeWidth;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    // 总进度
    private int mTotalProgress;
    // 当前进度
    private int mProgress;
    //大圆
    private Paint mBigPatient;
    //外圆颜色
    private int mBigCircleColor;

    public CustomProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取自定义的属性
        initAttrs(context, attrs);
        initVariable();
    }

    private float initRadius;
    private float initStrokeWidth;
    private float initCircleWidth;
    private float initWidth;
    private float initHeight;

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustomProgress, 0, 0);
        mRadius = typeArray.getDimension(R.styleable.CustomProgress_radius, 300);
        mStrokeWidth = typeArray.getDimension(R.styleable.CustomProgress_strokeWidth, 20);
        circleWidth = typeArray.getDimension(R.styleable.CustomProgress_circleWidth, 20);
        mCircleColor = typeArray.getColor(R.styleable.CustomProgress_circleColor, Color.BLUE);
        mRingColor = typeArray.getColor(R.styleable.CustomProgress_ringColor, Color.RED);
        mTotalProgress = typeArray.getInt(R.styleable.CustomProgress_totalProgress, 100);
        mBigCircleColor = typeArray.getColor(R.styleable.CustomProgress_bigCircleColor, Color.WHITE);

        typeArray.recycle();//注意这里要释放掉

        mRingRadius = mRadius + mStrokeWidth / 2;

        //保存初始状态值
        initRadius = mRadius;
        initStrokeWidth = 0;
        initCircleWidth = circleWidth;
    }

    public void changeInitAttrs() {
        ViewGroup.LayoutParams  lp = getLayoutParams();
        lp.width = (int)initWidth;
        lp.height = (int)initHeight;
        setLayoutParams(lp);
        mRadius = initRadius;
        mStrokeWidth = initStrokeWidth;
        circleWidth = initCircleWidth;
        mRingRadius = mRadius + mStrokeWidth / 2;
        initVariable();
        invalidate();
    }


    public void changeAttrs() {
        ViewGroup.LayoutParams  lp = getLayoutParams();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        initWidth = lp.width;
        initHeight = lp.height;
        lp.width = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, displayMetrics));
        lp.height =((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, displayMetrics));
        setLayoutParams(lp);
        mRadius = getResources().getDimension(R.dimen.customProgress_radius);
        mStrokeWidth = getResources().getDimension(R.dimen.customProgress_stroke_width);
        circleWidth = getResources().getDimension(R.dimen.customProgress_circle_width);
        mRingRadius = mRadius + mStrokeWidth / 2;
        initVariable();
        invalidate();
    }

    private void initVariable() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mStrokeWidth);


        mBigPatient = new Paint();
        mBigPatient.setColor(mBigCircleColor);
        mBigPatient.setAntiAlias(true);
        mBigPatient.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;

        canvas.drawCircle(mXCenter, mYCenter, mRadius + circleWidth, mBigPatient);
        canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);

        if (mProgress > 0) {
            RectF oval = new RectF();
            float r = circleWidth - mStrokeWidth; //外圈减进度条宽度
            oval.left = (mXCenter - mRingRadius - r);
            oval.top = (mYCenter - mRingRadius - r);
            oval.right = mRingRadius * 2 + (mXCenter - mRingRadius) + r;
            oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius) + r;
            canvas.drawArc(oval, -90, ((float) mProgress / mTotalProgress) * 360, false, mRingPaint); //
        }
    }

    public void setProgress(int progress) {
        mProgress = progress;
        postInvalidate();
    }

    public void setmTotalProgress(int totalProgress) {
        mTotalProgress = totalProgress;
    }
}

