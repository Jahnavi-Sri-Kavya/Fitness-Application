package com.example.fitness;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;

import static java.lang.Math.sqrt;

public class ShowSteps extends AppCompatActivity implements SensorEventListener, StepListener{

    ImageButton i1;
    TextView phone_id,calorie;
    public Map times;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private int numSteps;
    String user_name,user_phone,height,weight,age,x,y,goal;
    int h,w,a,step,z;
    int calorieb,wex;
    private Button BtnStart,BtnStop;
    private TextView TvSteps;
    DatabaseReference reference,reference2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_steps);
        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        calorie = findViewById(R.id.calorie);
        TvSteps =findViewById(R.id.tv_steps);
        BtnStart = findViewById(R.id.btn_start);
        BtnStop = findViewById(R.id.btn_stop);
        phone_id=findViewById(R.id.phone_id);

        Intent intent=getIntent();
        user_phone=intent.getStringExtra("phoneno");
        user_name=intent.getStringExtra("name");
        phone_id.setText("Hello "+user_name+", Welcome Back");
        i1=findViewById(R.id.updateprfile);

        reference= FirebaseDatabase.getInstance().getReference("user").child(user_phone);
        reference2= FirebaseDatabase.getInstance().getReference("steps").child(user_phone);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass2 user=snapshot.getValue(UserHelperClass2.class);

                 height=user.Height;
                 weight=user.Weight;
                 age=user.Age;
                 goal=user.Goal;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PhoneFromDB=user_phone;
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                intent.putExtra("phoneno",PhoneFromDB);
                startActivity(intent);
            }
        });



        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try {
                    z=Integer.parseInt(goal);
                }
                catch(NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }
                numSteps = z;
                TvSteps.setText(goal);
                sensorManager.registerListener(ShowSteps.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

            }
        });

        BtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sensorManager.unregisterListener(ShowSteps.this);
                String noofsteps=TvSteps.getText().toString();
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                String date = format.format(new Date());
                DateFormat format2 = new SimpleDateFormat("HH:mm");
                String time=format2.format(new Date());
                DataPoint datapoint=new DataPoint(time,noofsteps);
                reference2.child(date).push().setValue(datapoint);
                try {
                   h = Integer.parseInt(height);
                    w = Integer.parseInt(weight);
                    a = Integer.parseInt(age);
                    z = Integer.parseInt(goal);
                    step=Integer.parseInt(TvSteps.getText().toString());
                    step=z-step;
                    calorieb+= (int) (w*a*step*12/Math.pow(h,2));
                    System.out.println(calorieb);
                    y=Integer.toString(calorieb);
                    calorie.setText(y+"kcal");

                } catch(NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }
            }

        });
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }
    @Override
    public void step(long timeNs) {

            if (numSteps!=0){
                numSteps--;
                x=String.valueOf(numSteps);
                TvSteps.setText(x);
            }
            else{
                Toast.makeText(this, "Hurrah! Daily Goal Reached!", Toast.LENGTH_SHORT).show();
                TvSteps.setText(goal);
            }

    }
}
