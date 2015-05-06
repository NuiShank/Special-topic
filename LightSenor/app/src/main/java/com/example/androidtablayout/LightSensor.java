package com.example.androidtablayout;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class LightSensor extends Activity implements SensorEventListener {
    SensorManager sensorManager = null;//傳感器管理應用
    Sensor lightSensor = null;//光傳感器引用
    private ToggleButton toggleButton;//切換按鈕
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
        setContentView(R.layout.lightsensor);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);



        if(lightSensor == null){
            AlertDialog alert = new AlertDialog.Builder(LightSensor.this)
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
        show.setMovementMethod(new ScrollingMovementMethod());//滑動背景
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        showe.setText("環境值:OFF");

        edt1.setText(Integer.toString(time));


        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {//開啟
                    onResume();
                    first=true;
                    flag=true;
                } else {//關閉
                    onPause();
                    first=false;
                    flag=false;
                    showe.setText("環境值:OFF");
                    handler.removeCallbacks(rec);

                }
            }
        });
    }
/*
    private Runnable updateTimer = new Runnable() {
        public void run() {
            if(flag2)
            {
                if (!flag && !first) {
                    if (test > Environmentvalue) {
                        show.setText(show.getText() + "1");
                    }
                    if (test < (Environmentvalue/2.2))
                    {
                        show.setText(show.getText() + "0");
                    }
                }
            }
            handler.postDelayed(this, time);
        }
    };
*/
    public void clear(View V)
    {
        show.setText("");
    }

/*
    protected void onDestroy() {
//將執行緒銷毀掉
        handler.removeCallbacks(updateTimer);
        super.onDestroy();
    }
*/


    public MovementMethod scrollbars(){
        return new ScrollingMovementMethod();

    }

    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //註銷
        sensorManager.unregisterListener(this, lightSensor);
    }

    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //間聽
        sensorManager.registerListener(this,lightSensor, SensorManager.SENSOR_DELAY_GAME);


    }


    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
        if(sensor.getType() == Sensor.TYPE_LIGHT)
        {
        }
    }

    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
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
            if(test>Environmentvalue && first)
            {first=false;
                time=Integer.parseInt(edt1.getText().toString());

                handler.post(rec);

                /*
                //設定定時要執行的方法
                handler.removeCallbacks(updateTimer);
                //設定Delay的時間
                handler.postDelayed(updateTimer, 0);
                */

            }

        }

    }

    public class ClockThread1 extends Thread{

        public void run(){

            if (!flag && !first) {
                if (test > Environmentvalue) {
                    show.setText(show.getText() + "1");
                }
                if (test < (Environmentvalue/3))
                {
                    show.setText(show.getText() + "0");
                }
            }

            handler.postDelayed(rec,time);

        }

    }

}