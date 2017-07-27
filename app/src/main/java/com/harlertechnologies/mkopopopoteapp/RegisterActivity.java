package com.harlertechnologies.mkopopopoteapp;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    //defining views
    private EditText editTextIDNo;
    private EditText editTextLastname;
    private EditText editTextFirstname;
    private EditText editTextEmail;
    private EditText editTextDOB;
    private EditText editTextGender;
    private Button buttonNext;
    private Spinner genderSpinner;

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
        genderSpinner=(Spinner) findViewById(R.id.genderSpinner);

        buttonNext = (Button) findViewById(R.id.buttonNext);

        //populate spinner
        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        genderSpinner.setAdapter(adapter);

        //setting listeners to button
        buttonNext.setOnClickListener(this);
    }

    public void addUser(){
        final String idno = editTextIDNo.getText().toString().trim();
        final String lastname = editTextLastname.getText().toString().trim();
        final String firstname = editTextFirstname.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String dob = editTextDOB.getText().toString().trim();
        final String gender = genderSpinner.getSelectedItem().toString();


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
        //// TODO: 7/27/17 Validate
        String genderCheck = String.valueOf(genderSpinner.getSelectedItem());
        if(genderCheck.equals("Please select your gender")){
            Toast.makeText(RegisterActivity.this,"Please select your gender", Toast.LENGTH_SHORT).show();
            if(TextUtils.isEmpty(idno)){
                editTextIDNo.setError("ID Number is required");
                return;
            }
            if(TextUtils.isEmpty(lastname)){
                editTextLastname.setError("Last name is required");
                return;
            }
            if(TextUtils.isEmpty(email)){
                editTextEmail.setError("Email is required");
                return;
            }
        }else {
            au.execute();
            
            //// TODO: 7/27/17 Start next activity 
        }
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
