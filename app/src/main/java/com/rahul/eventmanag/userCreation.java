package com.rahul.eventmanag;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
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
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class userCreation extends AppCompatActivity  {
    private static final int SELECT_PICTURE = 100;
    Spinner role,dept;
 Uri selectedImageUri;
    Uri downloadUrl;
Button signup;
EditText email,pass,regusername,regMobile,designation;
private FirebaseAuth mAuth ;
ImageView imageView;
ImageButton imageChooser;
ProgressBar progressBar;
    DatabaseReference myRef;
    FirebaseDatabase database;
    String emailid,password,mobile,desig,dispn,deprt,rol;

    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creation);
        mAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.regemail);
        regMobile=findViewById(R.id.regmobile);
        designation=findViewById(R.id.designation);
        pass=findViewById(R.id.regpassword);
        imageView=findViewById(R.id.regimageView);
        regusername=findViewById(R.id.regusername);
        progressBar=findViewById(R.id.progressBar);
        imageChooser=findViewById(R.id.imgChooser);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        imageChooser.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });
        signup=findViewById(R.id.createaccount);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 emailid=email.getText().toString();
                 password=pass.getText().toString();
                 mobile=regMobile.getText().toString().trim();
                 desig=designation.getText().toString().trim();
                 dispn=regusername.getText().toString().trim();
                 deprt=dept.getSelectedItem().toString();
                 rol=role.getSelectedItem().toString();
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
                else if(mobile.length()<10){
                    regMobile.setError("invalid mobile no");
                    regMobile.requestFocus();

                }
                else if(designation==null){
                    designation.setError("fill designation");
                    designation.requestFocus();
                }
                else if(dispn.length()<5)
                {
                   regusername.setError("name should be greater then 5 char");
                   regusername.requestFocus();
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

                            uploadImages();


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

    private void showImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                 selectedImageUri = data.getData();
                try {
                    Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImageUri);
                    imageView.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else
            {
                Toast.makeText(userCreation.this,"problem in image chooser",Toast.LENGTH_LONG).show();

            }
        }
    }

    private void uploadImages(){

        mStorageRef = FirebaseStorage.getInstance().getReference("profilepic"+System.currentTimeMillis()+".jpg");
       if(selectedImageUri!=null) {

            mStorageRef.putFile(selectedImageUri)
                   .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           // Get a URL to the uploaded content
                           mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                           {
                                                                                 @Override
                                                                                 public void onSuccess(Uri uri) {
                                                                                     downloadUrl = uri;
                                                                                     updateUserDb(downloadUrl);
                                                                                     profileupdate(downloadUrl);

                                                                                     //Do what you need to do with url
                                                                                 }
                                                                             });
//                           downloadUrl = taskSnapshot.getUploadSessionUri();

//                           profileupdate();

                       }
                   })
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception exception) {
                           // Handle unsuccessful uploads
                           // ...
                       }

                   });


       }
    }
///////////////////update  user profile///////////////////////
    private void profileupdate(Uri d) {
        FirebaseUser user = mAuth.getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(regusername.getText().toString())
                .setPhotoUri(d)
                .build();

        user.updateProfile(profileUpdates);

    }
    //////////////////////////////
//////////////real time user database  maintain/////////////////
    private void updateUserDb(Uri d) {
         database = FirebaseDatabase.getInstance();
        myRef = database.getReference("user");
        String uid=myRef.push().getKey();
        usermodel umodel=new usermodel(uid,dispn,emailid,mobile,password,rol,deprt,d.toString());
        myRef.child(uid).setValue(umodel);
        Toast.makeText(userCreation.this,"user create",Toast.LENGTH_LONG).show();
        Intent i=new Intent(userCreation.this,com.rahul.eventmanag.login.class);
        startActivity(i);
    }
    //////////////////////////////////////

}
