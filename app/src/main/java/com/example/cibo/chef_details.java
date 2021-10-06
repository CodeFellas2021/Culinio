package com.example.cibo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

public class chef_details extends AppCompatActivity {
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> email = new ArrayList<>();
    ArrayList<String> password = new ArrayList<>();
    ShimmerFrameLayout frameLayout;
    ImageView no_chef;
    TextView txt;
    int position;
    RelativeLayout layout;
    ProgressBar pb;
    Context context, context2;
    ImageButton refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_details);
        context = this;
        refresh = findViewById(R.id.cd_refresh);
        frameLayout = findViewById(R.id.cd_shimmer_layout);
        ImageButton btn = findViewById(R.id.cd_add_btn);
        no_chef = findViewById(R.id.cd_no_chef);
        txt = findViewById(R.id.no_chef_txt);
        layout = findViewById(R.id.cd_relative);
        pb = findViewById(R.id.cd_progress);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(chef_details.this, add_chef.class));
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.clear();
                email.clear();
                password.clear();
                no_chef.setVisibility(View.INVISIBLE);
                txt.setVisibility(View.INVISIBLE);
                SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
                RecyclerView recyclerView = findViewById(R.id.chef_details_recyclelayout);
                chef_details_recycler a = new chef_details_recycler(chef_details.this, name, email, password, sharedPreferences.getString("key", ""), layout);
                recyclerView.setAdapter(a);
                a.notifyDataSetChanged();
                new load().execute();
            }
        });

        new load().execute();
    }

    class load extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            frameLayout.setVisibility(View.VISIBLE);
            frameLayout.startShimmer();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if(name.size()!=0)
            {frameLayout.setVisibility(View.INVISIBLE);
            frameLayout.stopShimmer();
            SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
            RecyclerView recyclerView = findViewById(R.id.chef_details_recyclelayout);
            chef_details_recycler a = new chef_details_recycler(chef_details.this, name, email, password, sharedPreferences.getString("key", ""), layout);
            recyclerView.setAdapter(a);
            recyclerView.setLayoutManager(new LinearLayoutManager(chef_details.this));}

            else
            {frameLayout.setVisibility(View.INVISIBLE);
            frameLayout.stopShimmer();
            no_chef.setVisibility(View.VISIBLE);
            txt.setVisibility(View.VISIBLE);}
        }

        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject db = null;
            JSONArray array = null;
            try {
                db = sheet_comms.readAllData("Sheet3");
                assert db != null;
                array = db.getJSONArray("records");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);

            if(array!=null)
            {for(int i=0;i<array.length();i++)
            {
                try {
                    if(array.getJSONObject(i).get("id").toString().equals(sharedPreferences.getString("key", "")))
                    {System.out.println("tttttttt");
                    name.add(array.getJSONObject(i).get("name").toString());
                    email.add(array.getJSONObject(i).get("email").toString());
                    password.add(array.getJSONObject(i).get("password").toString());}

                } catch (JSONException e) {
                    e.printStackTrace();
                }}}
            return null;
        }
    }

//    void delete_chef(int position)
//    {this.position = position;
//    new load2().execute();
//    new load().execute();}
//
//    void progress(int status)
//    {}
//
//    class load2 extends AsyncTask<Void, Void, Void>
//    {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progress(1);
//
//        }
//
//        @Override
//        protected void onPostExecute(Void unused) {
//            super.onPostExecute(unused);
//            progress(0);
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
//            sheet_comms.insert_chef("delete_chef", name.get(position), email.get(position), password.get(position), sharedPreferences.getString("key", ""));
//            return null;
//        }
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(chef_details.this, organization_profile.class));
    }
}