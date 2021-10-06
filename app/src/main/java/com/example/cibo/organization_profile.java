package com.example.cibo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class organization_profile extends AppCompatActivity {
    private int PICK_IMAGE_REQUEST = 1;
    Bitmap rbitmap;
    String userImage;
    ImageButton b_menu_add, l_menu_add, d_menu_add, bev_menu_add, op_refresh;
    Button id_btn, chef_btn;
    SharedPreferences sharedPreferences;
    ShimmerFrameLayout frameLayout, lunchframeLayout, dinnerframeLayout, beverageframeLayout;
    ArrayList<String> mNames = new ArrayList<>();
    ArrayList<String> mImageUrls = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    ArrayList<String> bdesc = new ArrayList<>();
    ArrayList<String> b_time = new ArrayList<>();

    ArrayList<String> imagesurl2 = new ArrayList<>();
    ArrayList<String> image_name2 = new ArrayList<>();
    ArrayList<String> price2 = new ArrayList<>();
    ArrayList<String> ldesc = new ArrayList<>();
    ArrayList<String> l_time = new ArrayList<>();

    ArrayList<String> image3 = new ArrayList<>();
    ArrayList<String> name3 = new ArrayList<>();
    ArrayList<String> price3 = new ArrayList<>();
    ArrayList<String> ddesc = new ArrayList<>();
    ArrayList<String> dtime = new ArrayList<>();

    ArrayList<String> names4 = new ArrayList<>();
    ArrayList<String> images4 = new ArrayList<>();
    ArrayList<String> price4 = new ArrayList<>();
    ArrayList<String> bev_desc = new ArrayList<>();
    ArrayList<String> bev_time = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_profile);
        id_btn = findViewById(R.id.id_btn);
        sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
        id_btn.setText(sharedPreferences.getString("key", ""));
        frameLayout = findViewById(R.id.op_shimmer_layout);
        op_refresh = findViewById(R.id.op_refresh);
        lunchframeLayout = findViewById(R.id.op_lunch_shimmer_layout);
        dinnerframeLayout = findViewById(R.id.op_shimmer_dinner_shimmer_layout);
        beverageframeLayout = findViewById(R.id.op_bev_shimmer_layout);
        b_menu_add = findViewById(R.id.breakfast_menu_add);
        l_menu_add = findViewById(R.id.lunch_menu_add);
        d_menu_add = findViewById(R.id.dinner_menu_add);
        bev_menu_add = findViewById(R.id.beverage_menu_add);
        chef_btn = findViewById(R.id.chef_details_btn);

        op_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNames.clear();mImageUrls.clear();price.clear();bdesc.clear();b_time.clear();
                RecyclerView recyclerView = findViewById(R.id.op_recycler_view);
                recyclerView.removeAllViewsInLayout();

                imagesurl2.clear();image_name2.clear();price2.clear();ldesc.clear();l_time.clear();
                RecyclerView lunchrecyclerView = findViewById(R.id.lunch_recycler_view);
                lunchrecyclerView.removeAllViewsInLayout();

                image3.clear();name3.clear();price3.clear();ddesc.clear();dtime.clear();
                RecyclerView dinnerrecyclerView = findViewById(R.id.dinner_recycler_view);
                dinnerrecyclerView.removeAllViewsInLayout();

                names4.clear();images4.clear();price4.clear();bev_desc.clear();bev_time.clear();
                RecyclerView beveragerecyclerView = findViewById(R.id.beverage_recycler_view);
                beveragerecyclerView.removeAllViewsInLayout();

                new load().execute();
            }
        });

        id_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", id_btn.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(organization_profile.this, "Copied To Clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        chef_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(organization_profile.this, chef_details.class));
            }
        });


        bev_menu_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(organization_profile.this, add_to_menu.class);
                intent.putExtra("time", "Beverage");
                startActivity(intent);
            }
        });

        d_menu_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(organization_profile.this, add_to_menu.class);
                intent.putExtra("time", "Dinner");
                startActivity(intent);
            }
        });

        l_menu_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(organization_profile.this, add_to_menu.class);
            intent.putExtra("time", "lunch");
            startActivity(intent);
            }
        });

        b_menu_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(organization_profile.this, add_to_menu.class);
                intent.putExtra("time", "breakfast");
                startActivity(intent);
            }
        });

        new load().execute();

    }

    void load_data()
    {
//        System.out.println(sharedPreferences.getString("key", "") + " fffffffffffff");
//


        SharedPreferences sharedPreferences2 = getSharedPreferences("orga_key", MODE_PRIVATE);
        String k = sharedPreferences2.getString("key", "");
        JSONObject db;
        JSONArray array = null;
        try {
            db = sheet_comms.readAllData("Sheet2");
            assert db != null;
            {array = db.getJSONArray("records");
            System.out.println("ppppppp " + db);}
        } catch (IOException | JSONException e) {
            System.out.println(e + "llooooooggggggg");
        }

//            System.out.print( " fffffffffffff " + db);
            if(array!=null)
            {for(int i=0;i<array.length();i++)
            {
                try {
                if(array.getJSONObject(i).get("id").toString().equals(k) && array.getJSONObject(i).get("ftime").toString().equals("breakfast"))
                {mImageUrls.add(array.getJSONObject(i).get("image").toString());
                    mNames.add(array.getJSONObject(i).get("fname").toString());
                    price.add(array.getJSONObject(i).get("fprice").toString());
                    bdesc.add(array.getJSONObject(i).get("fdesc").toString());
                    b_time.add("breakfast");}

                if(array.getJSONObject(i).get("id").toString().equals(k) && array.getJSONObject(i).get("ftime").toString().equals("lunch"))
                {imagesurl2.add(array.getJSONObject(i).get("image").toString());
                    image_name2.add(array.getJSONObject(i).get("fname").toString());
                    price2.add(array.getJSONObject(i).get("fprice").toString());
                    ldesc.add(array.getJSONObject(i).get("fdesc").toString());
                    l_time.add("lunch");}

                if(array.getJSONObject(i).get("id").toString().equals(k) && array.getJSONObject(i).get("ftime").toString().equals("Dinner"))
                {image3.add(array.getJSONObject(i).get("image").toString());
                    name3.add(array.getJSONObject(i).get("fname").toString());
                    price3.add(array.getJSONObject(i).get("fprice").toString());
                    ddesc.add(array.getJSONObject(i).get("fdesc").toString());
                dtime.add("Dinner");}

                if(array.getJSONObject(i).get("id").toString().equals(k) && array.getJSONObject(i).get("ftime").toString().equals("Beverage"))
                {images4.add(array.getJSONObject(i).get("image").toString());
                    names4.add(array.getJSONObject(i).get("fname").toString());
                    price4.add(array.getJSONObject(i).get("fprice").toString());
                    bev_desc.add(array.getJSONObject(i).get("fdesc").toString());
                bev_time.add("Beverage");}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                }}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 1, stream);
//                byte[] bytes = stream.toByteArray();
//                bit = Base64.encodeToString(bytes, Base64.URL_SAFE);
////                encoded = new String(Base64.encodeBase64(bytes), "UTF-8");
//                Toast.makeText(this, bit, Toast.LENGTH_SHORT).show();
//                System.out.println("cccccccccccc " + bit);
//                Log.i("ssssssssss ", bit);
//                Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                rbitmap = getResizedBitmap(bitmap2,250);//Setting the Bitmap to ImageView
//                userImage = getStringImage(rbitmap);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                rbitmap = getResizedBitmap(bitmap,250);//Setting the Bitmap to ImageView
                String userImage2 = getStringImage(rbitmap);
                System.out.println("kkkkk " + userImage);
//                userImage = userImage2.replaceAll("\\+","@");\
//
//                img_btn.setImageBitmap(rbitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void recycler()
    {LinearLayoutManager layoutManager = new LinearLayoutManager(organization_profile.this, LinearLayoutManager.HORIZONTAL, false);
    RecyclerView recyclerView = findViewById(R.id.op_recycler_view);
    recyclerView.setLayoutManager(layoutManager);
    recyclerviewadapter adapter = new recyclerviewadapter(organization_profile.this, mNames, mImageUrls, price, bdesc, b_time);
    recyclerView.setAdapter(adapter);

    LinearLayoutManager lunchlayoutManager = new LinearLayoutManager(organization_profile.this, LinearLayoutManager.HORIZONTAL, false);
    RecyclerView lunchrecyclerView = findViewById(R.id.lunch_recycler_view);
    lunchrecyclerView.setLayoutManager(lunchlayoutManager);
    lunchrecyclerviewadapter lunchadapter = new lunchrecyclerviewadapter(organization_profile.this, image_name2, imagesurl2, price2, ldesc, l_time);
    lunchrecyclerView.setAdapter(lunchadapter);

    LinearLayoutManager dinnerlayoutManager = new LinearLayoutManager(organization_profile.this, LinearLayoutManager.HORIZONTAL, false);
    RecyclerView dinnerrecyclerView = findViewById(R.id.dinner_recycler_view);
    dinnerrecyclerView.setLayoutManager(dinnerlayoutManager);
    dinnerrecyclerviewadapter dinneradapter = new dinnerrecyclerviewadapter(organization_profile.this, name3, image3, price3, ddesc, dtime);
    dinnerrecyclerView.setAdapter(dinneradapter);

    LinearLayoutManager beveragelayoutManager = new LinearLayoutManager(organization_profile.this, LinearLayoutManager.HORIZONTAL, false);
    RecyclerView beveragerecyclerView = findViewById(R.id.beverage_recycler_view);
    beveragerecyclerView.setLayoutManager(beveragelayoutManager);
    beveragerecyclerviewadapter beverageadapter = new beveragerecyclerviewadapter(organization_profile.this, names4, images4, price4, bev_desc, bev_time);
    beveragerecyclerView.setAdapter(beverageadapter);
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }

    public void to_menu_expand(Bitmap image, String name, String price)
    {Intent intent = new Intent(organization_profile.this, menu_expand.class);
    intent.putExtra("image", image);
    intent.putExtra("name", name);
    intent.putExtra("price", price);
    startActivity(intent);
    }

    class load extends AsyncTask<Void, Void, Void>
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
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            frameLayout.setVisibility(View.INVISIBLE);
            frameLayout.stopShimmer();
            lunchframeLayout.setVisibility(View.INVISIBLE);
            lunchframeLayout.stopShimmer();
            dinnerframeLayout.setVisibility(View.INVISIBLE);
            dinnerframeLayout.stopShimmer();
            beverageframeLayout.setVisibility(View.INVISIBLE);
            beverageframeLayout.stopShimmer();
            if(mNames.size()!=0)
            {recycler();
            frameLayout.setVisibility(View.INVISIBLE);
            frameLayout.stopShimmer();
            lunchframeLayout.setVisibility(View.INVISIBLE);
            lunchframeLayout.stopShimmer();
            dinnerframeLayout.setVisibility(View.INVISIBLE);
            dinnerframeLayout.stopShimmer();
            beverageframeLayout.setVisibility(View.INVISIBLE);
            beverageframeLayout.stopShimmer();

            ConstraintLayout constraintLayout = findViewById(R.id.op_conslayout2);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.connect(R.id.lunch_txt,ConstraintSet.TOP,R.id.op_recycler_view,ConstraintSet.BOTTOM,0);
            constraintSet.connect(R.id.dinner_txt, ConstraintSet.TOP, R.id.lunch_recycler_view, ConstraintSet.BOTTOM, 0);
            constraintSet.connect(R.id.beverage_txt, ConstraintSet.TOP, R.id.dinner_recycler_view, ConstraintSet.BOTTOM, 0);
            constraintSet.applyTo(constraintLayout);}}

        @Override
        protected Void doInBackground(Void... voids) {
            load_data();
            return null;

        }
    }

    @Override
    public void onBackPressed()
    {super.onBackPressed();
        finishAffinity();}

}