package com.example.fitness;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StepsBarcharts extends AppCompatActivity {
    ImageButton i2;
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntriesArrayList;
    int xtime,ysteps;
    DatabaseReference reference;
    ArrayList<String> xval;
    String user_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_barcharts);
        i2=findViewById(R.id.back3);
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StepsBarcharts.this, ShowSteps.class);
                startActivity(intent);
            }
        });
        Intent intent=getIntent();
        user_phone=intent.getStringExtra("phoneno");

        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = format.format(new Date());

        reference= FirebaseDatabase.getInstance().getReference("steps");

        barChart = findViewById(R.id.idBarChart);

        // calling method to get bar entries.
        getBarEntries();

        barDataSet = new BarDataSet(barEntriesArrayList, "No.of Steps per Day");
        barData = new BarData(barDataSet);
        barChart.setData(barData);

        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barDataSet.notifyDataSetChanged();
        barChart.getDescription().setEnabled(false);

    }



    private void getBarEntries() {
        // creating a new array list

        barEntriesArrayList = new ArrayList<>();
        xval=new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChildren()){
                    for (DataSnapshot mydatasnapshot:snapshot.getChildren()){
                        DataPoint datapoint=mydatasnapshot.getValue(DataPoint.class);

                            try {
                                xtime=Integer.parseInt(datapoint.getXtime());
                                ysteps=Integer.parseInt(datapoint.getYstep());
                            }
                            catch(NumberFormatException nfe) {
                                System.out.println("Could not parse " + nfe);
                            }


                        barEntriesArrayList.add(new BarEntry(xtime,ysteps));

                       /* XAxis xAxis = barChart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(xval));*/

                    }
                }
                else{
                    barChart.clear();
                    barChart.invalidate();
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

    }
}