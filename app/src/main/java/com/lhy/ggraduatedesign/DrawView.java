package com.lhy.ggraduatedesign;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View implements View.OnTouchListener {
    float PI=(float)Math.PI;//PI=3.1415那个什么的每次调用(float)Math.PI太麻烦，自定义一个。
    float canvasWidth,canvasHeight;//画布宽、高
    float width,height;//自定义长宽
    float left,up;//自定义左上角位置
    private DateBean dateBean;

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //开始一定要先调用Init()初始化参数。我自定义的参数没有默认值。
        if(dateBean==null){
            Paint paint=new Paint();
            paint.setTextSize(100f);
            canvas.drawText("尚未投掷",250,250,paint);
        }else if(dateBean!=null&&dateBean.getVx()==0){
            Paint paint=new Paint();
            paint.setTextSize(100f);
            canvas.drawText("投掷失败",250,250,paint);
        }
        else{
            Init(canvas);
            Render(canvas);
        }
    }
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        return false;
    }
    //初始化全局参数。
    //建议不要在其它函数里更改在这里初始化的参数，以免得不出正确结果
    void Init(Canvas canvas){
        //获取画布宽、高
        canvasWidth=(float)canvas.getWidth();
        canvasHeight=(float)canvas.getHeight();
        //自定义长宽
        if(dateBean.getHeight()*1.1>dateBean.getDistant()*canvasHeight/canvasWidth){
            width=height*canvasWidth/canvasHeight;
            height=(float)(dateBean.getHeight());
        }else {
            height=width*canvasHeight/canvasWidth;
            width=(float)(dateBean.getDistant());
        }
        // 自定义左上角位置
        left=0;up=height;
    }
    //渲染画面。
    //画图步骤在这里。
    void Render(Canvas canvas){
        //新建画笔
        Paint paint=new Paint();
        DrawCurve(canvas, paint);
    }


    void DrawGrid(Canvas canvas,float dx,float dy,Paint paint){
        //画纵向网格线
        //左侧
        float x=0;
        while (x>left){
            canvas.drawLine(PX(x),PY(up),PX(x),PY(up-height),paint);
            x-=dx;
        }
        //右侧
        x=0;
        while (x<width+left){
            canvas.drawLine(PX(x),PY(up),PX(x),PY(up-height),paint);
            x+=dx;
        }
        //画横向网格线
        //上侧
        float y=0;
        while (y<up){
            canvas.drawLine(PX(left),PY(y),PX(left+width),PY(y),paint);
            y+=dy;
        }
        //下侧
        y=0;
        while (y>up-height){
            canvas.drawLine(PX(left),PY(y),PX(left+width),PY(y),paint);
            y-=dy;
        }
    }

    void DrawCoordName(Canvas canvas,Paint paint,String xAxisName,String yAxisName,String originName){
        //设置文字大小
        paint.setTextSize(40f);
        //在适当位置显示x,y,O名称
        canvas.drawText(xAxisName,PX(left+width)-30f,PY(0f)+30f,paint);
        canvas.drawText(yAxisName,PX(0f),PY(up)+30f,paint);
        canvas.drawText(originName,PX(0f),PY(0f)+30f,paint);

    }
    //画点函数。使用自定义坐标。
    void DrawPoint(Canvas canvas,float x,float y,Paint paint){
        canvas.drawPoint(PX(x),PY(y),paint);
    }
    //(重要提示:所有你想画的曲线都在这里进行。)
    //画曲线函数。
    //使用画布坐标。所以要调用PX(x),PY(y)把自定义坐标里的量(如x,y)转换成画布坐标。
    void DrawCurve(Canvas canvas,Paint paint){//绘制曲线
        double vx = dateBean.getVx();
        double vy = dateBean.getVy();
        float bodyHeight = dateBean.getBodyHeight();
        double time = dateBean.getTime();
        double distant = dateBean.getDistant();
        double height = dateBean.getHeight();
        double a = vy/vx;
        double b = 9.8/(2*vx*vx);
        paint.setARGB(255,0,0,0);
        paint.setStrokeWidth(12);
            for(float x=left;x<distant;x+=0.001f){
                float y=(float)(a*x-b*x*x+bodyHeight);
            DrawPoint(canvas,x,y,paint);
        }
        //因为x,y是自定义坐标，canvas.drawText()是系统提供的函数，
        // 所以要调用PX(),PY()将(PI/2,sin(PI/2))转换成画布坐标；
    }
    //将自定义坐标转换成画布坐标的函数，
    //当要用自定义坐标在系统提供的函数上绘图时，要调用这两个函数把自定义坐标转换成画布坐标
    float PX(float x){
        return (x-left)*canvasWidth/width;
    }
    float PY(float y){
        return (up-y)*canvasHeight/height;
    }

    public DateBean getDateBean() {
        return dateBean;
    }

    public void setDateBean(DateBean dateBean) {
        this.dateBean = dateBean;
    }
}
