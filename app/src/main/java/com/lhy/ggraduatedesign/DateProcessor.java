package com.lhy.ggraduatedesign;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DateProcessor {
    public final static double PER_TIME = 0.015;
    private List<float[]> angal;
    private List<float[]> accelerations;
    private List<float[]> gyroscopes;
    private List<Float> z = new ArrayList<>();
    private List<Float> zx = new ArrayList<>();

    private float gyroscopesAngel;
    private float finalAngal;
    private float finalSpeed;
    public void setAngal(List<float[]> angal) {
        this.angal = angal;
    }

    public void setAccelerations(List<float[]> accelerations) {
        this.accelerations = accelerations;
    }


    public DateProcessor(List<float[]> angal, List<float[]> accelerations, List<float[]> gyroscopes) {
        this.angal = angal;
        this.accelerations = accelerations;
        this.gyroscopes = gyroscopes;

        gyroscopessum();
        ProcessAngle();
        speed();
    }

    private float PerProcessAngle(float[] i){
        float z = i[2] - 90;
        if(z<0){
            return 0;
        }else if(z>180){
            return 180;
        }else {
            return z;
        }
    }

    public float getFinalAngal() {
        return finalAngal;
    }

    public float getFinalSpeed() {
        return finalSpeed;
    }

    private void ProcessAngle(){
        for(float[] i : angal){
            z.add(PerProcessAngle(i));
            zx.add(i[2]);
        }
        if(z.size()-1>=0){
            int halfsize = (int)Math.ceil(zx.size()/2);
            List list = zx.subList(0,halfsize+1);
            float max = (float)Collections.max(list);
            float n = max+gyroscopesAngel-90;
            finalAngal = n;
        }
        else
            finalAngal =0;
    }

    private void speed(){
        float sum = 0;
        for(int i =0;i<accelerations.size();i++){
            double n = (accelerations.get(i))[0];
            if(n<0){
                n=-n;
            }
            sum+=n*PER_TIME;
        }
        finalSpeed = sum;
    }

    private void gyroscopessum(){
        float sum = 0;
        for(float[] n: gyroscopes){
            sum+=Math.abs(n[1])*0.18;
        }
        gyroscopesAngel=sum;
    }
}
