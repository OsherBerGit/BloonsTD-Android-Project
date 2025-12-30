package com.example.bloonstowerdefense;

public class TowertoFb {
    float x,y;
    String t;

    public TowertoFb(float x, float y, String t) {
        this.x = x;
        this.y = y;
        this.t = t;
    }

    public TowertoFb(){}

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }
}
