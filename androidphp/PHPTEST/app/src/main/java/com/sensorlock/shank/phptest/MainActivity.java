package com.sensorlock.shank.phptest;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements SensorEventListener {
    private String msgx,msgy,msgz,msg,imei;
    private TextView text_x;
    private TextView text_y;
    private TextView text_z,Gtext;
    private SensorManager aSensorManager;
    private Sensor aSensor;
    private int i=0,gravityRate=5000;
    private float gravity[] = new float[3];
    private float mAccelCurrent,mAccelLast;

    public MainActivity() {
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        text_x = (TextView)findViewById(R.id.TextView01);
        text_y = (TextView)findViewById(R.id.TextView02);
        text_z = (TextView)findViewById(R.id.TextView03);
        Gtext = (TextView)findViewById(R.id.Gtext);

        aSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        aSensorManager.registerListener(this,
                aSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                gravityRate * 1000);

        //取得IMEI
        TelephonyManager tM=(TelephonyManager)this.getSystemService(this.TELEPHONY_SERVICE);
         imei = tM.getDeviceId();

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
// TODO Auto-generated method stub

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
// TODO Auto-generated method stub
        gravity[0] = event.values[0];
        gravity[1] = event.values[1];
        gravity[2] = event.values[2];
        text_x.setText("X = "+gravity[0]);
        text_y.setText("Y = "+gravity[1]);
        text_z.setText("Z = " + gravity[2]);
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt((double) (gravity[0]*gravity[0] + gravity[1]*gravity[1] + gravity[2]*gravity[2]));
        float delta = mAccelCurrent - mAccelLast;
        msg=""+delta;
        msgx=""+gravity[0];
        msgy=""+gravity[1];
        msgz=""+gravity[2];
        Thread x = new Thread(new sendPostRunnable(msgx,msgy,msgz,msg));
        x.start();

    }
    @Override
    protected void onPause()
    {
// TODO Auto-generated method stub
/* 取消註冊SensorEventListener */
        aSensorManager.unregisterListener(this);

        super.onPause();
    }

    protected void onResume() {

        aSensorManager.registerListener(this,
                aSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                gravityRate * 1000);

        super.onResume();
    }
    public void Oneclick(View v)//開關
    {
        i++;
        if(i>=2)
        {i=0;}
        if(i==1)
        {onPause();}
        else
        {onResume();}
    }



    private String uriAPI = "http://shankmc.no-ip.org:81/httpPostTest/gforce.php";
    /** 「要更新版面」的訊息代碼 */
    protected static final int REFRESH_DATA = 0x00000001;

    /** 建立UI Thread使用的Handler，來接收其他Thread來的訊息 */
    Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                // 顯示網路上抓取的資料
                case REFRESH_DATA:
                    String result = null;
                    if (msg.obj instanceof String)
                        result = (String) msg.obj;
                    if (result != null)
                        // 印出網路回傳的文字

                    break;
            }
        }
    };

    class sendPostRunnable implements Runnable
    {
        String strTxtx = null;
        String strTxty = null;
        String strTxtz = null;
        String strTxtg = null;


        // 建構子，設定要傳的字串
        public sendPostRunnable(String strTxtx,String strTxty,String strTxtz,String strTxtg)
        {
            this.strTxtx = strTxtx;
            this.strTxty = strTxty;
            this.strTxtz = strTxtz;
            this.strTxtg = strTxtg;

        }

        @Override
        public void run()
        {
            String result = sendPostDataToInternet(strTxtx, strTxty, strTxtz, strTxtg);
            mHandler.obtainMessage(REFRESH_DATA, result).sendToTarget();
        }

    }

    private String sendPostDataToInternet(String strTxtx,String strTxty,String strTxtz,String strTxtg)
    {

		/* 建立HTTP Post連線 */

        HttpPost httpRequest = new HttpPost(uriAPI);
		/*
		 * Post運作傳送變數必須用NameValuePair[]陣列儲存
		 */
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("X", strTxtx));
        params.add(new BasicNameValuePair("Y", strTxty));
        params.add(new BasicNameValuePair("Z", strTxtz));
        params.add(new BasicNameValuePair("G", strTxtz));
        params.add(new BasicNameValuePair("IMEI", imei));


        try

        {

			/* 發出HTTP request */

            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/* 取得HTTP response */
            HttpResponse httpResponse = new DefaultHttpClient()
                    .execute(httpRequest);
			/* 若狀態碼為200 ok */
            if (httpResponse.getStatusLine().getStatusCode() == 200)
            {
				/* 取出回應字串 */
                String strResult = EntityUtils.toString(httpResponse
                        .getEntity());
                // 回傳回應字串
                return strResult;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }



}