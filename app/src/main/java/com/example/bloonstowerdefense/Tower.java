package com.example.bloonstowerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Tower extends Shape{
    Paint p;
    String t;
    Context Context;
    int RangeColor;
    int range;
    int countdown;

    float degree, lastdegree=0;
    public Tower(float x, float y, String t, Context context) {
        super(x,y);
        Context = context;
        this.t = t;

        p = new Paint();
        p.setColor(Color.RED);
        RangeColor = Color.RED;
        countdown = 0;

        if(t.equals("Dart")){
            this.b = BitmapFactory.decodeResource(context.getResources(),R.drawable.dart_monkey);
            range = 325;}
        if(t.equals("Tack")){
            this.b = BitmapFactory.decodeResource(context.getResources(),R.drawable.tack_shooter);
            range = 250;}
    }

    public void draw(Canvas canvas)
    {
        p.setAlpha(50);
        // p.setStyle(Paint.Style.STROKE);
        // p.setStrokeWidth(20);
        // canvas.drawCircle(x,y,range+5,p);
        // p.setStyle(Paint.Style.FILL);
        // p.setColor(Color.GREEN);
        if (RangeColor != Color.TRANSPARENT)
            canvas.drawCircle(x,y,range,p);
        canvas.drawBitmap(b, x-b.getWidth()/2,y-b.getHeight()/2,null);
    }

    public void setXandY(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean didUserTouchMe(float xu, float yu)
    {
        if (xu>x-b.getWidth()/2 && xu<x+b.getWidth()/2 && yu>y-b.getHeight()/2 && yu<y+b.getHeight()/2)
            return true;
        return false;
    }

    public void setRangeColor(int color) {
        RangeColor = color;
        p.setColor(RangeColor); }

    public int getRangeColor() {
        return RangeColor; }

    public boolean inRange(Balloon b){ // if the balloon is near to the tower
        return Math.sqrt(Math.pow(b.x-this.x,2)+Math.pow(b.y-this.y,2)) <= this.range;
    }

    public void setBitmap(Bitmap b) { // Exchange the bitmap
        this.b = b;
    }

    public Dart Shoot(Balloon b) { // Dart Monkey's shooting
        return new Dart(this.x,this.y, this.t,(int) (this.degree), b.context);
    }

    public Dart[] Explode(){ // Tack Shooter's shooting
        Dart[] darts = new Dart[8];
        darts[0] = new Dart(x,y,"up",0, Context);
        darts[1] = new Dart(x,y,"up_right",0,Context);
        darts[2] = new Dart(x,y,"right",0,Context);
        darts[3] = new Dart(x,y,"down_right",0,Context);
        darts[4] = new Dart(x,y,"down",0,Context);
        darts[5] = new Dart(x,y,"down_left",0,Context);
        darts[6] = new Dart(x,y,"left",0,Context);
        darts[7] = new Dart(x,y,"up_left",0,Context);
        return darts;
    }
}
