package com.example.bloonstowerdefense;

import android.graphics.Bitmap;

public class PicEnable {
    float x, y;
    Bitmap bitmap;

    public PicEnable(float x, float y, Bitmap bitmap) {
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
    }

    public boolean diduserTouchMe(float xTouch, float yTouch){
        if (xTouch>x && xTouch<x+bitmap.getWidth() && yTouch>y && yTouch<y+bitmap.getHeight())
            return true;
        return false;
    }
}
