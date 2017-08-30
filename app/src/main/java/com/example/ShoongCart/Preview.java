package com.example.ShoongCart;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


class Preview extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder mHolder;
    Camera mCamera = null;

    public void close()
    {
        if( mCamera != null){
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;

        }
    }

    public Preview(Context context, AttributeSet attrs) {
        super(context, attrs);

        try{
            if (mCamera == null) {
                mCamera = Camera.open();
                mCamera.setDisplayOrientation(90);
            }

            mHolder = getHolder();

            mHolder.addCallback(this);
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }catch (Exception e) {

        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }



    public void surfaceCreated(SurfaceHolder holder) {

        try{



            if( mCamera == null){
                mCamera = Camera.open();
                mCamera.setDisplayOrientation(90);
            }


            if (mCamera != null)
            {
                try {

                    mCamera.setPreviewDisplay(holder);
                    Camera.Parameters parameters = mCamera.getParameters();

                    mCamera.setParameters(parameters);
                    mCamera.startPreview();
                }

                catch (IOException exception) {
                    Log.e("Error", "exception:surfaceCreated Camera Open ");
                    mCamera.release();
                    mCamera = null;
                    // TODO: add more exception handling logic here
                }
            }

        }
        catch (Exception e) {

            Log.e("camera open error","error");

        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if(mCamera!=null ){

            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;

        }
    }



    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (mHolder.getSurface() == null) {
            return;
        }

// 작업을 위해 잠시 멈춘다
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // 에러가 나더라도 무시한다.
        }

// 카메라 설정을 다시 한다.
        Camera.Parameters parameters = mCamera.getParameters();
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
        mCamera.setParameters(parameters);

// View 를 재생성한다.
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Exception e) {
        }

    }

}
