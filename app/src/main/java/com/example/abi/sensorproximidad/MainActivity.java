package com.example.abi.sensorproximidad;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity  {
//   Creamos una variable de tipo sensorManager para poder hacer la comunicacion con nuestro dispositivo movil
    SensorManager sensorManager;
//    Creamos una variable y es la que representa nuestro sensor
    Sensor sensor;
//   Nos avisara cuando algo se este aproximando a nuestro dispositivo movil
    SensorEventListener sensorEventListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        cremos nuestra instancia
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        Mandamos a llamar nuestra variable y agregamos el tipo de sensor que utilizamremos en este caso es Aproximacion
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
//        Ahora verificaremos si el dispositivo movil cuenta oon este sensor y si no lo hay se finaliza nuestro activity
        if (sensor == null)
            finish();
//        Agregamos nuestro evento
        sensorEventListener = new SensorEventListener() {
            @Override
//            Este metodo detecta cuando los valores del sensor cambian
            public void onSensorChanged(SensorEvent sensorEvent) {
//                verificaremos la distancia del sensor sea menor que al rango maximo
                if(sensorEvent.values[0]<sensor.getMaximumRange()){
//                    si esto se cumple estamos dentro del rango del sensor cambiara a color rojo
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
//                    de lo contraio cambiara de color verde
                }else {
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        start();
    }
//    Creamos un metodo para inicar
//    Mandamos a llamar a nuestro sensorManager agregamos el tiempo de respuesta en microsegundos
    public void start()
    {
        sensorManager.registerListener(sensorEventListener,sensor,2000*1000);
    }

//Creamos el metodo onResume  y inicamos nuevamente con star
    @Override
    protected void onResume() {
        start();

        super.onResume();
    }
//creamos un metodo onPause y mandamos a llamar a stop
    @Override
    protected void onPause() {
        stop();
        super.onPause();

    }
    //    Creamos un metodo para detener
//    mandamos a llamar a nuestro sensoManager

    public void stop() {
        sensorManager.unregisterListener(sensorEventListener);

    }
}
