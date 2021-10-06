package com.example.cibo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    TextView hw;
    Button sign_in, chef;
    EditText key, name, email;
    ProgressBar pb;
    RelativeLayout relativeLayout;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb = findViewById(R.id.ma_progress);
        relativeLayout = findViewById(R.id.me_relative);
        SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        chef = findViewById(R.id.chef_btn);
        chef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, chef_login.class));
            }
        });
        sign_in = findViewById(R.id.main_sign_in);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new load().execute();
            }
        });

        key = findViewById(R.id.userkey_btn);
        name = findViewById(R.id.name_btn);
        email = findViewById(R.id.mail_btn);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        editor.putString("token", token);
                        editor.apply();
//                        name.setText(token);
                        System.out.println("kkkkkk " + token);
                        Log.d(TAG, token);
                    }
                });

        Button reg = findViewById(R.id.main_register_btn);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, orga_sign_in.class));
            }
        });

    }

    int check()
    {   JSONObject db = null;
        int flag = 0;
        JSONArray array = null;
        try {
            db = sheet_comms.readAllData("Sheet2");
            if(db != null)
                array = db.getJSONArray("records");
        } catch (IOException | JSONException e) {}

        SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(array!=null)
        {for(int i=0;i<array.length();i++)
        {try {
            if(array.getJSONObject(i).get("id").toString().equals(key.getText().toString()))
            {editor.putString("key", key.getText().toString());
                editor.putString("emp_name", name.getText().toString());
                editor.putString("emp_email", email.getText().toString());
                editor.apply();
                flag = 1;
                break;}

        } catch (JSONException e) {
            e.printStackTrace();
        }
        }
    return flag;}
    return 0;}

    class load extends AsyncTask<Void, Void, Void>
    {int flag=0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            flag = check();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if(flag==1)
            {pb.setVisibility(View.INVISIBLE);
            relativeLayout.setVisibility(View.INVISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            startActivity(new Intent(MainActivity.this, employee_menu.class));}

            else
            {Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            pb.setVisibility(View.INVISIBLE);
            relativeLayout.setVisibility(View.INVISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);}
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}