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
    private ToggleButton toggleButton;//�������s

    public static int CURRENT_SETP = 0;
    public static float SENSITIVITY = 10; // SENSITIVITY?�ӫ�
    private float mLastValues[] = new float[3 * 2];
    private float mScale[] = new float[2];
    private float mYOffset;
    private static long end = 0;
    private static long start = 0;
    /**
     * �̦Z�[�t�פ�V
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

        //���oIMEI
        TelephonyManager tM=(TelephonyManager)this.getSystemService(this.TELEPHONY_SERVICE);
        imei = tM.getDeviceId();


        //�������s����{��
        toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {//�}��
                    onResume();

                } else {//����
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

    //??�P��??�쪺?��?��?��?�N??��??��k
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
                            if (end - start > 500) {// ��?�P??���F�@�B

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
/* �������USensorEventListener */
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
    /** �u�n��s�����v���T���N�X */
    protected static final int REFRESH_DATA = 0x00000001;

    /** �إ�UI Thread�ϥΪ�Handler�A�ӱ�����LThread�Ӫ��T�� */
    Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                // ��ܺ����W��������
                case REFRESH_DATA:
                    String result = null;
                    if (msg.obj instanceof String)
                        result = (String) msg.obj;
                    if (result != null)
                        // �L�X�����^�Ǫ���r

                        break;
            }
        }
    };

    class sendPostRunnable implements Runnable
    {
        String strStep = null;

        // �غc�l�A�]�w�n�Ǫ��r��
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

		/* �إ�HTTP Post�s�u */

        HttpPost httpRequest = new HttpPost(uriAPI);
		/*
		 * Post�B�@�ǰe�ܼƥ�����NameValuePair[]�}�C�x�s
		 */
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("Step", strStep));
        params.add(new BasicNameValuePair("IMEI", imei));


        try

        {

			/* �o�XHTTP request */
            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/* ���oHTTP response */
            HttpResponse httpResponse = new DefaultHttpClient()
                    .execute(httpRequest);
			/* �Y���A�X��200 ok */
            if (httpResponse.getStatusLine().getStatusCode() == 200)
            {
				/* ���X�^���r�� */
                String strResult = EntityUtils.toString(httpResponse
                        .getEntity());
                // �^�Ǧ^���r��
                return strResult;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }



}