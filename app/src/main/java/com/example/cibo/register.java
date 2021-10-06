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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

public class register extends AppCompatActivity {
    EditText oname, omail, obranch, opassword;
    Button register;
    String org_name, org_email, org_branch, org_password;
    RelativeLayout relativeLayout;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        relativeLayout = findViewById(R.id.ar_relative);
        progressBar = findViewById(R.id.ar_progress);
        register = findViewById(R.id.register_register_btn);
        oname = findViewById(R.id.register_orga_name);
        obranch = findViewById(R.id.register_orga_branch);
        omail = findViewById(R.id.register_orga_email);
        opassword = findViewById(R.id.register_orga_password);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new load().execute();
                }
        });

    }

    class load extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            relativeLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            relativeLayout.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
            JSONObject db = null;
            try {
                db = sheet_comms.readAllData("Sheet1");
            } catch (IOException e) {
                e.printStackTrace();
            }
            int id = random_id(db);
            while (id==0000)
            {id = random_id(db);}
            org_name = oname.getText().toString();
            org_email = omail.getText().toString();
            org_branch = obranch.getText().toString();
            org_password = opassword.getText().toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("key", String.valueOf(id));
            editor.apply();
            sheet_comms.org_regis(org_name, org_email, org_branch, org_password, id);
            startActivity(new Intent(register.this, organization_profile.class));
            return null;
        }
    }

    int random_id(JSONObject db)
    {Random random = new Random();
    int id =  Integer.parseInt(String.format("%04d", random.nextInt(10000)));
    boolean flag = false;
    try {
        JSONArray array = null;
        System.out.println("dbbbbb" + db);
        if (db != null) {
            array = db.getJSONArray("records");}
        for(int i=0;i<array.length();i++)
        {System.out.println("iiiiidddddd " + array.getJSONObject(i).get("id"));
        if(array.getJSONObject(i).get("id").equals(id))
        {flag = true;
        break;}}
    } catch (JSONException e) {
        e.printStackTrace();
    }
    if(flag == true)
    return 0000;
    else
    return id;}

}