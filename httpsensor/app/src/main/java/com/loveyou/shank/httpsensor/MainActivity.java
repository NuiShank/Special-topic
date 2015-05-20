package com.loveyou.shank.httpsensor;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {

    Button AccelerometerBtn,GyroscopeBtn,ProximityBtn,PedometerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AccelerometerBtn = (Button) findViewById(R.id.Accelerometer);
        GyroscopeBtn = (Button) findViewById(R.id.Gyroscope);
        ProximityBtn = (Button) findViewById(R.id.Proximity);
        PedometerBtn = (Button) findViewById(R.id.Pedometer);
        if (AccelerometerBtn != null)
            AccelerometerBtn.setOnClickListener(this);
        if (GyroscopeBtn != null)
            GyroscopeBtn.setOnClickListener(this);
        if ( ProximityBtn != null)
            ProximityBtn.setOnClickListener(this);
        if ( PedometerBtn != null)
            PedometerBtn.setOnClickListener(this);
    }

    public void onClick(View v)

    {
        if (v == AccelerometerBtn)
        {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Accelermeter.class);
            startActivity(intent);

        }

        if (v == GyroscopeBtn)
        {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Gyroscope.class);
            startActivity(intent);

        }
        if (v == ProximityBtn)
        {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Proximity.class);
            startActivity(intent);

        }
        if (v == PedometerBtn)
        {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, TestPedometer.class);
            startActivity(intent);

        }

    }
}
