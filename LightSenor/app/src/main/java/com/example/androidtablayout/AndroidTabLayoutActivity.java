package com.example.androidtablayout;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AndroidTabLayoutActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TabHost tabHost = getTabHost();
        
        // 閃光
        TabSpec lightflash = tabHost.newTabSpec("");
        lightflash.setIndicator("", getResources().getDrawable(R.drawable.icon_lightflash_tab));
        Intent lightflashIntent = new Intent(this, flashlight.class);
        lightflash.setContent(lightflashIntent);
        
        // 接收
        TabSpec receive = tabHost.newTabSpec("");
        receive.setIndicator("", getResources().getDrawable(R.drawable.icon_receive_tab));
        Intent receiveIntent = new Intent(this, LightSensor.class);
        receive.setContent(receiveIntent);






        

        tabHost.addTab(lightflash);
        tabHost.addTab(receive);


    }
}