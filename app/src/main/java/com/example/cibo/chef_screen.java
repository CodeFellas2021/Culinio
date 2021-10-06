package com.example.cibo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class chef_screen extends AppCompatActivity {
    JSONObject db = null;
    JSONArray array = null;
    public static ArrayList<String> name = new ArrayList<>();
    public static ArrayList<String> img = new ArrayList<>();
    public static ArrayList<String> mail = new ArrayList<>();
    public static ArrayList<String> c_name = new ArrayList<>();
    public static ArrayList<String> token = new ArrayList<>();
    public static ArrayList<String> id = new ArrayList<>();
    public static Context context;
    public static Context context2;
    public static RecyclerView r;
    ShimmerFrameLayout shimmerFrameLayout;
    ImageView chef_img;
    TextView txt;
    ImageButton refresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_screen);
        refresh = findViewById(R.id.cs_refresh);
        r = findViewById(R.id.cs_order_recycler_view);
        context2 = this.getApplicationContext();
        shimmerFrameLayout = findViewById(R.id.cs_shimmer_layout);
        chef_img = findViewById(R.id.cs_chef_img);
        txt = findViewById(R.id.cs_txt);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.clear();img.clear();mail.clear();c_name.clear();token.clear();id.clear();
                r.removeAllViewsInLayout();
                chef_img.setVisibility(View.INVISIBLE);
                txt.setVisibility(View.INVISIBLE);
                new load().execute();
            }
        });

        new load().execute();
    }


    public static void re_write(int pos, String token2, String dish2, String email2, String image2,String name2, String id2)
    {   name.remove(pos);
        img.remove(pos);
        mail.remove(pos);
        c_name.remove(pos);
        token.remove(pos);
        id.remove(pos);
        LinearLayoutManager ordered_layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        RecyclerView chef_order = r;
        chef_order.setLayoutManager(ordered_layoutManager);
        chef_order ordered_adapter = new chef_order(context, name, img, mail, c_name, token, id);
        chef_order.setAdapter(ordered_adapter);

    do_in_back(token2, dish2, email2, image2, name2, id2);

    }

static void do_in_back(String token2, String dish2, String email2, String image2,String name2, String id)
{
    System.out.println("inside do_in_back");
    StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://script.google.com/macros/s/AKfycbyiNcLUOtC9Sx9cowD0Hwz_KmMxUpOnTx5iGzvW2iWFuTFSPfxkodgL77i2tjFqgiHu/exec",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                            loading.dismiss();
//                            Toast.makeText(AddUser.this,response,Toast.LENGTH_LONG).show();
                    System.out.println("xxxxxxxxxxxxx" + response);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("xxxxxxxxxxxxx" + error);
//                            Toast.makeText(AddUser.this,error.toString(),Toast.LENGTH_LONG).show();
                }
            }){
        @Override
        protected Map<String,String> getParams(){
            Map<String,String> params = new HashMap<String, String>();
            params.put("action","firebaseNotification");
            params.put("dish", dish2);
            params.put("img", image2);
            params.put("email", email2);
            params.put("name", name2);
            params.put("id", id);
            params.put("token", token2);
//                        System.out.println("bbbbbbbbbb " + b.getString("name") + " " + b.getString("price") + " " + b.getString("desc") + " " + b.getString("time") + " " + sharedPreferences.getString("key", ""));
            return params;
        }

    };

    int socketTimeout = 30000; // 30 seconds. You can change it
    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    stringRequest.setRetryPolicy(policy);
    RequestQueue requestQueue = Volley.newRequestQueue(context2);
    requestQueue.add(stringRequest);

    }

    class load extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            shimmerFrameLayout.startShimmer();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if(name.size()!=0)
            {LinearLayoutManager ordered_layoutManager = new LinearLayoutManager(chef_screen.this, LinearLayoutManager.VERTICAL, false);
            RecyclerView chef_order = r;
            shimmerFrameLayout.setVisibility(View.INVISIBLE);
            shimmerFrameLayout.stopShimmer();
            chef_order.setLayoutManager(ordered_layoutManager);
            chef_order ordered_adapter = new chef_order(chef_screen.this, name, img, mail, c_name, token, id);
            chef_order.setAdapter(ordered_adapter);}
            else
            {shimmerFrameLayout.setVisibility(View.INVISIBLE);
            shimmerFrameLayout.stopShimmer();
            chef_img.setVisibility(View.VISIBLE);
            txt.setVisibility(View.VISIBLE);}
        }

        @Override
        protected Void doInBackground(Void... voids) {
            SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
            try {
                db = sheet_comms.readAllData("sheet4");
                System.out.println("db length " + db.length());
                if (db != null) {
                    array = db.getJSONArray("records");}
                if(array.length()!=0)
                {for(int i=0;i<array.length();i++)
                {if(sharedPreferences.getString("key", "").equals(array.getJSONObject(i).get("id").toString()))
                {name.add(array.getJSONObject(i).get("dish").toString());
                img.add(array.getJSONObject(i).get("img").toString());
                mail.add(array.getJSONObject(i).get("email").toString());
                token.add(array.getJSONObject(i).get("token").toString());
                c_name.add(array.getJSONObject(i).get("name_").toString());
                id.add(array.getJSONObject(i).get("id").toString());}}

                for(int i=0;i<name.size();i++)
                {System.out.println(name.get(i) + "  pppppp");
                    System.out.println(array.getJSONObject(i+1) + "  pppppp");}}

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onBackPressed()
    {super.onBackPressed();
        finishAffinity();}
}