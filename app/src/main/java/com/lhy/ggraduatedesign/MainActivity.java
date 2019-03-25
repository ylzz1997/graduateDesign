package com.lhy.ggraduatedesign;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    DateProcessor dp;
    SensorUtil su;
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = findViewById(R.id.main_b1);
        bt.setOnTouchListener((v,e) -> {
            onButtonClick(v,e);
            return false;
        });
        su = new SensorUtil(this);
    }

    private void onButtonClick(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                su.Initialization();
                break;
            case MotionEvent.ACTION_UP:
                su.Pause();
                dp = new DateProcessor(su.getAngal(),su.getAccelerations(),su.getGyroscopes());

                String a = "";
                for(float[] i : su.getAngal()){
                    a+=i[2]+" ";
                }

                tv = findViewById(R.id.main_text);
                tv.setText(dp.getFinalAngal()+" "+dp.getFinalSpeed()+" \n"+su.getAngal().size()+" "+su.getAccelerations().size()+"\n"+a);
                su.Reset();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        su.Pause();
    }

    @Override
    public void setRequestedOrientation(int requestedOrientation){
        return;
    }
}
