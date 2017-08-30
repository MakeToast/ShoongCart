package com.example.ShoongCart;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;


import static android.content.Context.SENSOR_SERVICE;
import static com.example.ShoongCart.MainActivity.latitude;
import static com.example.ShoongCart.MainActivity.longitude;

public class DrawOnTop extends View implements SensorEventListener {

    final double ex_latitude = 37.495055;
    final double ex_longitude = 126.960616;
    int ex_left, ex_right;

    // 센서 관련 객체
    SensorManager m_sensor_manager;
    int m_acc_sensor, m_mag_sensor;
    double rad;
    double dx;
    double dy;
    double degree;
    private MainActivity mContext;

    DisplayMetrics dm = getContext().getApplicationContext().getResources().getDisplayMetrics();
    int width = dm.widthPixels;
    int height = dm.heightPixels;

    // 데이터를 저장할 변수들
    float[] m_acc_data = null, m_mag_data = null;
    float[] m_rotation = new float[9];
    float[] m_result_data = new float[3];

    Bitmap horse;


    public DrawOnTop(Context context) {
        super(context);
        mContext = (MainActivity) context;
        initSensor(context);
        horse = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // TODO Auto-generated method stub
        Paint paint = new Paint();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(20);// 크기 10
        paint.setTextSize((int)(width/25));

            dx = ex_latitude - latitude;
            dy = ex_longitude - longitude;
            rad = Math.atan2(dx, dy);
            degree = (rad * 180) / Math.PI;
            double a = m_result_data[0] + 90;
            if (degree + a < 360) {
                degree += a;
            } else if (degree + a >= 360) {
                degree = degree + a - 360;
            }
            double theta, dist;
            theta = longitude - ex_longitude;
            dist = Math.sin(deg2rad(latitude)) * Math.sin(deg2rad(ex_latitude)) + Math.cos(deg2rad(latitude))
                    * Math.cos(deg2rad(ex_latitude)) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);

            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;    // 단위 mile 에서 km 변환.
            dist = dist * 1000.0;      // 단위  km 에서 m 로 변환

            if (degree > 45 && degree < 135 && dist<100) {
                degree = 1 - (degree - 45) / 90;
                ex_left = (int) (width * degree)-(120-5*10); //list[i].name.length() = 5 레지던스홀로 했을때
                ex_right = (int) (width * degree + 5* 22);

                canvas.drawBitmap(horse, ex_left, height/4, paint);
                canvas.drawText( " 초코우유 (거리는 : " + (int) dist + "m)", (int) (width * degree), (int)(height/1.6), paint); // 텍스트 표시
            }

        super.onDraw(canvas);
    }

    private void initSensor(Context context) {
        // 시스템서비스로부터 SensorManager 객체를 얻는다.
        m_sensor_manager = (SensorManager) context.getSystemService(SENSOR_SERVICE);

        // SensorManager 를 이용해서 가속센서와 자기장 센서 객체를 얻는다.
        m_acc_sensor = Sensor.TYPE_ACCELEROMETER;
        m_mag_sensor = Sensor.TYPE_MAGNETIC_FIELD;

        // 센서 값을 이 컨텍스트에서 받아볼 수 있도록 리스너를 등록한다.
        m_sensor_manager.registerListener(this, m_sensor_manager.getDefaultSensor(m_acc_sensor), SensorManager.SENSOR_DELAY_UI);
        m_sensor_manager.registerListener(this, m_sensor_manager.getDefaultSensor(m_mag_sensor), SensorManager.SENSOR_DELAY_UI);
    }

    public void viewDestroy() {
        m_sensor_manager.unregisterListener(this);

    }

    // 정확도 변경시 호출되는 메소드. 센서의 경우 거의 호출되지 않는다.
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // 가속 센서가 전달한 데이터인 경우
            // 수치 데이터를 복사한다.
            m_acc_data = event.values.clone();
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            // 자기장 센서가 전달한 데이터인 경우
            // 수치 데이터를 복사한다.
            m_mag_data = event.values.clone();
        }

        // 데이터가 존재하는 경우
        if (m_acc_data != null && m_mag_data != null) {
            // 가속 데이터와 자기장 데이터로 회전 매트릭스를 얻는다.
            SensorManager.getRotationMatrix(m_rotation, null, m_acc_data, m_mag_data);
            // 회전 매트릭스로 방향 데이터를 얻는다.
            SensorManager.getOrientation(m_rotation, m_result_data);

            String str;
            // Radian 값을 Degree 값으로 변환한다.
            m_result_data[0] = (float) Math.toDegrees(m_result_data[0]);

            // 0 이하의 값인 경우 360을 더한다.
            if (m_result_data[0] < 0) m_result_data[0] += 360;

            invalidate();
        }
    }

    // 주어진 도(degree) 값을 라디언으로 변환
    private double deg2rad(double deg) {
        return (double) (deg * Math.PI / (double) 180d);
    }

    // 주어진 라디언(radian) 값을 도(degree) 값으로 변환
    private double rad2deg(double rad) {
        return (double) (rad * (double) 180d / Math.PI);
    }

//    public boolean onTouchEvent(MotionEvent event) {
//        // TODO Auto-generated method stub
//
//        float X, Y;
//        X = event.getX();
//        Y = event.getY();
//
//        for (int i = 0; i <= 26; i++)
//            if (X >= list[i].left
//                    && X <= list[i].right ) {
//                Intent intent = new Intent(mContext, Inform.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("index",i);
//                mContext.startActivity(intent);
//
//                break;
//            }
//        return super.onTouchEvent(event);
//    }


}