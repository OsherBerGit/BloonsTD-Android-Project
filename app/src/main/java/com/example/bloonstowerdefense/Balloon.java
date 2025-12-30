package com.example.bloonstowerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Balloon extends Shape{
    int l; // the level of the bloon
    int dx=5, dy=0; // the speed of the bloon
    Context context;

    public Balloon(float x, float y, int l, Context context) {
        super(x,y);
        this.context = context;
        this.l = l;

        if(l==1)
            this.b = BitmapFactory.decodeResource(context.getResources(),R.drawable.red);
        if(l==2)
            this.b = BitmapFactory.decodeResource(context.getResources(),R.drawable.blu);
        if(l==3)
            this.b = BitmapFactory.decodeResource(context.getResources(),R.drawable.gre);
        this.b = Bitmap.createScaledBitmap(this.b, (int) (this.b.getWidth()/1.125), (int) (this.b.getHeight()/1.125),true);
    }

    public void moveTo(int d){
        if(d==1){
            dx=-5;
            dy=0;}
        if(d==2){
            dx=0;
            dy=5;}
        if(d==3){
            dx=0;
            dy=-5;}
        if(d==4){
            dx=5;
            dy=0;}
    }

    public void move(){
        x=x+dx*l*2;
        y=y+dy*l*2;
    }

    public void Pop() {
        l--;
        if(l==2)
            this.b = BitmapFactory.decodeResource(context.getResources(),R.drawable.blu);
        if(l==1)
            this.b = BitmapFactory.decodeResource(context.getResources(),R.drawable.red);
        this.b = Bitmap.createScaledBitmap(this.b, (int) (this.b.getWidth()/1.125), (int) (this.b.getHeight()/1.125),true);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(b,x-b.getWidth()/2,y-b.getHeight()/2,new Paint());
//        Paint p = new Paint();
//        p.setColor(Color.BLACK);
//        canvas.drawCircle(x,y,5,p);
    }
}
