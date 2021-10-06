package com.example.cibo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

public class employee_orders extends AppCompatActivity {
    JSONObject db = null;
    JSONArray array = null;
    ImageView no_img;
    TextView txt;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> img = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    ShimmerFrameLayout frameLayout;
    ImageButton refresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_orders);
        frameLayout = findViewById(R.id.orders_shimmer_layout);
        no_img = findViewById(R.id.e_order_img);
        txt = findViewById(R.id.e_order_text);
        new disp().execute();

        refresh = findViewById(R.id.e_order_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                no_img.setVisibility(View.INVISIBLE);
                txt.setVisibility(View.INVISIBLE);
                name.clear();img.clear();price.clear();
                RecyclerView ordered = findViewById(R.id.e_order_recycler_view);
                ordered.removeAllViewsInLayout();
                new disp().execute();
            }
        });
    }

    public class disp extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            frameLayout.startShimmer();
            frameLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
            try {
                db = sheet_comms.readAllData("sheet4");
                if (db != null) {
                    array = db.getJSONArray("records");}
                if(array!=null)
                {for(int i=0;i<array.length();i++)
                {if(sharedPreferences.getString("key", "").equals(array.getJSONObject(i).get("id").toString()) && sharedPreferences.getString("emp_email", "").equals(array.getJSONObject(i).get("email").toString()) && sharedPreferences.getString("emp_name", "").equals(array.getJSONObject(i).get("name_").toString()))
                {name.add(array.getJSONObject(i).get("dish").toString());
                img.add(array.getJSONObject(i).get("img").toString());
                price.add(array.getJSONObject(i).get("price").toString());}}

                db = sheet_comms.readAllData("sheet5");
                if (db != null) {
                    array = db.getJSONArray("records");}
                for(int i=0;i<array.length();i++)
                {if(sharedPreferences.getString("key", "").equals(array.getJSONObject(i).get("id").toString()) && sharedPreferences.getString("emp_email", "").equals(array.getJSONObject(i).get("email").toString()) && sharedPreferences.getString("emp_name", "").equals(array.getJSONObject(i).get("name").toString()))
                {name.add(array.getJSONObject(i).get("dish").toString());
                img.add(array.getJSONObject(i).get("img").toString());
                price.add(array.getJSONObject(i).get("price").toString());}}}
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            frameLayout.stopShimmer();
            frameLayout.setVisibility(View.INVISIBLE);
            if(name.size()==0)
            {no_img.setVisibility(View.VISIBLE);
            txt.setVisibility(View.VISIBLE);}
            else
            {show();}
        }
    }

    void show()
    {LinearLayoutManager ordered_layoutManager = new LinearLayoutManager(employee_orders.this, LinearLayoutManager.VERTICAL, false);
    RecyclerView ordered = findViewById(R.id.e_order_recycler_view);
    ordered.setLayoutManager(ordered_layoutManager);
    ordered ordered_adapter = new ordered(employee_orders.this, name, img, price);
    ordered.setAdapter(ordered_adapter);}

    public class delete extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(employee_orders.this, employee_menu.class));
    }
}