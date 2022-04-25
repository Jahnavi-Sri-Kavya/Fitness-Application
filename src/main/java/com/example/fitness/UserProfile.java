package com.example.fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {
    TextInputLayout goal,height,weight,age;
    TextView t1;
    Button update;
    RadioGroup rg1,rg2;
    RadioButton m,f,l,mo,a;
    String user_name,user_Username,user_email,user_phone,user_pass,gender,Active;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        rg1=findViewById(R.id.radiogrp);
        rg2=findViewById(R.id.radiogrp2);
        goal=findViewById(R.id.goal);
        height=findViewById(R.id.Height_profile);
        weight=findViewById(R.id.weight_profile);
        age=findViewById(R.id.Age_profile);
        update=findViewById(R.id.update);
        m=findViewById(R.id.radio_pirates);
        f=findViewById(R.id.radio_ninjas);
        l=findViewById(R.id.radio_pirates2);
        mo=findViewById(R.id.radio_ninjas2);
        a=findViewById(R.id.radio_ninjas3);
        t1=findViewById(R.id.welcome);
        showAllUserData();
        reference=FirebaseDatabase.getInstance().getReference("user");

    }

    private void showAllUserData() {
        Intent intent=getIntent();
        user_name = intent.getStringExtra("name");
        user_Username=intent.getStringExtra("username");
        user_phone=intent.getStringExtra("phoneno");
        user_email=intent.getStringExtra("email");
        user_pass=intent.getStringExtra("password");
        t1.setText("Welcome "+user_name+",");
    }

    public void gender(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_pirates:
                if (checked)
                    gender="Male";
                    break;
            case R.id.radio_ninjas:
                if (checked)
                    gender="Female";
                    break;
        }
    }


    public void activityStatus(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_pirates2:
                if (checked)
                    Active="Light";
                break;
            case R.id.radio_ninjas2:
                if (checked)
                    Active="Moderate";
                break;
            case R.id.radio_ninjas3:
                if (checked)
                    Active="Very";
                break;
        }
    }

    private Boolean validateDailysteps(){
        String val=goal.getEditText().getText().toString();
        if (val.isEmpty()){
            goal.setError("Field cannot be empty");
            return false;
        }
        else if(Integer.parseInt(val)>50000){
            goal.setError("Sorry! Max goal is 50000 steps");
            return false;
        }
        else{
            goal.setError(null);
            return true;
        }
    }
    private Boolean validateheight(){
        String val=height.getEditText().getText().toString();
        if (val.isEmpty()){
            height.setError("Field cannot be empty");
            return false;
        }
        else{
            height.setError(null);
            height.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateweight(){
        String val=weight.getEditText().getText().toString();
        if (val.isEmpty()){
            weight.setError("Field cannot be empty");
            return false;
        }
        else{
            weight.setError(null);
            return true;
        }
    }
    private Boolean validateage(){
        String val=age.getEditText().getText().toString();
        if (val.isEmpty()){
            age.setError("Field cannot be empty");
            return false;
        }
        else{
            age.setError(null);
            return true;
        }
    }

    public void update(View view) {

        if(!validateDailysteps()| !validateheight() | !validateweight() | !validateage())
        {
            return ;
        }

        String Height=height.getEditText().getText().toString();
        String Weight=weight.getEditText().getText().toString();
        String Age=age.getEditText().getText().toString();
        String Name=user_name;
        String Username=user_Username;
        String Email=user_email;
        String PhoneNumber=user_phone;
        String Password=user_pass;
        String Gender=gender;
        String Actives=Active;
        String Goal=goal.getEditText().getText().toString();
        UserHelperClass2 helperClass= new UserHelperClass2(Name,Username,Email,PhoneNumber,Password,Height,Weight,Age,Gender,Actives,Goal);
        reference.child(PhoneNumber).setValue(helperClass);
        String userEntereduser=user_phone;
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                     @Override
                                                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                         String PhoneFromDB=snapshot.child(userEntereduser).child("phoneNo").getValue(String.class);
                                                         String nameFromDB=snapshot.child(userEntereduser).child("name").getValue(String.class);
                                                         String goal=snapshot.child(userEntereduser).child("goal").getValue(String.class);
                                                         Intent intent = new Intent(getApplicationContext(), ShowSteps.class);
                                                         intent.putExtra("name",nameFromDB);
                                                         intent.putExtra("phoneno",PhoneFromDB);
                                                         intent.putExtra("goal",goal);
                                                         startActivity(intent);
                                                     }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}