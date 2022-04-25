package com.example.fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {
    TextInputLayout fullname,email,height,weight,usernameLabel,age,password,phno,goal1;
    TextView t1,t2;
    Button update;
    String user_name,user_Username,user_gender,user_phone,user_goal,user_height,user_weight,user_age,Active;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        fullname=findViewById(R.id.full_name_dash);
        usernameLabel=findViewById(R.id.user_name_dash);
        email=findViewById(R.id.email_dash);
        password=findViewById(R.id.password_dash);
        phno=findViewById(R.id.phone_dash);
        height=findViewById(R.id.Height_dash);
        weight=findViewById(R.id.weight_dash);
        age=findViewById(R.id.Age_dash);
        update=findViewById(R.id.update_dash);
        goal1=findViewById(R.id.goal1);
        t1=findViewById(R.id.genderview);
        t2=findViewById(R.id.Active);

        Intent intent=getIntent();
        user_phone=intent.getStringExtra("phoneno");
        phno.getEditText().setText(user_phone);

        reference=FirebaseDatabase.getInstance().getReference("user").child(user_phone);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass2 user=snapshot.getValue(UserHelperClass2.class);
                String UsernameFromDB=user.username;
                String nameFromDB=user.name;
                String EmailFromDB=user.email;
                String passwordFromDB=user.password;
                String HeightFromDB=user.Height;
                String WeightFromDB=user.Weight;
                String AgeFromDB=user.Age;
                String GenderFromDB=user.Gender;
                String ActiveFromDB=user.Active;
                String GoalFromDB=user.Goal;

                fullname.getEditText().setText(nameFromDB);
                usernameLabel.getEditText().setText(UsernameFromDB);
                email.getEditText().setText(EmailFromDB);
                password.getEditText().setText(passwordFromDB);
                height.getEditText().setText(HeightFromDB);
                weight.getEditText().setText(WeightFromDB);
                age.getEditText().setText(AgeFromDB);
                goal1.getEditText().setText(GoalFromDB);
                t1.setText("Gender: "+GenderFromDB);
                t2.setText("Activity Level: "+ActiveFromDB);
                user_gender=GenderFromDB;


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void activityStatus(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_1:
                if (checked)
                    Active="Light";
                break;
            case R.id.radio_2:
                if (checked)
                    Active="Moderate";
                break;
            case R.id.radio_3:
                if (checked)
                    Active="Very";
                break;
        }
    }
    private Boolean validateName(){
        String val=fullname.getEditText().getText().toString();
        if (val.isEmpty()){
            fullname.setError("Field cannot be empty");
            return false;
        }
        else{
            fullname.setError(null);
            return true;
        }
    }
    private Boolean validateUserName(){
        String val=usernameLabel.getEditText().getText().toString();
        String noWhiteSpace="\\A\\w{4,20}\\z";
        if (val.isEmpty()){
            usernameLabel.setError("Field cannot be empty");
            return false;
        }
        else if(val.length()>15){
            usernameLabel.setError("username too long");
            return false;
        }
        else if (!val.matches(noWhiteSpace)){
            usernameLabel.setError("White Spaces are not allowed");
            return false;
        }
        else{
            usernameLabel.setError(null);
            usernameLabel.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateemail(){
        String val=email.getEditText().getText().toString();
        String emailp="[a-zA-Z0-9,_.]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()){
            email.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(emailp)){
            email.setError("Invalid Email Address");
            return false;
        }
        else{
            email.setError(null);
            return true;
        }
    }
    private Boolean validatePhoneNo(){
        String val=phno.getEditText().getText().toString();
        if (val.isEmpty()){
            phno.setError("Field cannot be empty");
            return false;
        }
        else{
            phno.setError(null);
            return true;
        }
    }
    private Boolean validatePassword(){
        String val=password.getEditText().getText().toString();
        String passwordval="^"+"(?=.*[a-zA-Z])"+"(?=.*[@#$%^&+=])"+"(?=\\S+$)"+".{4,}"+"$";
        if (val.isEmpty()){
            password.setError("Field cannot be Empty");
            return false;
        }
        else if (!val.matches(passwordval)){
            password.setError("Password is too week");
            return false;
        }
        else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateDailysteps(){
        String val=goal1.getEditText().getText().toString();
        if (val.isEmpty()){
            goal1.setError("Field cannot be empty");
            return false;
        }
        else if(Integer.parseInt(val)>50000){
            goal1.setError("Sorry! Max goal is 50000 steps");
            return false;
        }
        else{
            goal1.setError(null);
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

        if(!validateDailysteps()| !validateheight() | !validateweight() | !validateage() | !validateName()| !validateUserName() | !validateemail() | !validatePhoneNo() | !validatePassword())
        {
            return ;
        }

        String Height=height.getEditText().getText().toString();
        String Weight=weight.getEditText().getText().toString();
        String Age=age.getEditText().getText().toString();
        String Name=fullname.getEditText().getText().toString();
        String Username=usernameLabel.getEditText().getText().toString();
        String Email=email.getEditText().getText().toString();
        String PhoneNumber=phno.getEditText().getText().toString();
        String Password=password.getEditText().getText().toString();
        String Actives=Active;
        String Gender=user_gender;
        String Goal=goal1.getEditText().getText().toString();
        UserHelperClass2 helperClass= new UserHelperClass2(Name,Username,Email,PhoneNumber,Password,Height,Weight,Age,Gender,Actives,Goal);
        reference.setValue(helperClass);

        String PhoneFromDB=user_phone;
        Intent intent = new Intent(getApplicationContext(), ShowSteps.class);
        intent.putExtra("phoneno",PhoneFromDB);
        intent.putExtra("name",Name);
        intent.putExtra("goal",Goal);
        startActivity(intent);


        if (isNameChanged() || isUsernameChanged()) {
            Toast.makeText(this, "Data have been Changed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Data are same and cannot be updated ", Toast.LENGTH_SHORT).show();
        }


    }
    private boolean isUsernameChanged() {
        if (!user_Username.equals(usernameLabel.getEditText().toString())){
            reference.child("username").setValue(usernameLabel.getEditText().getText().toString());
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isNameChanged () {
        if (!user_name.equals(fullname.getEditText().toString())){
            reference.child("name").setValue(fullname.getEditText().getText().toString());
            return true;
        }
        else{
            return false;
        }
    }

}