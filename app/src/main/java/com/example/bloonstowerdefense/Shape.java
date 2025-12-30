package com.example.bloonstowerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public abstract class Shape {
    protected float x, y;
    protected Bitmap b;
    public Shape(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }

    public float getX() { return x; }

    public float getY() {return y;}
}