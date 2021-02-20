package com.zhboy.animationsnow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhou_hao
 * @date: 2021/2/20
 * @description:
 **/
public class SnowView extends View {

    private Paint paint;
    private List<SnowBean> snowBeanList; //雪花的集合
    private int snowNum = 50;//雪花的数量

    public SnowView(Context context) {
        super(context);
    }

    public SnowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        snowBeanList = new ArrayList<>();
    }

    public SnowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //进行雪花粒子的添加
        snowBeanList.clear();
        for (int i = 0; i < snowNum; i++) {
            snowBeanList.add(SnowBean.getInstance(w, h));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //进行雪花粒子的绘制
        for (int i = 0; i < snowNum; i++) {
            snowBeanList.get(i).onDraw(canvas, paint);//绘制雪花粒子
            snowBeanList.get(i).dropDownStep();//绘制下路速度
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        }, 10);
    }
}
