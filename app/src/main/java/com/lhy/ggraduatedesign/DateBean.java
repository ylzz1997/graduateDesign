package com.lhy.ggraduatedesign;

public class DateBean {
    private double vx;
    private double vy;
    private float angle;
    private float speed;
    private double distant;
    private double height;
    private float bodyHeight;
    private double time;
    final static float G = 9.8f;
    final static double PI = Math.PI;

    //h=Vyt-1/2gt^2+height;
    //h=Vyt-1/2gt^2+height;


    public DateBean(float angle, float speed, float bodyHeight) {
        this.angle = angle;
        this.speed = speed;
        this.bodyHeight = bodyHeight;

        vx = speed * Math.sin(angle*Math.PI/180);
        vy = speed * Math.cos(angle*Math.PI/180);

        height = bodyHeight + (vy*vy)/(2*G);
        time = vy/G+Math.sqrt((height*2)/G);
        distant = vx*time;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public double getDistant() {
        return distant;
    }

    public double getHeight() {
        return height;
    }

    public double getTime() {
        return time;
    }

    public float getBodyHeight() {
        return bodyHeight;
    }
}
