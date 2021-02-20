package com.zhboy.animationsnow;

import android.graphics.Point;

/**
 * @author: zhou_hao
 * @date: 2021/2/20
 * @description: 贝塞尔曲线
 **/
public class BezierUtils {

    /**
     * 三个点的贝塞尔曲线算法
     *
     * @param t  插值
     * @param p0 第一个点
     * @param p1 第一个点
     * @param p2 第一个点
     * @return 计算之后的点
     */
    public static Point calculatePoint(float t, Point p0, Point p1, Point p2) {
        Point point = new Point();

        float temp = 1 - t;

        point.x = (int) (temp * temp * p0.x + 2 * t * temp * p1.x + t * t * p2.x);
        point.y = (int) (temp * temp * p0.y + 2 * t * temp * p1.y + t * t * p2.y);

        return point;
    }

}
