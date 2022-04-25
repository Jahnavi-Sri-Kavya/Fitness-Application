package com.example.fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    Button callSignUp,login_btn,forgetpwd;
    ImageView image;
    TextView logo,slogan;
    TextInputLayout username,passwords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        callSignUp=findViewById(R.id.signup_screen);
        image=findViewById(R.id.logo_image);
        logo=findViewById(R.id.logo_name);
        slogan=findViewById(R.id.slogan_name);
        username=findViewById(R.id.username);
        passwords=findViewById(R.id.passwordl);
        login_btn=findViewById(R.id.login_btn);
        forgetpwd=findViewById(R.id.forgetpwd);


        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,SignUp.class);
                Pair[] pairs=new Pair[7];
                pairs[0] = new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(logo,"logo_text");
                pairs[2] = new Pair<View,String>(slogan,"slogan_text");
                pairs[3] = new Pair<View,String>(username,"username");
                pairs[4] = new Pair<View,String>(passwords,"password");
                pairs[5] = new Pair<View,String>(login_btn,"go_train");
                pairs[6] = new Pair<View,String>(callSignUp,"login_signUp");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
                    startActivity(intent,options.toBundle());
                }
            }
        });
        forgetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,ForgetPassword.class);
                startActivity(intent);
            }
        });
                
    }
    private Boolean validateUsername(){
        String val=username.getEditText().getText().toString();
        if (val.isEmpty()){
            username.setError("Field cannot be Empty");
            return false;
        }
        else{
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword(){
        String val=passwords.getEditText().getText().toString();
        if (val.isEmpty()){
            passwords.setError("Field cannot be Empty");
            return false;
        }
        else{
            passwords.setError(null);
            passwords.setErrorEnabled(false);
            return true;
        }

    }
    public void loginUser(View view){
        if (!validateUsername()|!validatePassword()){
            return ;
        }
        else{
            isUser();
        }
    }
    private void isUser() {
        String userEntereduser=username.getEditText().getText().toString();
        String userEnteredpwd=passwords.getEditText().getText().toString();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("user");

        Query checkUser=reference.orderByChild("phoneNo").equalTo(userEntereduser);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB=snapshot.child(userEntereduser).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userEnteredpwd)) {
                        username.setError(null);
                        username.setErrorEnabled(false);
                        String UsernameFromDB=snapshot.child(userEntereduser).child("username").getValue(String.class);
                        String PhoneFromDB=snapshot.child(userEntereduser).child("phoneNo").getValue(String.class);
                        String nameFromDB=snapshot.child(userEntereduser).child("name").getValue(String.class);
                        String EmailFromDB=snapshot.child(userEntereduser).child("email").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                        intent.putExtra("username",UsernameFromDB);
                        intent.putExtra("phoneno",PhoneFromDB);
                        intent.putExtra("name",nameFromDB);
                        intent.putExtra("password",passwordFromDB);
                        intent.putExtra("email",EmailFromDB);
                        startActivity(intent);

                    }
                    else{
                        passwords.setError("Wrong Password!");
                        passwords.requestFocus();
                    }
                }
                else{
                    username.setError("No such user exists!");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}