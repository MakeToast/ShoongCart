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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import static android.content.Context.SENSOR_SERVICE;
import static com.example.ShoongCart.MainActivity.latitude;
import static com.example.ShoongCart.MainActivity.longitude;
import static com.example.ShoongCart.MainActivity.prLatitude;
import static com.example.ShoongCart.MainActivity.prLongitude;

public class DrawOnTop extends View implements SensorEventListener {

    double ex_latitude, ex_longitude;
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

    Bitmap horse, coupon;
    Building list[] = new Building[30];


    public DrawOnTop(Context context) {
        super(context);
        mContext = (MainActivity) context;
        initSensor(context);
        initBuilding();
        horse = BitmapFactory.decodeResource(getResources(), R.drawable.present);
        coupon = BitmapFactory.decodeResource(getResources(), R.drawable.coupon);

        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // TODO Auto-generated method stub
        Paint paint = new Paint();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(20);// 크기 10
        paint.setTextSize((int) (width / 25));

        ex_latitude = prLatitude;
        ex_longitude = prLongitude;

        //double distance = distance(latitude, longitude, prLatitude, prLongitude);
        //double de =  bearingP1toP2(latitude, longitude, prLatitude, prLatitude);

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

        if (degree > 45 && degree < 135) {
            degree = 1 - (degree - 45) / 90;
            ex_left = (int) (width * degree) - (120 - 4 * 10); //list[i].name.length() = 5 레지던스홀로 했을때
            ex_right = (int) (width * degree + 4 * 22);

            canvas.drawBitmap(horse, ex_left, height / 4, paint);
            canvas.drawText((int) dist + "m 앞으로", (int) (width * degree), (int) (height / 2.5), paint); // 텍스트 표시
            Log.d("distance", ": " + dist + "degree: " + degree);
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onDraw(canvas);


        for (int i = 0; i <= 26; i++) {
            dx = list[i].latitude - latitude;
            dy = list[i].longitude - longitude;
            rad = Math.atan2(dx, dy);
            degree = (rad * 180) / Math.PI;
            a = m_result_data[0] + 90;
            if (degree + a < 360) {
                degree += a;
            } else if (degree + a >= 360) {
                degree = degree + a - 360;
            }
            theta = longitude - list[i].longitude;
            dist = Math.sin(deg2rad(latitude)) * Math.sin(deg2rad(list[i].latitude)) + Math.cos(deg2rad(latitude))
                    * Math.cos(deg2rad(list[i].latitude)) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);

            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;    // 단위 mile 에서 km 변환.
            dist = dist * 1000.0;      // 단위  km 에서 m 로 변환

            if (degree > 45 && degree < 135 && dist < 50) {
                degree = 1 - (degree - 45) / 90;
                list[i].left = (int) (width * degree) - (120 - 4* 10);
                list[i].right = (int) (width * degree + 4 * 22);

                canvas.drawBitmap(coupon, list[i].left, height / 4, paint);
                canvas.drawText((int) dist + "m 앞으로", (int) (width * degree), (int) (height / 2.5), paint); // 텍스트 표시
            }
        }
        super.onDraw(canvas);
    }

    private void initBuilding() {
        for (int i = 0; i < list.length; i++) {
            list[i] = new Building();
        }
        list[0].name = "문화관";
        list[0].latitude = 37.496471;
        list[0].longitude = 126.954306;
        list[4].name = "테니스장";
        list[4].latitude = 37.496869;
        list[4].longitude = 126.954897;
        list[8].name = "경상관";
        list[8].latitude = 37.496494;
        list[8].longitude = 126.955171;
        list[12].name = "안익태기념관";
        list[12].latitude = 37.495730;
        list[12].longitude = 126.955035;
        list[16].name = "백마관";
        list[16].latitude = 37.497811;
        list[16].longitude = 126.956151;
        list[20].name = "대운동장";
        list[20].latitude = 37.497258;
        list[20].longitude = 126.956124;
        list[24].name = "베어드홀";
        list[24].latitude = 37.496479;
        list[24].longitude = 126.956290;
        list[1].name = "형남공학관";
        list[1].latitude = 37.495755;
        list[1].longitude = 126.956167;
        list[5].name = "교육관";
        list[5].latitude = 37.497806;
        list[5].longitude = 126.956832;
        list[9].name = "학생회관";
        list[9].latitude = 37.497065;
        list[9].longitude = 126.956902;
        list[13].name = "벤처중소기업센터";
        list[13].latitude = 37.497533;
        list[13].longitude = 126.957503;
        list[17].name = "진리관";
        list[17].latitude = 37.496890;
        list[17].longitude = 126.957444;
        list[21].name = "한국기독교박물관";
        list[21].latitude = 37.495549;
        list[21].longitude = 126.957026;
        list[25].name = "조만식기념관";
        list[25].latitude = 37.497171;
        list[25].longitude = 126.958399;
        list[2].name = "한경직기념관";
        list[2].latitude = 37.495586;
        list[2].longitude = 126.957594;
        list[6].name = "웨스트민스터홀";
        list[6].latitude = 37.496803;
        list[6].longitude = 126.958442;
        list[10].name = "신양관";
        list[10].latitude = 37.496382;
        list[10].longitude = 126.958227;
        list[14].name = "중앙도서관";
        list[14].latitude = 37.496242;
        list[14].longitude = 126.958603;
        list[18].name = "미래관";
        list[18].latitude = 37.495578;
        list[18].longitude = 126.958496;
        list[22].name = "농구장";
        list[22].latitude = 37.495374;
        list[22].longitude = 126.958432;
        list[26].name = "연구관";
        list[26].latitude = 37.496264;
        list[26].longitude = 126.959210;
        list[3].name = "전산관";
        list[3].latitude = 37.495451;
        list[3].longitude = 126.959484;
        list[7].name = "창의관";
        list[7].latitude = 37.494702;
        list[7].longitude = 126.959269;
        list[11].name = "커밍홀";
        list[11].latitude = 37.495906;
        list[11].longitude = 126.959902;
        list[15].name = "정보과학관";
        list[15].latitude = 37.494557;
        list[15].longitude = 126.959677;
        list[19].name = "글로벌브레인홀";
        list[19].latitude = 37.495706;
        list[19].longitude = 126.960444;
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

    public double distance(double P1_latitude, double P1_longitude,
                           double P2_latitude, double P2_longitude) {
        if ((P1_latitude == P2_latitude) && (P1_longitude == P2_longitude)) {
            return 0;
        }
        double e10 = P1_latitude * Math.PI / 180;
        double e11 = P1_longitude * Math.PI / 180;
        double e12 = P2_latitude * Math.PI / 180;
        double e13 = P2_longitude * Math.PI / 180;
  /* 타원체 GRS80 */
        double c16 = 6356752.314140910;
        double c15 = 6378137.000000000;
        double c17 = 0.0033528107;
        double f15 = c17 + c17 * c17;
        double f16 = f15 / 2;
        double f17 = c17 * c17 / 2;
        double f18 = c17 * c17 / 8;
        double f19 = c17 * c17 / 16;
        double c18 = e13 - e11;
        double c20 = (1 - c17) * Math.tan(e10);
        double c21 = Math.atan(c20);
        double c22 = Math.sin(c21);
        double c23 = Math.cos(c21);
        double c24 = (1 - c17) * Math.tan(e12);
        double c25 = Math.atan(c24);
        double c26 = Math.sin(c25);
        double c27 = Math.cos(c25);
        double c29 = c18;
        double c31 = (c27 * Math.sin(c29) * c27 * Math.sin(c29))
                + (c23 * c26 - c22 * c27 * Math.cos(c29))
                * (c23 * c26 - c22 * c27 * Math.cos(c29));
        double c33 = (c22 * c26) + (c23 * c27 * Math.cos(c29));
        double c35 = Math.sqrt(c31) / c33;
        double c36 = Math.atan(c35);
        double c38 = 0;
        if (c31 == 0) {
            c38 = 0;
        } else {
            c38 = c23 * c27 * Math.sin(c29) / Math.sqrt(c31);
        }
        double c40 = 0;
        if ((Math.cos(Math.asin(c38)) * Math.cos(Math.asin(c38))) == 0) {
            c40 = 0;
        } else {
            c40 = c33 - 2 * c22 * c26
                    / (Math.cos(Math.asin(c38)) * Math.cos(Math.asin(c38)));
        }
        double c41 = Math.cos(Math.asin(c38)) * Math.cos(Math.asin(c38))
                * (c15 * c15 - c16 * c16) / (c16 * c16);
        double c43 = 1 + c41 / 16384
                * (4096 + c41 * (-768 + c41 * (320 - 175 * c41)));
        double c45 = c41 / 1024 * (256 + c41 * (-128 + c41 * (74 - 47 * c41)));
        double c47 = c45
                * Math.sqrt(c31)
                * (c40 + c45
                / 4
                * (c33 * (-1 + 2 * c40 * c40) - c45 / 6 * c40
                * (-3 + 4 * c31) * (-3 + 4 * c40 * c40)));
        double c50 = c17
                / 16
                * Math.cos(Math.asin(c38))
                * Math.cos(Math.asin(c38))
                * (4 + c17
                * (4 - 3 * Math.cos(Math.asin(c38))
                * Math.cos(Math.asin(c38))));
        double c52 = c18
                + (1 - c50)
                * c17
                * c38
                * (Math.acos(c33) + c50 * Math.sin(Math.acos(c33))
                * (c40 + c50 * c33 * (-1 + 2 * c40 * c40)));
        double c54 = c16 * c43 * (Math.atan(c35) - c47);
        // return distance in meter
        return c54;
    }

    //방위각 구하는 부분
    public short bearingP1toP2(double P1_latitude, double P1_longitude,
                               double P2_latitude, double P2_longitude) {
        // 현재 위치 : 위도나 경도는 지구 중심을 기반으로 하는 각도이기 때문에
        //라디안 각도로 변환한다.
        double Cur_Lat_radian = P1_latitude * (3.141592 / 180);
        double Cur_Lon_radian = P1_longitude * (3.141592 / 180);
        // 목표 위치 : 위도나 경도는 지구 중심을 기반으로 하는 각도이기 때문에
        // 라디안 각도로 변환한다.
        double Dest_Lat_radian = P2_latitude * (3.141592 / 180);
        double Dest_Lon_radian = P2_longitude * (3.141592 / 180);
        // radian distance
        double radian_distance = 0;
        radian_distance = Math.acos(Math.sin(Cur_Lat_radian)
                * Math.sin(Dest_Lat_radian) + Math.cos(Cur_Lat_radian)
                * Math.cos(Dest_Lat_radian)
                * Math.cos(Cur_Lon_radian - Dest_Lon_radian));
        // 목적지 이동 방향을 구한다.(현재 좌표에서 다음 좌표로 이동하기 위해서는
        //방향을 설정해야 한다. 라디안값이다.
        double radian_bearing = Math.acos((Math.sin(Dest_Lat_radian) - Math
                .sin(Cur_Lat_radian)
                * Math.cos(radian_distance))
                / (Math.cos(Cur_Lat_radian) * Math.sin(radian_distance)));
        // acos의 인수로 주어지는 x는 360분법의 각도가 아닌 radian(호도)값이다.
        double true_bearing = 0;
        if (Math.sin(Dest_Lon_radian - Cur_Lon_radian) < 0) {
            true_bearing = radian_bearing * (180 / 3.141592);
            true_bearing = 360 - true_bearing;
        } else {
            true_bearing = radian_bearing * (180 / 3.141592);
        }
        return (short) true_bearing;
    }
}