package com.example.fitness;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    Button backlogin,regbtn;
    TextInputLayout regName,regUsername,regEmail,regphoneNo,regPassword;

    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        backlogin=findViewById(R.id.login_screen);
        regName=findViewById(R.id.full_name);
        regUsername=findViewById(R.id.user_name);
        regEmail=findViewById(R.id.email);
        regphoneNo=findViewById(R.id.phoneno);
        regPassword=findViewById(R.id.password);
        regbtn=findViewById(R.id.signup_go);

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode =FirebaseDatabase.getInstance();
                reference =rootNode.getReference("user");
                if(!validateName()| !validateUserName() | !validateemail() | !validatePhoneNo() | !validatePassword())
                {
                    return ;
                }
                Intent intent=new Intent(SignUp.this,Login.class);
                startActivity(intent);
                String name=regName.getEditText().getText().toString();
                String username=regUsername.getEditText().getText().toString();
                String email=regEmail.getEditText().getText().toString();
                String phoneNo=regphoneNo.getEditText().getText().toString();
                String password=regPassword.getEditText().getText().toString();
                UserHelperClass helperClass=new UserHelperClass(name,username,email,phoneNo,password);
                reference.child(phoneNo).setValue(helperClass);



            }
        });

        backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUp.this,Login.class);
                startActivity(intent);
            }
        });

    }
    private Boolean validateName(){
        String val=regName.getEditText().getText().toString();
        if (val.isEmpty()){
            regName.setError("Field cannot be empty");
            return false;
        }
        else{
            regName.setError(null);
            return true;
        }
    }
    private Boolean validateUserName(){
        String val=regUsername.getEditText().getText().toString();
        String noWhiteSpace="\\A\\w{4,20}\\z";
        if (val.isEmpty()){
            regUsername.setError("Field cannot be empty");
            return false;
        }
        else if(val.length()>15){
            regUsername.setError("username too long");
            return false;
        }
        else if (!val.matches(noWhiteSpace)){
            regUsername.setError("White Spaces are not allowed");
            return false;
        }
        else{
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateemail(){
        String val=regEmail.getEditText().getText().toString();
        String emailp="[a-zA-Z0-9,_.]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()){
            regEmail.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(emailp)){
            regEmail.setError("Invalid Email Address");
            return false;
        }
        else{
            regEmail.setError(null);
            return true;
        }
    }
    private Boolean validatePhoneNo(){
        String val=regphoneNo.getEditText().getText().toString();
        if (val.isEmpty()){
            regphoneNo.setError("Field cannot be empty");
            return false;
        }
        else{
            regphoneNo.setError(null);
            return true;
        }
    }
    private Boolean validatePassword(){
        String val=regPassword.getEditText().getText().toString();
        String passwordval="^"+"(?=.*[a-zA-Z])"+"(?=.*[@#$%^&+=])"+"(?=\\S+$)"+".{4,}"+"$";
        if (val.isEmpty()){
            regPassword.setError("Field cannot be Empty");
            return false;
        }
        else if (!val.matches(passwordval)){
            regPassword.setError("Password is too week");
            return false;
        }
        else{
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }



}