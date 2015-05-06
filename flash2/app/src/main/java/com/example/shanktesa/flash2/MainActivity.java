package com.example.shanktesa.flash2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {
    Button keep,stop,butstrt;
    //宣告 deviccmera.class
    deviccmera mcamera=new deviccmera();

    private EditText str,delytime;//str=使用者輸入的字串，delytime=間隔秒數
    private TextView show;//顯示字串轉2進位
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //開啟相機，手電筒
        mcamera.acquireCamera();
        mcamera.startPreview();

        keep=(Button)findViewById(R.id.keep);
        stop=(Button)findViewById(R.id.stop);
        butstrt=(Button)findViewById(R.id.start);
        keep.setOnClickListener(keeplight);
        stop.setOnClickListener(stopA);
        butstrt.setOnClickListener(start);

        str=(EditText) findViewById(R.id.str);
        delytime=(EditText) findViewById(R.id.delytime);
        show=(TextView) findViewById(R.id.show);
    }
    //連閃
    private Button.OnClickListener keeplight=new
            Button.OnClickListener(){
                public void onClick(View v)
                {
                    mcamera.chang(true);
                }
            };
    //結束
    private Button.OnClickListener stopA=new
            Button.OnClickListener(){
                public void onClick(View v)
                {
                    mcamera.releaseCamera();mcamera.acquireCamera();mcamera.startPreview();
                }

            };

    //字串閃燈
    private Button.OnClickListener start=new
            Button.OnClickListener(){
                public void onClick(View v)
                {

                    StringBuffer str1;
                    str1=new StringBuffer();
                    str1.append("1");
                    for(int i=0;i<str.getText().length();i++)
                    {
                        str1.append(binary(str.getText().charAt(i)));
                    }
                    show.setText(str1);
                    long blinkDelay=Long.parseLong(delytime.getText().toString());

                    String str = str1.toString();
                    mcamera.flashstr(str,blinkDelay);


                }
            };


    //字串進位轉2進位(支援各國語言!!)
    protected String binary(char number){
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




}
