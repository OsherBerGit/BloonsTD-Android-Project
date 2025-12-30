package com.example.bloonstowerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Square {
    static int Left = 1;
    static int Down = 2;
    static int Up = 3;
    static int Right = 4;
    static int EmptyVal = 0;
    private float  w,h;
    private Paint p;
    private int direction;
    private float x; //x location on canvas
    private float y; //y location on canvas

    public Square(float x, float y, float w, float h, int color) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        p = new Paint();
        p.setColor(color);
        direction = 0;
    }

    public int getDirecetion(){
        return direction;
    }

    public int getColor() {
        return p.getColor();
    }

    public void toRoute(int color, int d) {
        this.p.setColor(color);
        direction = d;
    }
    public void draw(Canvas canvas) {canvas.drawRect(x,y,x+w,y+h,p);}

//    public boolean didXandYInSquare(float xc, float yc)
//    {
//        // xc & yc are the coin location
//        // check if the point in the middle of the circle, is in the square
//        if(xc > x && xc < x+w && yc > y && yc < y+h)
//            return  true;
//        return false;
//    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
