package com.zhboy.animationsnow;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

/**
 * @author: zhou_hao
 * @date: 2021/2/20
 * @description: 雪花粒子
 **/
public class SnowBean {
    private int x;//位置 x
    private int y;//位置 y
    private int color;//颜色
    private int radius;//大小
    private int rotation;//旋转的角度
    private int speed;//下落速度

    private int parentWidth;//自定义view的宽
    private int parentHeight;//自定义view的高

    private int strokeWidth;//绘制雪花的线宽

    //绘制贝塞尔曲线，做雪花下路的曲线效果
    private Point startPoint;
    private Point middlePoint;
    private Point endPoint;


    /**
     * 定义静态的构造方法
     *
     * @param width  自定义view的宽
     * @param height 自定义view的高
     * @return snow实体
     */
    public static SnowBean getInstance(int width, int height) {

        SnowBean snowBean = new SnowBean();

        snowBean.setParentHeight(height);
        snowBean.setParentWidth(width);

        Random random = new Random();
        int x = random.nextInt(width);//初始位置x是0-width
        int y = -random.nextInt(height);//初始位置y

        int radius = 10 + random.nextInt(10);//大小是10-20
        int rotation = random.nextInt(60);//旋转角度是60度
        int speed = 2 + Math.abs(20 - radius) / 2;//越大的雪花，下落的越块

        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        int color = Color.rgb(r, g, b);

        int strokeWidth = (int) (radius * 0.2);//雪花线宽,根据雪花大小变化

        //贝塞尔曲线三个点
        Point start = new Point(random.nextInt(width), -random.nextInt(height));
        Point middle = new Point(random.nextInt(width), random.nextInt(height));
        Point end = new Point(random.nextInt(width), height + random.nextInt(height));

        snowBean.setX(x);
        snowBean.setY(y);
        snowBean.setColor(color);
        snowBean.setRadius(radius);
        snowBean.setRotation(rotation);
        snowBean.setSpeed(speed);
        snowBean.setStrokeWidth(strokeWidth);

        snowBean.setStartPoint(start);
        snowBean.setMiddlePoint(middle);
        snowBean.setEndPoint(end);

        return snowBean;
    }

    /**
     * 下落过程的逻辑
     */
    public void dropDownStep() {
        y += speed;

        //当雪花粒子不可见了，就重新回到上方
        if (y > parentHeight) {
            y = -50;
        }
        setRotation(getRotation() + 1);//设置旋转的角度
    }


    public void onDraw(Canvas canvas, Paint paint) {
        canvas.save();//对canvas进行操作的是都应该保存

        paint.setColor(getColor());//设置画笔的颜色
        paint.setStrokeWidth(strokeWidth);

        //每一次绘制角度都需要更新 所以要动态获取当前角度
        canvas.rotate(getRotation(), getX(), getY());

        //画出一个雪花
        for (int i = 0; i < 6; i++) {

            //画雪花六分之一花瓣的中间线
            int startLineX = getX();
            int endLineX = getX() + getRadius();
            canvas.drawLine(startLineX, getY(), endLineX, getY(), paint);

            //画雪花六分之一花瓣的上边线
            int startLine1X = (int) (getX() + getRadius() * 0.5);
            int startLine1Y = getY();
            double degree60 = Math.toRadians(60);
            int endLine1X = (int) (startLine1X + radius * 0.5 * Math.cos(degree60));
            int endLine1Y = (int) (startLine1Y - radius * 0.5 * Math.sin(degree60));
            canvas.drawLine(startLine1X, startLine1Y, endLine1X, endLine1Y, paint);

            //画雪花六分之一花瓣的下边线
            int startLine2X = (int) (getX() + getRadius() * 0.5);
            int startLine2Y = getY();
            int endLine2X = (int) (startLine1X + radius * 0.5 * Math.cos(degree60));
            int endLine2Y = (int) (startLine1Y + radius * 0.5 * Math.sin(degree60));
            canvas.drawLine(startLine2X, startLine2Y, endLine2X, endLine2Y, paint);

            //对当前的画布进行旋转，从而形成一个六边形雪花粒子
            canvas.rotate(60, getX(), getY());
        }

        //绘制的雪花的坐标
//        canvas.drawRect(getX() - getRadius(), getY() - getRadius(), getX() + getRadius(), getY() + getRadius(), paint);
        canvas.restore();//对canvas进行操作的是都应该重新恢复
    }

    public int getX() {
        //通过贝塞尔曲线 调整x值
        float t = getY() * 1.0f / (getEndPoint().y - getStartPoint().y);
        return BezierUtils.calculatePoint(t, getStartPoint(), getMiddlePoint(), getEndPoint()).x;

    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getParentWidth() {
        return parentWidth;
    }

    public void setParentWidth(int parentWidth) {
        this.parentWidth = parentWidth;
    }

    public int getParentHeight() {
        return parentHeight;
    }

    public void setParentHeight(int parentHeight) {
        this.parentHeight = parentHeight;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getMiddlePoint() {
        return middlePoint;
    }

    public void setMiddlePoint(Point middlePoint) {
        this.middlePoint = middlePoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }
}
