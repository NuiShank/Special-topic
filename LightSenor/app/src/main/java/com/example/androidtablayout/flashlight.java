package com.example.androidtablayout;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;


import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import android.widget.TextView;

public class flashlight extends Activity {


    private Camera camera;

    private boolean hasFlash;
    Parameters params;
    private String second="50";
    private  EditText pass,se;
    private  TextView t1,show;
    private StringBuffer str;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flahlight);

        // flash switch button


		/*
		 * First check if device is supporting flashlight or not
		 */
        hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!hasFlash) {
            // device doesn't support flash
            // Show alert message and close the application
            AlertDialog alert = new AlertDialog.Builder(flashlight.this)
                    .create();
            alert.setTitle("Error");
            alert.setMessage("不支援手電筒");
            alert.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // closing the application

                }
            });
            alert.show();
            return;
        }
        pass=(EditText) findViewById(R.id.editText1);
        se=(EditText) findViewById(R.id.editText2);
        t1=(TextView) findViewById(R.id.textView2);
        show=(TextView) findViewById(R.id.textView4);
        show.setMovementMethod(new ScrollingMovementMethod());


        getCamera();


    }


    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();

            } catch (RuntimeException e) {
                Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
            }
        }
    }


    private void turnOnFlash() {



            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();





    }


    private void turnOffFlash() {



            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();




    }




    public void blind(View v)
    {
        str=new StringBuffer();
        str.append("1");
        for(int i=0;i<pass.getText().length();i++)
        {
            str.append(binary(pass.getText().charAt(i)));
        }
        show.setText(str);
        second=se.getText().toString();

        long blinkDelay=Long.parseLong(second); //Delay in ms
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                turnA();
            }
            try {
                Thread.sleep(blinkDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        turnOffFlash();

    }

    public void end(View v)
    {

        turnOffFlash();

    }

    public String binary(char number){//10進位轉2進位
        String binary;
        binary= Integer.toBinaryString(number);
        while(true)
        {
            if(binary.length()<8)
            {
                binary="0"+binary;
            }
            else
            {return binary;}
        }

    }





    public void turnA()
    {

        for (int i = 0; i < 2; i++) {
            if (i==1) {
                turnOffFlash();
            } else {

                turnOnFlash();
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}