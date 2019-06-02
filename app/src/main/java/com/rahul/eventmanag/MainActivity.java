package com.rahul.eventmanag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageView profile;
    TextView dispname;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Toolbar toolbar;
    Button guestc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profile=findViewById(R.id.profileimg);
        dispname=findViewById(R.id.dispname);
        toolbar=findViewById(R.id.menutoolbar);
        guestc=findViewById(R.id.guestcreation);
        guestc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, com.rahul.eventmanag.guestCreation.class);
            startActivity(i);
            }
        });
        setSupportActionBar(toolbar);
        mAuth=FirebaseAuth.getInstance();
        loadUserInfo();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                FirebaseAuth  mAuth=FirebaseAuth.getInstance();
                mAuth.signOut();
                finish();
                startActivity(new Intent(MainActivity.this,com.rahul.eventmanag.login.class));
        }
        return true;
    }

    private void loadUserInfo(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
           if(currentUser.getDisplayName()!=null){
               dispname.setText(mAuth.getCurrentUser().getDisplayName());
           }
           if(currentUser.getPhotoUrl()!=null){

               Glide.with(this)
                       .load(currentUser.getPhotoUrl())
                       .placeholder(R.drawable.ic_person_black_24dp)
                       .into(profile);
//               Toast.makeText(MainActivity.this,currentUser.getPhotoUrl().toString(),Toast.LENGTH_LONG).show();

           }
        }

    }

    protected void  onStart() {

        super.onStart();

         currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            finish();
            startActivity(new Intent(MainActivity.this,com.rahul.eventmanag.login.class));
        }
    }
}
