package com.rahul.eventmanag;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.util.regex.Pattern;

public class login extends AppCompatActivity {
Button login,signup;
CheckBox checkBox;
EditText email,pass;
Boolean remeber=false;
Intent intent;
private FirebaseAuth mAuth;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.login);
        pass=findViewById(R.id.pass);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        progressBar=findViewById(R.id.loginprogressBar);
        mAuth = FirebaseAuth.getInstance();
        checkBox=findViewById(R.id.remember);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                remeber=isChecked;

            }
        });
        signup=findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i=new Intent(login.this,com.rahul.eventmanag.userCreation.class);
            startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (remeber){
                    Toast.makeText(login.this, "checked box hitted", Toast.LENGTH_SHORT).show();
                }
                String emailid=email.getText().toString();
                String password=pass.getText().toString();
                boolean []res=new boolean[2];
                res=validate(emailid,password);
                if(!res[0]){
                    email.setError("invalid email");
                    email.requestFocus();
                }
                else if(!res[1]){
                    pass.setError("password must be >5");
                    pass.requestFocus();
                }
                else
                {
mAuth.signInWithEmailAndPassword(emailid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        progressBar.setVisibility(View.GONE);
        if(task.isSuccessful()){
            Intent i=new Intent(login.this,com.rahul.eventmanag.MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        else
        {
            Toast.makeText(login.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
        }
    }
});
                }

            }
        });
    }
    protected void  onStart() {

        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }
    public static boolean[] validate(String em,String pass){
        boolean []rs=new boolean[2];
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);

        rs[0]=pat.matcher(em).matches();
        if(pass.length()<5){
            rs[1]=false;
        }
        else
        rs[1]=true;
return rs;
    }
}
