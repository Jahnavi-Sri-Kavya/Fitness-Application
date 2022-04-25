package com.example.fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.fitness.R.*;

public class NewPassword extends AppCompatActivity {
    Button next;
    DatabaseReference reference;
    String user_phone,passwords;
    TextInputLayout t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        next=findViewById(R.id.changepwd);
        t1=findViewById(R.id.newp);
        t2=findViewById(id.confp);
        Intent intent=getIntent();
        user_phone=intent.getStringExtra("phno");
        reference= FirebaseDatabase.getInstance().getReference("user").child(user_phone);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass2 user=snapshot.getValue(UserHelperClass2.class);

                passwords=user.password;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void update(View view){
        if (isNameChanged()) {
            Toast.makeText(this, "Data have been Changed", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(NewPassword.this,SuccessMsg.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Data are same and cannot be updated ", Toast.LENGTH_SHORT).show();
        }


    }
    private boolean isNameChanged () {
        if (!passwords.equals(t1.getEditText().toString())){
            reference.child("password").setValue(t1.getEditText().getText().toString());
            return true;
        }
        else{
            return false;
        }
    }
}