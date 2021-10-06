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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class add_chef extends AppCompatActivity {
    EditText c_name, c_email, c_password, c_confirm_password;
    Button add_btn;
    RelativeLayout relativeLayout;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chef);
        relativeLayout = findViewById(R.id.ac_relative);
        progressBar = findViewById(R.id.ac_progress);
        c_name = findViewById(R.id.ac_chef_name);
        c_email = findViewById(R.id.ac_chef_email);
        c_password = findViewById(R.id.ac_chef_password);
        c_confirm_password = findViewById(R.id.ac_confirm_password);
        add_btn = findViewById(R.id.ac_add_btn);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flag = 0;
                String cc_name = c_name.getText().toString().trim();
                String cc_email = c_email.getText().toString().trim();
                String cc_password = c_password.getText().toString().trim();
                String ccc_password = c_confirm_password.getText().toString();
                ArrayList<String> to_show = new ArrayList<>();

                if(cc_name!=null && cc_name.length()!=0)
                {flag=0;}
                else
                {to_show.add("Enter valid name");
                flag = 1;}

                if(cc_email!=null && cc_email.length()!=0 && cc_email.contains("@"))
                {flag = 0;}
                else
                {to_show.add("Enter valid mailID");
                flag = 1;}

                if(cc_password!=null && cc_password.length()!=0 && ccc_password.equals(cc_password))
                {flag=0;}
                else
                {to_show.add("Invalid password");
                flag = 1;}

                if(flag==0)
                {new load().execute();
                postexec();
                startActivity(new Intent(add_chef.this, chef_details.class));}

                else
                    Toast.makeText(add_chef.this, to_show.get(0), Toast.LENGTH_SHORT).show();
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
        protected Void doInBackground(Void... voids) {
            SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
            StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://script.google.com/macros/s/AKfycbyiNcLUOtC9Sx9cowD0Hwz_KmMxUpOnTx5iGzvW2iWFuTFSPfxkodgL77i2tjFqgiHu/exec",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            loading.dismiss();
                            System.out.println("ssssssss " + response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("ssssssss " + error);
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("action","add_chef");
                    params.put("name", c_name.getText().toString());
                    params.put("email",c_email.getText().toString());
                    params.put("password",c_password.getText().toString());
                    params.put("id", sharedPreferences.getString("key", ""));
//                        System.out.println("bbbbbbbbbb " + f_image);
                    return params;
                }

            };

            int socketTimeout = 30000; // 30 seconds. You can change it
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            RequestQueue requestQueue = Volley.newRequestQueue(add_chef.this);
            requestQueue.add(stringRequest);
            return null;
        }
    }

    void postexec()
    {relativeLayout.setVisibility(View.INVISIBLE);
    progressBar.setVisibility(View.INVISIBLE);
    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    Toast.makeText(add_chef.this, "Details added successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(add_chef.this, chef_details.class));
    }
}