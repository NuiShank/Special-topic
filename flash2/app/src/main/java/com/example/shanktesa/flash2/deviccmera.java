package com.example.shanktesa.flash2;

import android.hardware.Camera;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.Log;

import java.util.List;


public class deviccmera {

    private static final String TAG = deviccmera.class.getSimpleName();

    private boolean mIsFlashlightOn;
    //宣告相機
    private Camera mCamera ;
    private boolean mIsPreviewStarted;
    private Handler mHandler = new Handler();
    private boolean mStateOn;   // only used for strobing effect

    //連閃
    private final Runnable mStrober = new Runnable() {
        @Override
        public void run() {
            if (mIsFlashlightOn) {
                if (mStateOn) {
                    // turn state on
                    toggleTorch(mCamera,true);
                    mStateOn = false;
                    mHandler.postDelayed(this, 20);
                } else {
                    // turn state off
                    toggleTorch(mCamera,false);
                    mStateOn = true;
                    mHandler.postDelayed(this, 30);
                }
            } else {
                toggleTorch(mCamera,false);
            }
        }
    };




    //判斷手電筒開關
    public boolean toggleTorch(Camera camera, boolean on) {
        setFlashMode(camera, (on ? Camera.Parameters.FLASH_MODE_TORCH : Camera.Parameters.FLASH_MODE_OFF));
        return true;
    }

    private void setFlashMode(Camera camera, String mode) {
        //Log.v(TAG, "Setting flash mode: " + mode);
        Camera.Parameters params = camera.getParameters();
        params.setFlashMode(mode);
        camera.setParameters(params);
    }


    //是否支援手電筒
    private boolean supportsTorchMode() {
        //相機設備關掉
        if (mCamera == null)
            return false;

        Camera.Parameters params = mCamera.getParameters();
        List<String> flashModes = params.getSupportedFlashModes();
        return (flashModes != null) &&
                (flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH));
    }

   //連閃
    public boolean chang(boolean on)
    {
        assert (mCamera != null);
        //判斷是否有相機
        if (mCamera == null) {
            Log.wtf(TAG, "toggling with NULL camera!");
            return false;
        }

        //判斷是否有手電筒
        if (!supportsTorchMode()) {

            Log.d(TAG, "This device does not support 'torch' mode");
            return false;
        }

            mIsFlashlightOn=true;
            mStateOn=true;
            mHandler.post(mStrober);
        return on;

    }

    //啟動相機
    public boolean acquireCamera() {
        Log.v(TAG, "Acquiring camera...");
        assert (mCamera == null);
        try {
            mCamera = Camera.open();
        }
        catch (RuntimeException e) {
            Log.e(TAG, "Failed to open camera: " + e.getLocalizedMessage());
        }

        return (mCamera != null);
    }

    public void startPreview() {
        if (!mIsPreviewStarted && mCamera != null) {
            Log.v(TAG, "Starting preview...");
            mCamera.startPreview();
            mIsPreviewStarted = true;
        }
    }


    //關閉相機
    public void releaseCamera() {
        if (mCamera != null) {
            Log.v(TAG, "Releasing camera...");
            if (mIsFlashlightOn) {

                toggleTorch(mCamera,false);
                mIsFlashlightOn=false;
            }
            stopPreview();
            mCamera.release();
            mCamera = null;
            mHandler.removeCallbacks(mStrober);
        }
    }

    public void stopPreview() {
        if (mIsPreviewStarted && mCamera != null) {
            Log.v(TAG, "Stopping preview...");
            mCamera.stopPreview();
            mIsPreviewStarted = false;
        }
    }



    //字串手電筒
    public void flashstr(String str, long time)
    {


        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                turnA();
            }
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            toggleTorch(mCamera,false);
        }


    }

    //縮短亮燈時間
    protected void turnA()
    {
        for(int i=0;i<2;i++)
        {
            //打開
            if(i==0)
            {toggleTorch(mCamera,true);}
            //關閉
            else
            { toggleTorch(mCamera,false);}

            try {
                //先打開20毫秒後關閉
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}

