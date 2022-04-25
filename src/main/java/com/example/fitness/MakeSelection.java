package com.example.fitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class MakeSelection extends AppCompatActivity {
    Button phone,email;
    TextView emailtxt,phoneno;
    ImageButton backlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_selection);
        phone=findViewById(R.id.phonebtn);
        email=findViewById(R.id.emailbtn);
        emailtxt=findViewById(R.id.email_des);
        phoneno=findViewById(R.id.phone_des);
        showAllUserData();
        backlogin=findViewById(R.id.back2);
        backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MakeSelection.this,ForgetPassword.class);
                startActivity(intent);
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MakeSelection.this,PhoneVerification.class);
                String phNo= phoneno.getText().toString();
                intent.putExtra("PhoneNo",phNo);
                startActivity(intent);
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MakeSelection.this,NewPassword.class);
                startActivity(intent);
            }
        });

    }
    private void showAllUserData() {
        Intent intent = getIntent();
        String user_phoneno = intent.getStringExtra("phoneNo");
        String user_email = intent.getStringExtra("email");
        emailtxt.setText(user_email);
        phoneno.setText(user_phoneno);
    }

}