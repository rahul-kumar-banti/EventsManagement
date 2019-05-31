package com.rahul.eventmanag;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class userCreation extends AppCompatActivity  {
Spinner role,dept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creation);
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
}
