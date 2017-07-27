package com.harlertechnologies.mkopopopoteapp;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    //defining views
    private EditText editTextIDNo;
    private EditText editTextLastname;
    private EditText editTextFirstname;
    private EditText editTextEmail;
    private EditText editTextDOB;
    private EditText editTextGender;
    private Button buttonNext;

    final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //check for permissions
        int permissionCheck = ContextCompat.checkSelfPermission(RegisterActivity.this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION);


        //request permissions

        if(ContextCompat.checkSelfPermission(RegisterActivity.this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED){

            //should we show an explanation?
            if(ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)){

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            }else {
                //no explanation needed, we can request the permission

                ActivityCompat.requestPermissions(RegisterActivity.this,
                        new String []{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        //initializing views
        editTextIDNo = (EditText) findViewById(R.id.editTextIDNo);
        editTextLastname = (EditText) findViewById(R.id.editTextLastname);
        editTextFirstname = (EditText) findViewById(R.id.editTextFirstname);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextDOB=(EditText) findViewById(R.id.editTextDOB);
        editTextGender=(EditText) findViewById(R.id.editTextGender);

        buttonNext = (Button) findViewById(R.id.buttonNext);

        //setting listeners to button
        buttonNext.setOnClickListener(this);
    }

    public void addUser(){
        final String idno = editTextIDNo.getText().toString().trim();
        final String lastname = editTextLastname.getText().toString().trim();
        final String firstname = editTextFirstname.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String dob = editTextDOB.getText().toString().trim();
        final String gender = editTextGender.getText().toString().trim();


        class AddUser extends AsyncTask<Void, Void, String>{
            ProgressDialog loading; //// TODO: 7/26/17 Progress Dialog depracated, look for alternative

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this,"Adding...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v){
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_IDNO, idno);
                params.put(Config.KEY_LASTNAME, lastname);
                params.put(Config.KEY_FIRSTNAME, firstname);
                params.put(Config.KEY_EMAIL, email);
                params.put(Config.KEY_DOB, dob);
                params.put(Config.KEY_GENDER, gender);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;
            }
        }
        AddUser au = new AddUser();
        au.execute();
    }

    @Override
    public void onClick(View v){
        if(v == buttonNext){
            addUser();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                //if request is cancelled, the results array is empty
                if(grantResults.length>0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    //permission was granted, do tasks
                }else{
                    //permission denied, disable functionality that depends on this permission
                }
                return;
            }

            //other case lines for other permissions app might request
        }
    }
}
