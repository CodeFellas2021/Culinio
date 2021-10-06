package com.example.cibo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class menu_expand extends AppCompatActivity {
    private int PICK_IMAGE_REQUEST = 1;
    ImageButton img, tick, delete;
    EditText name, price, desc;
    String user_image, p_name, p_price, p_desc, p_img;
    Bitmap rbitmap;
    TextView f_time;
    RelativeLayout layout;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_expand);
        layout = findViewById(R.id.me_relative);
        progressBar = findViewById(R.id.me_progress);
        SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
        img = findViewById(R.id.me_image);
        tick = findViewById(R.id.me_tick);
        name = findViewById(R.id.me_f_name);
        price = findViewById(R.id.me_price);
        desc = findViewById(R.id.me_desc);
        f_time = findViewById(R.id.me_time);
        delete = findViewById(R.id.me_delete);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        user_image = b.getString("img");
        byte[] bytes = Base64.decode(user_image, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        img.setImageBitmap(bitmap);
        name.setText(b.getString("name"));
        price.setText(b.getString("price"));
        desc.setText(b.getString("desc"));
        f_time.setText(b.getString("time"));

        p_name = b.getString("name");
        p_price = b.getString("price");
        p_desc = b.getString("desc");
        p_img = user_image;

    delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new load2().execute();
        }
    });

    img.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
    });

    tick.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new load().execute();
            Toast.makeText(menu_expand.this, "Updated successfully!", Toast.LENGTH_SHORT).show();
        }
    });

    }

    class load2 extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            layout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            layout.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            Toast.makeText(menu_expand.this, "Deleted successfully!", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Intent intent = getIntent();
            Bundle b = intent.getExtras();
            SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
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
                    params.put("action","delete_menu");
                    params.put("name", b.getString("name"));
                    params.put("price",b.getString("price"));
                    params.put("desc",b.getString("desc"));
                    params.put("time", b.getString("time"));
                    params.put("id", sharedPreferences.getString("key", ""));
                    System.out.println("bbbbbbbbbb " + b.getString("name") + " " + b.getString("price") + " " + b.getString("desc") + " " + b.getString("time") + " " + sharedPreferences.getString("key", ""));
                    return params;
                }

            };

            int socketTimeout = 30000; // 30 seconds. You can change it
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            RequestQueue requestQueue = Volley.newRequestQueue(menu_expand.this);
            requestQueue.add(stringRequest);
            return null;
        }
    }

    class load extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            layout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            layout.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Intent intent = getIntent();
            Bundle b = intent.getExtras();
            String f_time = b.getString("time");
            SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);

            StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"https://script.google.com/macros/s/AKfycbyiNcLUOtC9Sx9cowD0Hwz_KmMxUpOnTx5iGzvW2iWFuTFSPfxkodgL77i2tjFqgiHu/exec",
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
                    params.put("action","delete_menu");
                    params.put("name", b.getString("name"));
                    params.put("price",b.getString("price"));
                    params.put("desc",b.getString("desc"));
                    params.put("time", b.getString("time"));
                    params.put("id", sharedPreferences.getString("key", ""));
                    System.out.println("bbbbbbbbbb " + b.getString("name") + " " + b.getString("price") + " " + b.getString("desc") + " " + b.getString("time") + " " + sharedPreferences.getString("key", ""));
                    return params;
                }

            };

            int socketTimeout = 30000; // 30 seconds. You can change it
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest1.setRetryPolicy(policy);
            RequestQueue requestQueue = Volley.newRequestQueue(menu_expand.this);
            requestQueue.add(stringRequest1);


            StringRequest stringRequest2 = new StringRequest(Request.Method.POST,"https://script.google.com/macros/s/AKfycbyiNcLUOtC9Sx9cowD0Hwz_KmMxUpOnTx5iGzvW2iWFuTFSPfxkodgL77i2tjFqgiHu/exec",
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
                    params.put("action","insert");
//                    params.put("pname", p_name);
//                    params.put("pprice",p_price);
//                    params.put("pdesc",p_desc);
//                    params.put("pimage",p_img);
//                    params.put("ptime", f_time);
                    params.put("fname", name.getText().toString());
                    params.put("fprice", price.getText().toString());
                    params.put("fdesc", desc.getText().toString());
                    params.put("fimage", user_image);
                    params.put("ftime", f_time);
                    params.put("id", sharedPreferences.getString("key", ""));
                    System.out.println("bbbbbbbbbb " + price.getText().toString());
                    return params;
                }

            };

            int socketTimeout1 = 30000; // 30 seconds. You can change it
            RetryPolicy policy2 = new DefaultRetryPolicy(socketTimeout1,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest2.setRetryPolicy(policy2);
            RequestQueue requestQueue1 = Volley.newRequestQueue(menu_expand.this);
            requestQueue1.add(stringRequest2);
            return null;
        }
    }

//    void update_req()
//    {
////        String name = this.name.getText().toString();
////        String price = this.price.getText().toString();
////        String desc = this.desc.getText().toString();
////        String img = this.user_image;
//
//
//
//    }

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
                this.user_image = getStringImage(rbitmap);

                System.out.println("kkkkk " + this.user_image);
//                userImage = userImage2.replaceAll("\\+","@");\
//
                img.setImageBitmap(rbitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(menu_expand.this, organization_profile.class));
    }
}