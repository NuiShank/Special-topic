package com.example.shanktesa.ls;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements SensorEventListener {
    SensorManager sensorManager = null;//傳感器管理應用
    Sensor lightSensor = null;//光傳感器引用
    ClockThread1 rec = new ClockThread1();


    int time=50;
    EditText edt1;
    TextView value_0 = null,show,showe;
    Float Environmentvalue,test;
    boolean flag=true,first=true;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);



        if(lightSensor == null){
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
                    .create();
            alert.setTitle("Error");
            alert.setMessage("不支援亮度感光器");
            alert.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // closing the application

                }
            });
            alert.show();
            return;
        }


        value_0 = (TextView)findViewById(R.id.textView1);
        show=(TextView) findViewById(R.id.textView3);
        edt1=(EditText) findViewById(R.id.editText);
        showe=(TextView) findViewById(R.id.textView);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        showe.setText("環境值:OFF");

        edt1.setText(Integer.toString(time));


        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {//開啟
                    onResume();
                    first = true;
                    flag = true;
                } else {//關閉
                    onPause();
                    showe.setText("環境值:OFF");
                    handler.removeCallbacks(rec);

                }
            }
        });
    }

    public void clear(View V)
    {
        show.setText("");
    }





    protected void onPause() {

        super.onPause();
        //註銷
        sensorManager.unregisterListener(this, lightSensor);
    }

    protected void onResume() {

        super.onResume();
        //間聽
        sensorManager.registerListener(this,lightSensor, SensorManager.SENSOR_DELAY_FASTEST);


    }


    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_LIGHT)
        {
            test = event.values[0];
            value_0.setText("強度:"+event.values[0]);
            //取得環境值
            if(flag)
            {
                Environmentvalue= event.values[0]*(float)2.5;
                showe.setText("環境值:"+String.valueOf(Environmentvalue));
                flag=false;
            }
            //閃第一下
            else if ((test > Environmentvalue) && first) {
                first = false;
                time = Integer.parseInt(edt1.getText().toString());

                MyCount timerCount = new MyCount(time - 2, 1);
                timerCount.start();


            }

        }

    }

    public class ClockThread1 extends Thread{

        public void run(){

            if (!flag && !first) {
                if (test > Environmentvalue) {
                    show.setText(show.getText() + "1");
                }
                if (test < (Environmentvalue/3.2))
                {
                    show.setText(show.getText() + "0");
                }
            }

            handler.postDelayed(rec,time);

        }

    }

//倒數
    public class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

    //時間到執行
        @Override
        public void onFinish() {
            handler.post(rec);
        }
    //過程
        @Override
        public void onTick(long millisUntilFinished) {

        }
    }

}