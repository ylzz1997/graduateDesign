package com.lhy.ggraduatedesign;

        import android.content.SharedPreferences;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences settings;
    TextView tv;
    DateProcessor dp;
    SensorUtil su;
    Button bt;
    Button bto;
    Button setb;
    float body_height = 1.5f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = getSharedPreferences("setting", 0);
        body_height = Float.parseFloat(settings.getString("bh","1.5"));
        bto = findViewById(R.id.button_one);
        bt = findViewById(R.id.main_b1);
        setb = findViewById(R.id.setting);
        bt.setOnTouchListener((v,e) -> {
            onButtonClick(v,e);
            return false;
        });
        bto.setOnTouchListener((v,e) -> {
            onButtonClick(v,e);
            return false;
        });
        setb.setOnClickListener((v -> {
            showDialog();
        }));

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


    private void showDialog(){
        View view = LayoutInflater.from(this).inflate(R.layout.body_height,null,false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

        Button btn_cancel_high_opion = view.findViewById(R.id.bh_ok);
        Button btn_agree_high_opion = view.findViewById(R.id.bh_cancel);
        EditText bh = view.findViewById(R.id.editText);
        bh.setText(String.valueOf(body_height));
        btn_cancel_high_opion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                body_height = Float.parseFloat(bh.getText().toString());
                settings.edit().putString("bh",bh.getText().toString()).commit();
                //... To-do
                dialog.dismiss();
            }
        });

        btn_agree_high_opion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //... To-do
                dialog.dismiss();
            }
        });

        dialog.show();
        //此处设置位置窗体大小，我这里设置为了手机屏幕宽度的3/4
        dialog.getWindow().setLayout((this.getResources().getDisplayMetrics().widthPixels/4*3), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

}


