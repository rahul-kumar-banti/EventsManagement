package com.rahul.eventmanag;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class guestCreation extends AppCompatActivity  {
    private static final int SELECT_PICTURE = 100;



Button createGuest;
EditText guestemail,guestname,guestaddress,guestmobile,guestcompany,guestdesignation,guestDescrisption;
private FirebaseAuth mAuth ;
ProgressBar guestprogressBar;
    DatabaseReference myRef;
    FirebaseDatabase database;
    String gemail,gname,gaddress,gmobile,gcompany,gdesignation,descrtion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_creation);
        mAuth=FirebaseAuth.getInstance();
        guestemail=findViewById(R.id.guestemail);
        guestmobile=findViewById(R.id.guestmobile);
        guestdesignation=findViewById(R.id.guestdesignation);
        guestname=findViewById(R.id.guestname);
        guestaddress=findViewById(R.id.guesAddress);
        guestcompany=findViewById(R.id.guestcompany);
        guestDescrisption=findViewById(R.id.guestDescrisption);
        createGuest=findViewById(R.id.createGuest);
        guestprogressBar=findViewById(R.id.guestprogressBar);




        createGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gemail=guestemail.getText().toString().trim();
                gname=guestname.getText().toString().trim();
                gaddress=guestaddress.getText().toString().trim();
                gmobile=guestmobile.getText().toString().trim();
                gcompany=guestcompany.getText().toString().trim();
                gdesignation=guestdesignation.getText().toString().trim();
                descrtion=guestDescrisption.getText().toString().trim();

                boolean []res=new boolean[2];
                res=validate(gemail,gname);
                if(!res[0]){
                    guestemail.setError("invalid email");
                    guestemail.requestFocus();
                }
                else if(!res[1]){
                    guestname.setError("name should be >5  char");
                    guestname.requestFocus();
                }
                else if(gmobile.length()<10){
                    guestmobile.setError("invalid mobile no");
                    guestmobile.requestFocus();

                }
                else if(gdesignation==null){
                    guestdesignation.setError("fill designation");
                    guestdesignation.requestFocus();
                }
                else if(descrtion.length()<10)
                {
                    guestDescrisption.setError("description should at least 10 char");
                    guestDescrisption.requestFocus();
                }
                else if(gcompany==null)
                {
                    guestcompany.setError("fill  company name");
                    guestcompany.requestFocus();
                }
                else
                {
                    guestprogressBar.setVisibility(View.VISIBLE);
                    createguestdb();

                }
            }
        });




    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            Intent i=new Intent(guestCreation.this, login.class);
            startActivity(i);
        }
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







    //////////////////////////////
//////////////real time user database  maintain/////////////////
    private void createguestdb() {
         database = FirebaseDatabase.getInstance();
        myRef = database.getReference("guest");
        String uid=myRef.push().getKey();
        guestModel guestModel=new guestModel(gemail,gname,gaddress,gmobile,gcompany,gdesignation,descrtion);
        myRef.child(uid).setValue(guestModel);
        Toast.makeText(guestCreation.this,"guest is created",Toast.LENGTH_LONG).show();
        Intent i=new Intent(guestCreation.this, com.rahul.eventmanag.MainActivity.class);
        startActivity(i);
    }
    //////////////////////////////////////

}
