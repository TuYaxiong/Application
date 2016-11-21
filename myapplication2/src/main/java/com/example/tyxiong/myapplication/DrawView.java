package com.example.tyxiong.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by tuyaxiong on 13/10/2016.
 * company Ltd
 * 53432973@qq.com
 */
/*
*
* 这就是什么个双缓冲...就是把先前的内容保存起来(用一个组件相同大小的内存区域)
* View组件的onDraw()方法是绘制的整个View内容,所以需要将之前的保存起来+更新
* */
public class DrawView extends View {
    private Bitmap bitmap;
    Canvas canvas = new Canvas();
    Paint paint = new Paint();
    private Path path = new Path();

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, int width, int height) {

        super(context);
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);//用于缓存的Bitmap
        canvas.setBitmap(bitmap);//新建一Canvas,它绘制的目标是Bitmap上
        paint.setStrokeWidth(2.0f);
        paint.setColor(Color.YELLOW);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DiscretePathEffect(3.0f,2.0f));//Path填充效果.
        paint.setDither(true);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap, 0, 0, paint);//绘制的目标是DrawView组件,将缓存绘制到组件上.
        canvas.drawPath(path, this.paint);      //将更新的绘制到组件上.
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                path.moveTo(x, y);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                path.lineTo(x, y);
                break;
            }
            case MotionEvent.ACTION_UP: {
                canvas.drawPath(path, paint);//绘制到Bitmap上
                path.reset();
                break;
            }

        }
        invalidate();
        return true;
    }
}




