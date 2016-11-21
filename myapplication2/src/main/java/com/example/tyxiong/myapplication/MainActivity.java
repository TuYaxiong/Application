package com.example.tyxiong.myapplication;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

/*传感器:加速度传感器 方向传感器 陀螺/磁场/重力/线性/温度/光/压力/心率/
*
*  为指定传感器注册监听器即可,当环境发生改变时,android系统通过传感器获取外部数据,将数据传给监听方法..
*  使用3步:
*   1 通过getSystemService() SensorManager对象
*   2 实例方法getDefaultSensor(int type)获取指定传感器
*   3 同样SensorManager方法registerListener()方法为其注册监听
*       3参数listener监听器实现方法onSensorChanged()值变化 onAccuracyChanged()精度变化
*           sensor被注册监听器的传感器
*           rate获取环境数据速率 SENSOR_DELAY_UI/NORMAL/GAME/FASTEST
*
* */
public class MainActivity extends Activity {

    StringBuilder sb;
    TextView showx;
    TextView showy;
    TextView showz;

    SensorManager systemService;
    private Sensor sensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showx = (TextView) findViewById(R.id.showx);
        showy = (TextView) findViewById(R.id.showy);
        showz = (TextView) findViewById(R.id.showz);
        systemService = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = systemService.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


    }
    @Override
    protected void onResume() {

        super.onResume();
        systemService.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                sb = new StringBuilder();
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER: {              //加速度传感器 xyz轴加速度
                        float values[] = event.values;
                        showx.setText(String.valueOf(values[0]));
                        showy.setText(String.valueOf(values[1]));
                        showz.setText(String.valueOf(values[2]));

                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        
        super.onPause();//可以在暂停时将监听器取消,耗电...
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}






