package com.example.cibo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class add_to_menu extends AppCompatActivity {
    private int PICK_IMAGE_REQUEST = 1;
    Bitmap rbitmap;
    String userImage, f_name, f_price, f_desc, f_time;
    ImageButton img, tick_btn;
    EditText name;
    EditText price;
    EditText desc;
    TextView time;
    RelativeLayout relativeLayout;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_menu);
        relativeLayout = findViewById(R.id.atm_relative);
        progressBar = findViewById(R.id.atm_progress);
        img = findViewById(R.id.atm_image);
        tick_btn = findViewById(R.id.atm_tick);
        name = findViewById(R.id.atm_f_name);
        price = findViewById(R.id.atm_price);
        desc = findViewById(R.id.atm_desc);
        time = findViewById(R.id.atm_time);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        f_time = b.getString("time");
        time.setText(f_time);

        tick_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flag = 0;
                f_name = name.getText().toString().trim();
                f_price = price.getText().toString().trim();
                f_desc = desc.getText().toString().trim();
                ArrayList<String> to_show = new ArrayList<>();
//              System.out.println("hhhhhh " + userImage);
                if(userImage!=null && userImage.length()!=0)
                {flag = 0;}
                else
                {to_show.add("Upload image");
                flag = 1;}

                if(f_name!=null && f_name.length()!=0)
                {flag = 0;}
                else
                {to_show.add("Enter name");
                flag = 1;}

                if(f_price!=null && f_price.length()!=0 && flag == 0)
                {flag = 0;}
                else
                {to_show.add("Enter price");
                flag = 1;}

                if(f_desc!=null && f_desc.length()!=0 && flag == 0)
                {flag = 0;}
                else
                {to_show.add("Enter description");
                flag = 1;}

                if(flag == 0)
                {new load().execute();
                startActivity(new Intent(add_to_menu.this, organization_profile.class));}

                else
                    Toast.makeText(add_to_menu.this, to_show.get(0), Toast.LENGTH_SHORT).show();
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
            f_name = name.getText().toString();
            f_price = price.getText().toString();
            f_desc = desc.getText().toString();
            send_req(f_name, f_price, f_desc, userImage, f_time);
            return null;
        }
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
                userImage = getStringImage(rbitmap);

                System.out.println("kkkkk " + userImage);
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

    void send_req(String f_name, String f_price, String f_desc, String f_image, String time)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://script.google.com/macros/s/AKfycbyiNcLUOtC9Sx9cowD0Hwz_KmMxUpOnTx5iGzvW2iWFuTFSPfxkodgL77i2tjFqgiHu/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                            loading.dismiss();
//                            Toast.makeText(AddUser.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(AddUser.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("action","insert");
                params.put("fname", f_name);
                params.put("fprice",f_price);
                params.put("fdesc",f_desc);
                params.put("fimage",f_image);
                params.put("ftime", time);
                params.put("id", sharedPreferences.getString("key", ""));
                System.out.println("bbbbbbbbbb " + f_image);
                return params;
            }

        };

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(add_to_menu.this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(add_to_menu.this, organization_profile.class));
    }
}