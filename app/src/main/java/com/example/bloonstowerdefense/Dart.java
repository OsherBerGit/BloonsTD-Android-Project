package com.example.bloonstowerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Objects;

public class Dart extends Shape {
    public float dx=0, dy=0;
    int timeleft;
    int direct;


    public Dart(float x, float y, String t, int direct ,Context context) {
        super(x, y);
        this.timeleft = 30;
        this.direct = direct;

        if (t.equals("Dart")) {
            this.b = BitmapFactory.decodeResource(context.getResources(), R.drawable.dart); }
//            dx = (float) (15*Math.cos(direct));
//            dy = (float) (15*Math.sin(direct));
        if (t.equals("up")) {
            this.b = BitmapFactory.decodeResource(context.getResources(), R.drawable.up);
            dy = -15; }
        if (t.equals("up_right")) {
            this.b = BitmapFactory.decodeResource(context.getResources(), R.drawable.up_right);
            dx = (int) (15 * 0.7071067812);
            dy = -(int) (15 *  0.7071067812); }
        if (t.equals("right")) {
            this.b = BitmapFactory.decodeResource(context.getResources(), R.drawable.right);
            dx = 15; }
        if (t.equals("down_right")) {
            this.b = BitmapFactory.decodeResource(context.getResources(), R.drawable.down_right);
            dx = (int) (15 * 0.7071067812);
            dy = (int) (15 * 0.7071067812); }
        if (t.equals("down")) {
            this.b = BitmapFactory.decodeResource(context.getResources(), R.drawable.down);
            dy = 15; }
        if (t.equals("down_left")) {
            this.b = BitmapFactory.decodeResource(context.getResources(), R.drawable.down_left);
            dx = -(int) (15 * 0.7071067812);
            dy = (int) (15 * 0.7071067812); }
        if (t.equals("left")) {
            this.b = BitmapFactory.decodeResource(context.getResources(), R.drawable.left);
            dx = -15; }
        if (t.equals("up_left")) {
            this.b = BitmapFactory.decodeResource(context.getResources(), R.drawable.up_left);
            dx = -(int) (15 * 0.7071067812);
            dy = -(int) (15 * 0.7071067812); }
        this.b = Bitmap.createScaledBitmap(this.b, (this.b.getWidth()/2), (this.b.getHeight()/2),true);
        }

    public void setBitmap(Bitmap b) {
        this.b = b;
    }

    public void draw(Canvas canvas) { canvas.drawBitmap(b, (x-b.getWidth()/3), (y-b.getHeight()/3),new Paint()); }

    public void move() {
        x = x + dx;
        y = y + dy;
    }
}