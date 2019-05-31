package com.rahul.eventmanag;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class userCreation extends AppCompatActivity  {
Spinner role,dept;
Button signup;
EditText email,pass;
private FirebaseAuth mAuth;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creation);
        mAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.regemail);
        pass=findViewById(R.id.regpassword);
        progressBar=findViewById(R.id.progressBar);
        signup=findViewById(R.id.createaccount);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    progressBar.setVisibility(View.VISIBLE);
                    requestSignup(emailid,password);
                }
            }
        });
        List<String> userrole = new ArrayList<String>();
        userrole.add("faculty");
        userrole.add("admin");
        List<String> department = new ArrayList<String>();
        department.add("IT");
        department.add("Management");
        dept=findViewById(R.id.departmentspinner);

        role=findViewById(R.id.rolespinner);
        ArrayAdapter<String> adpt;
        adpt = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,userrole);
        adpt.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        role.setAdapter(adpt);
        ArrayAdapter<String> deptadpt=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,department);
        deptadpt.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        dept.setAdapter(deptadpt);

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        String em=currentUser.getEmail().toString();
    }
    private void requestSignup(String emailid, String password) {
        mAuth.createUserWithEmailAndPassword(emailid, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("create user", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i=new Intent(userCreation.this,com.rahul.eventmanag.login.class);
                            startActivity(i);
                            Toast.makeText(userCreation.this,"current user"+user.getEmail(),Toast.LENGTH_LONG).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(userCreation.this, "user is  already  registerd",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Log.w("on un success", task.getException().getMessage(), task.getException());
                                Toast.makeText(userCreation.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }


                        }


                    }
                });

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
