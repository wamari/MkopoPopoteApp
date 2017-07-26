package com.harlertechnologies.mkopopopoteapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    //defining views
    private EditText editTextIDNo;
    private EditText editTextLastname;
    private EditText editTextFirstname;
    private EditText editTextEmail;
    private EditText editTextDOB;
    private EditText editTextGender;

    private Button buttonSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initializing views
        editTextIDNo = (EditText) findViewById(R.id.editTextIDNo);
        editTextLastname = (EditText) findViewById(R.id.editTextLastname);
        editTextFirstname = (EditText) findViewById(R.id.editTextFirstname);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextDOB=(EditText) findViewById(R.id.editTextDOB);
        editTextGender=(EditText) findViewById(R.id.editTextGender);

        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);

        //setting listeners to button
        buttonSignUp.setOnClickListener(this);
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
        if(v == buttonSignUp){
            addUser();
        }
    }
}
