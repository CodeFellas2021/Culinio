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

public class chef_login extends AppCompatActivity {
    JSONObject db = null;
    JSONArray array = null;
    EditText email, password, id;
    Button signin;
    ProgressBar pb;
    RelativeLayout layout;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_login);
        pb = findViewById(R.id.cl_progress);
        layout = findViewById(R.id.cl_relative);
        signin = findViewById(R.id.cl_signin_signin_btn);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new chef_load().execute();
//                if(flag==0)
//                {Toast.makeText(chef_login.this, "Invalid", Toast.LENGTH_SHORT).show();}
            }
        });

        email = findViewById(R.id.cl_orga_email);
        password = findViewById(R.id.cl_orga_password);
        id = findViewById(R.id.cl_orga_id);}



    class chef_load extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
            layout.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if(flag==1)
            {pb.setVisibility(View.INVISIBLE);
            layout.setVisibility(View.INVISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            startActivity(new Intent(chef_login.this, chef_screen.class));}

            else
            {Toast.makeText(chef_login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            pb.setVisibility(View.INVISIBLE);
            layout.setVisibility(View.INVISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);}
        }

        @Override
        protected Void doInBackground(Void... voids) {
            SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            flag = 0;
            try {
                db = sheet_comms.readAllData("Sheet3");
                if (db != null) {
                    array = db.getJSONArray("records");}
                if(array!=null)
                {for(int i=0;i<array.length();i++)
                {System.out.println(email.getText().toString() + " " + array.getJSONObject(i).get("email").toString()  + " " +  password.getText().toString() + " " + array.getJSONObject(i).get("password").toString() + " " + id.getText().toString() + " " + array.getJSONObject(i).get("id").toString() + " ooooo");
                    if(email.getText().toString().equals(array.getJSONObject(i).get("email").toString()) && password.getText().toString().equals(array.getJSONObject(i).get("password").toString()) && id.getText().toString().equals(array.getJSONObject(i).get("id").toString()))
                    {System.out.println(email.getText().toString() + " " + array.getJSONObject(i).get("email").toString() + " " + password.getText().toString() + " " + array.getJSONObject(i).get("password").toString() + " " + id.getText() + " " + array.getJSONObject(i).get("id").toString() + " ggggg");
                    flag = 1;
                    editor.putString("key", array.getJSONObject(i).get("id").toString());
                    editor.apply();}}}

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}