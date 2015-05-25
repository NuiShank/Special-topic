package com.loveyou.shank.httpsensor;


import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import java.util.ArrayList;
import java.util.List;

public class TestPedometer extends Activity implements SensorEventListener {
    private String msgs,imei;
    private TextView text_x;
    private TextView text_y;
    private TextView text_z;
    private TextView StepPedometer;
    private SensorManager aSensorManager;
    private int gravityRate=5000;
    private float gravity[] = new float[3];
    private float mAccelCurrent,mAccelLast;
    private ToggleButton toggleButton;//切換按鈕

    public static int CURRENT_SETP = 0;
    public static float SENSITIVITY = 10; // SENSITIVITY?敏度
    private float mLastValues[] = new float[3 * 2];
    private float mScale[] = new float[2];
    private float mYOffset;
    private static long end = 0;
    private static long start = 0;
    /**
     * 最后加速度方向
     */
    private float mLastDirections[] = new float[3 * 2];
    private float mLastExtremes[][] = { new float[3 * 2], new float[3 * 2] };
    private float mLastDiff[] = new float[3 * 2];
    private int mLastMatch = -1;

    int h = 480;



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.sensor);


        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        text_x = (TextView)findViewById(R.id.TextView01);
        text_y = (TextView)findViewById(R.id.TextView02);
        text_z = (TextView)findViewById(R.id.TextView03);
        StepPedometer = (TextView)findViewById(R.id.StepPedometer);


        aSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        aSensorManager.registerListener(this,
                aSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                gravityRate * 1000);

        //取得IMEI
        TelephonyManager tM=(TelephonyManager)this.getSystemService(this.TELEPHONY_SERVICE);
        imei = tM.getDeviceId();


        //切換按鈕執行程式
        toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {//開啟
                    onResume();

                } else {//關閉
                    CURRENT_SETP=0;
                    StepPedometer.setText("0");
                    onPause();
                }
            }
        });

        mYOffset = h * 0.5f;
        mScale[0] = -(h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
        mScale[1] = -(h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
// TODO Auto-generated method stub
    }
    @Override

    //??感器??到的?值?生?化?就??用??方法
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        synchronized (this) {
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                float vSum = 0;
                for (int i = 0; i < 3; i++) {
                    final float v = mYOffset + event.values[i] * mScale[1];
                    vSum += v;
                }
                int k = 0;
                float v = vSum / 3;

                float direction = (v > mLastValues[k] ? 1
                        : (v < mLastValues[k] ? -1 : 0));
                if (direction == -mLastDirections[k]) {
                    // Direction changed
                    int extType = (direction > 0 ? 0 : 1); // minumum or
                    // maximum?
                    mLastExtremes[extType][k] = mLastValues[k];
                    float diff = Math.abs(mLastExtremes[extType][k]
                            - mLastExtremes[1 - extType][k]);

                    if (diff > SENSITIVITY) {
                        boolean isAlmostAsLargeAsPrevious = diff > (mLastDiff[k] * 2 / 3);
                        boolean isPreviousLargeEnough = mLastDiff[k] > (diff / 3);
                        boolean isNotContra = (mLastMatch != 1 - extType);

                        if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough
                                && isNotContra) {
                            end = System.currentTimeMillis();
                            if (end - start > 500) {// 此?判??走了一步

                                CURRENT_SETP++;
                                mLastMatch = extType;
                                start = end;
                                StepPedometer.setText("" + CURRENT_SETP);
                                msgs=""+CURRENT_SETP;
                                Thread x = new Thread(new sendPostRunnable(msgs));
                                x.start();
                            }
                        } else {
                            mLastMatch = -1;
                        }
                    }
                    mLastDiff[k] = diff;
                }
                mLastDirections[k] = direction;
                mLastValues[k] = v;
            }

        }
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

    private String uriAPI = "http://shankmc.no-ip.org:81/sensor/Pedometer/Pedometer.php";
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
        String strStep = null;

        // 建構子，設定要傳的字串
        public sendPostRunnable(String strStep)
        {
            this.strStep = strStep;

        }

        @Override
        public void run()
        {
            String result = sendPostDataToInternet(strStep);
            mHandler.obtainMessage(REFRESH_DATA, result).sendToTarget();
        }

    }

    private String sendPostDataToInternet(String strStep)
    {

		/* 建立HTTP Post連線 */

        HttpPost httpRequest = new HttpPost(uriAPI);
		/*
		 * Post運作傳送變數必須用NameValuePair[]陣列儲存
		 */
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("Step", strStep));
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