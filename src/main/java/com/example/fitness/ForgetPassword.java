package com.example.fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class ForgetPassword extends AppCompatActivity {
    Button Next;
    ImageButton backlogin;
    TextInputLayout email;
    CountryCodePicker countryCodePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Next=findViewById(R.id.next1);
        email=findViewById(R.id.FogetEmail);
        countryCodePicker=findViewById(R.id.country_code_picker);
        backlogin=findViewById(R.id.back1);
        backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ForgetPassword.this,Login.class);
                startActivity(intent);
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail()) {
                    return;
                } else {
                    isUser();
                }
            }
        });
    }
    private Boolean validateEmail(){
        String val=email.getEditText().getText().toString();
        if (val.isEmpty()){
            email.setError("Field cannot be Empty");
            return false;
        }
        else{
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private void isUser() {
        String userEnteredPhno = email.getEditText().getText().toString();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("user");

        Query checkUser=reference.orderByChild("phoneNo").equalTo(userEnteredPhno);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    email.setError(null);
                    email.setErrorEnabled(false);
                    Intent intent = new Intent(getApplicationContext(), PhoneVerification.class);
                    String code = countryCodePicker.getSelectedCountryCodeWithPlus();
                    final String _phoneNo =code+ userEnteredPhno;
                    intent.putExtra("phoneNo",_phoneNo);
                    startActivity(intent);
                } else {
                    email.setError("No such user exists!");
                    email.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}


