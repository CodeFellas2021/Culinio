package com.example.cibo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class employee_menu extends AppCompatActivity{

    ArrayList<String> mNames = new ArrayList<>();
    ArrayList<String> mImageUrls = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    ArrayList<String> bdesc = new ArrayList<>();

    ArrayList<String> imagesurl2 = new ArrayList<>();
    ArrayList<String> image_name2 = new ArrayList<>();
    ArrayList<String> price2 = new ArrayList<>();
    ArrayList<String> ldesc = new ArrayList<>();

    ArrayList<String> image3 = new ArrayList<>();
    ArrayList<String> name3 = new ArrayList<>();
    ArrayList<String> price3 = new ArrayList<>();
    ArrayList<String> ddesc = new ArrayList<>();

    ArrayList<String> names4 = new ArrayList<>();
    ArrayList<String> images4 = new ArrayList<>();
    ArrayList<String> price4 = new ArrayList<>();
    ArrayList<String> bev_desc = new ArrayList<>();
    TextView lunch;
    ImageButton cup, refresh;
    ProgressBar pg;
    RelativeLayout relativeLayout;
    ShimmerFrameLayout frameLayout, lunchframeLayout, dinnerframeLayout, beverageframeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_menu);
        lunch = findViewById(R.id.me_lunch_txt);
        cup = findViewById(R.id.me_cup);
        frameLayout = findViewById(R.id.shimmer_layout);
        refresh = findViewById(R.id.em_refresh);
        lunchframeLayout = findViewById(R.id.lunch_shimmer_layout);
        dinnerframeLayout = findViewById(R.id.dinner_shimmer_layout);
        beverageframeLayout = findViewById(R.id.beverage_shimmer_layout);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNames.clear();mImageUrls.clear();price.clear();bdesc.clear();
                RecyclerView employee_recycler = findViewById(R.id.me_recycler_view);
                employee_recycler.removeAllViewsInLayout();

                imagesurl2.clear();image_name2.clear();price2.clear();ldesc.clear();
                RecyclerView l_employee_recycler = findViewById(R.id.me_lunch_recycler_view);
                l_employee_recycler.removeAllViewsInLayout();

                image3.clear();name3.clear();price3.clear();ddesc.clear();
                RecyclerView d_employee_recycler = findViewById(R.id.me_dinner_recycler_view);
                d_employee_recycler.removeAllViewsInLayout();

                names4.clear();images4.clear();price4.clear();bev_desc.clear();
                RecyclerView bev_employee_recycler = findViewById(R.id.me_beverage_recycler_view);
                bev_employee_recycler.removeAllViewsInLayout();

                new disp().execute();
            }
        });

        cup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(employee_menu.this, employee_orders.class));
            }
        });
        new disp().execute();

    }

    class disp extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            frameLayout.setVisibility(View.VISIBLE);
            frameLayout.startShimmer();
            lunchframeLayout.setVisibility(View.VISIBLE);
            lunchframeLayout.startShimmer();
            dinnerframeLayout.setVisibility(View.VISIBLE);
            dinnerframeLayout.startShimmer();
            beverageframeLayout.setVisibility(View.VISIBLE);
            beverageframeLayout.startShimmer();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            SharedPreferences sharedPreferences2 = getSharedPreferences("orga_key", MODE_PRIVATE);
            String k = sharedPreferences2.getString("key", "");
            JSONObject db;
            JSONArray array = null;
            try {
                db = sheet_comms.readAllData("Sheet2");
                assert db != null;
                array = db.getJSONArray("records");
                System.out.println("ppppppp " + db);
            } catch (IOException | JSONException e) {

            }

//            System.out.print( " fffffffffffff " + db);

            for(int i=0;i<array.length();i++)
            {
                try {
                    if(array.getJSONObject(i).get("id").toString().equals(k) && array.getJSONObject(i).get("ftime").toString().toLowerCase().equals("breakfast"))
                    {mImageUrls.add(array.getJSONObject(i).get("image").toString());
                        mNames.add(array.getJSONObject(i).get("fname").toString());
                        price.add(array.getJSONObject(i).get("fprice").toString());
                        bdesc.add(array.getJSONObject(i).get("fdesc").toString());}

                    if(array.getJSONObject(i).get("id").toString().equals(k) && array.getJSONObject(i).get("ftime").toString().toLowerCase().equals("lunch"))
                    {imagesurl2.add(array.getJSONObject(i).get("image").toString());
                        image_name2.add(array.getJSONObject(i).get("fname").toString());
                        price2.add(array.getJSONObject(i).get("fprice").toString());
                        ldesc.add(array.getJSONObject(i).get("fdesc").toString());}

                    if(array.getJSONObject(i).get("id").toString().equals(k) && array.getJSONObject(i).get("ftime").toString().toLowerCase().equals("dinner"))
                    {image3.add(array.getJSONObject(i).get("image").toString());
                        name3.add(array.getJSONObject(i).get("fname").toString());
                        price3.add(array.getJSONObject(i).get("fprice").toString());
                        ddesc.add(array.getJSONObject(i).get("fdesc").toString());}

                    if(array.getJSONObject(i).get("id").toString().equals(k) && array.getJSONObject(i).get("ftime").toString().toLowerCase().equals("beverage"))
                    {images4.add(array.getJSONObject(i).get("image").toString());
                        names4.add(array.getJSONObject(i).get("fname").toString());
                        price4.add(array.getJSONObject(i).get("fprice").toString());
                        bev_desc.add(array.getJSONObject(i).get("fdesc").toString());}


                } catch (JSONException e) {
                    System.out.println(e + "llooooooggggggg");
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            frameLayout.stopShimmer();
            frameLayout.setVisibility(View.INVISIBLE);
            lunchframeLayout.stopShimmer();
            lunchframeLayout.setVisibility(View.INVISIBLE);
            dinnerframeLayout.stopShimmer();
            dinnerframeLayout.setVisibility(View.INVISIBLE);
            beverageframeLayout.stopShimmer();
            beverageframeLayout.setVisibility(View.INVISIBLE);
            ConstraintLayout constraintLayout = findViewById(R.id.op_conslayout);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.connect(R.id.me_lunch_txt,ConstraintSet.TOP,R.id.me_recycler_view,ConstraintSet.BOTTOM,0);
            constraintSet.connect(R.id.me_dinner_txt, ConstraintSet.TOP, R.id.me_lunch_recycler_view, ConstraintSet.BOTTOM, 0);
            constraintSet.connect(R.id.me_beverage_txt, ConstraintSet.TOP, R.id.me_dinner_recycler_view, ConstraintSet.BOTTOM, 0);
            constraintSet.applyTo(constraintLayout);
            recycler();

        }

    }

    void recycler()
    {   LinearLayoutManager layoutManager = new LinearLayoutManager(employee_menu.this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView employee_recycler = findViewById(R.id.me_recycler_view);
        employee_recycler.setLayoutManager(layoutManager);
        employee_recycler adapter = new employee_recycler(employee_menu.this, mNames, mImageUrls, price, bdesc);
        employee_recycler.setAdapter(adapter);

        LinearLayoutManager l_layoutManager = new LinearLayoutManager(employee_menu.this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView l_employee_recycler = findViewById(R.id.me_lunch_recycler_view);
        l_employee_recycler.setLayoutManager(l_layoutManager);
        l_employee_recycler l_adapter = new l_employee_recycler(employee_menu.this, image_name2, imagesurl2, price2, ldesc);
        l_employee_recycler.setAdapter(l_adapter);

        LinearLayoutManager d_layoutManager = new LinearLayoutManager(employee_menu.this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView d_employee_recycler = findViewById(R.id.me_dinner_recycler_view);
        d_employee_recycler.setLayoutManager(d_layoutManager);
        d_employee_recycler d_adapter = new d_employee_recycler(employee_menu.this, name3, image3, price3, ddesc);
        d_employee_recycler.setAdapter(d_adapter);

        LinearLayoutManager bev_layoutManager = new LinearLayoutManager(employee_menu.this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView bev_employee_recycler = findViewById(R.id.me_beverage_recycler_view);
        bev_employee_recycler.setLayoutManager(bev_layoutManager);
        bev_employee_recycler bev_adapter = new bev_employee_recycler(employee_menu.this, names4, images4, price4, bev_desc);
        bev_employee_recycler.setAdapter(bev_adapter);


    }

    @Override
    public void onBackPressed()
    {super.onBackPressed();
        finishAffinity();}
}