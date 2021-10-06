package com.example.cibo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class orga_sign_in extends AppCompatActivity {
JSONObject db = null;
EditText email, password;
SharedPreferences sharedPreferences;
ProgressBar progressBar;
RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orga_sign_in);
        progressBar = findViewById(R.id.sign_in_progress);
        relativeLayout = findViewById(R.id.sign_in_relative);
        email = findViewById(R.id.orga_email);
        password = findViewById(R.id.orga_password);
        Button reg = findViewById(R.id.register_btn);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(orga_sign_in.this, register.class));
            }
        });

        Button signin = findViewById(R.id.signin_signin_btn);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new load().execute();
            }
        });
    }


    class load extends AsyncTask<Void, Void, Void>
    {int flag = 0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            relativeLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if(flag==1)
            {getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            relativeLayout.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            startActivity(new Intent(orga_sign_in.this, organization_profile.class));}
            else
            {Toast.makeText(orga_sign_in.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            relativeLayout.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);}
        }

        @Override
        protected Void doInBackground(Void... voids) {
            sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);

            try {
                db = sheet_comms.readAllData("Sheet1");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                JSONArray array = null;
                if (db != null) {
                    array = db.getJSONArray("records");}
                if(array!=null)
                {for(int i=0;i<array.length();i++)
                {if(array.getJSONObject(i).get("oemail").equals(email.getText().toString()) && array.getJSONObject(i).get("opassword").equals(password.getText().toString()))
                {flag = 1;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("key", array.getJSONObject(i).get("id").toString());
                editor.apply();
                break;}}}
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}