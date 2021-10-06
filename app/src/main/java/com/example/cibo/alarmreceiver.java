package com.example.cibo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class alarmreceiver extends BroadcastReceiver {
    JSONObject db = null;
    JSONArray array = null;
    ArrayList<String> s_dish = new ArrayList<>();
    String token;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("in on receive");

//        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
//            if (!TextUtils.isEmpty(token)) {
////                hw.setText(token);
//                this.token = token;
//                System.out.println("kkkkkk " + token);
//            } else{
////                Log.w(TAG, "token should not be null...");
//            }
//        }).addOnFailureListener(e -> {
//            //handle e
//        }).addOnCanceledListener(() -> {
//            //handle cancel
//        }).addOnCompleteListener(task -> System.out.println("blablabla"));

        SharedPreferences sharedPreferences2 = context.getSharedPreferences("orga_key", MODE_PRIVATE);
        SharedPreferences sharedPreferences = context.getSharedPreferences("orga_order", MODE_PRIVATE);
        int flag = 0;
        try {
            db = sheet_comms.readAllData("sheet5");

            if (db != null) {
                array = db.getJSONArray("records");}
            System.out.println("in on receive length " + array.length());
            if(array!=null)
            {for(int i=0;i<array.length();i++)
            {if(sharedPreferences2.getString("key", "").equals(array.getJSONObject(i).get("id").toString()) && sharedPreferences2.getString("emp_email", "").equals(array.getJSONObject(i).get("email").toString()) && sharedPreferences2.getString("emp_name", "").equals(array.getJSONObject(i).get("name").toString()) && sharedPreferences.getString("dish", "").equals(array.getJSONObject(i).get("dish").toString()))
            {flag = 1;
            break;}}}
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        if(flag==1)
        {System.out.println("alarmreceive " + sharedPreferences.getString("dish", ""));
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://script.google.com/macros/s/AKfycbyiNcLUOtC9Sx9cowD0Hwz_KmMxUpOnTx5iGzvW2iWFuTFSPfxkodgL77i2tjFqgiHu/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("response " + response);
                        delete_schedule(context);
//                            loading.dismiss();
//                            Toast.makeText(AddUser.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                  Toast.makeText(AddUser.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("action", "ordered_dish");
                params.put("dish", sharedPreferences.getString("dish", ""));
                params.put("img", sharedPreferences.getString("img", ""));
                params.put("email", sharedPreferences.getString("email", ""));
                params.put("name", sharedPreferences.getString("name", ""));
                params.put("id", sharedPreferences.getString("id", ""));
                params.put("price", sharedPreferences.getString("price", ""));
                params.put("token", sharedPreferences2.getString("token", ""));
              System.out.println("tokent " + token);
                return params;
            }

        };

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);}

    else
        {SharedPreferences sharedPreferences1  = context.getSharedPreferences("orga_key", MODE_PRIVATE);
        new emp_order().get_the_earliest_schedule2(sharedPreferences1, sharedPreferences, context);}


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void delete_schedule(Context context)
    {System.out.println("inside delete_schedule");
        SharedPreferences sharedPreferences = context.getSharedPreferences("orga_order", MODE_PRIVATE);

        try {
            db = sheet_comms.readAllData("Sheet5");
            if (db != null && array !=null) {
                array = db.getJSONArray("records");}

            if (array != null) {
                for(int i=0;i<array.length();i++)
                {if(sharedPreferences.getString("key", "").equals(array.getJSONObject(i).get("id").toString()) && sharedPreferences.getString("emp_name", "").equals(array.getJSONObject(i).get("name").toString()) && sharedPreferences.getString("emp_email", "").equals(array.getJSONObject(i).get("email").toString()))
                {s_dish.add(array.getJSONObject(i).get("dish").toString());}}
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        System.out.println("alarmreceive " + sharedPreferences.getString("dish", ""));
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://script.google.com/macros/s/AKfycbyiNcLUOtC9Sx9cowD0Hwz_KmMxUpOnTx5iGzvW2iWFuTFSPfxkodgL77i2tjFqgiHu/exec",
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {
                        System.out.println("responseeee " + response);
                        SharedPreferences sharedPreferences1  = context.getSharedPreferences("orga_key", MODE_PRIVATE);
                        new emp_order().get_the_earliest_schedule2(sharedPreferences1, sharedPreferences, context);
//                            loading.dismiss();
//                            Toast.makeText(AddUser.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//              Toast.makeText(AddUser.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("action", "delete_scheduled");
                params.put("dish", sharedPreferences.getString("dish", ""));
                params.put("hour", sharedPreferences.getString("hour", ""));
                params.put("email", sharedPreferences.getString("email", ""));
                params.put("name", sharedPreferences.getString("name", ""));
                params.put("id", sharedPreferences.getString("id", ""));
                params.put("minute", sharedPreferences.getString("minute", ""));
//                        System.out.println("bbbbbbbbbb " + b.getString("name") + " " + b.getString("price") + " " + b.getString("desc") + " " + b.getString("time") + " " + sharedPreferences.getString("key", ""));
                return params;
            }

        };

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


//        try {
//            Thread.sleep(2000);
//            get_the_earliest_schedule(context);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }



    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    void get_the_earliest_schedule(Context context)
//    {SharedPreferences sharedPreferences = context.getSharedPreferences("orga_key", MODE_PRIVATE);
//        try {
//            db = sheet_comms.readAllData("Sheet5");
//            if (db != null) {
//                array = db.getJSONArray("records");}
//
//            for(int i=0;i<array.length();i++)
//            {if(sharedPreferences.getString("key", "").equals(array.getJSONObject(i).get("id").toString()) && sharedPreferences.getString("emp_name", "").equals(array.getJSONObject(i).get("name").toString()) && sharedPreferences.getString("emp_email", "").equals(array.getJSONObject(i).get("email").toString()))
//            {s_dish.add(array.getJSONObject(i).get("dish").toString());
//                s_price.add(array.getJSONObject(i).get("price").toString());
//                s_image.add(array.getJSONObject(i).get("img").toString());
//                s_email.add(array.getJSONObject(i).get("email").toString());
//                s_name.add(array.getJSONObject(i).get("name").toString());
//                s_id.add(array.getJSONObject(i).get("id").toString());
//                s_token.add(array.getJSONObject(i).get("token").toString());
//                s_hour.add(array.getJSONObject(i).get("hour").toString());
//                s_minute.add(array.getJSONObject(i).get("minute").toString());}}
//
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }
//
//        Calendar calendar = Calendar.getInstance();
//        Calendar calendar2 = Calendar.getInstance();
//        int small = 0;
//        if(s_hour.size()!=1)
//        {calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(s_hour.get(0)));
//            calendar.set(Calendar.MINUTE, Integer.parseInt(s_minute.get(0)));
//            calendar.set(Calendar.SECOND, 0);
//
//            for(int i=0;i<s_hour.size();i++)
//            {calendar2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(s_hour.get(i)));
//                calendar2.set(Calendar.MINUTE, Integer.parseInt(s_minute.get(i)));
//                calendar2.set(Calendar.SECOND, 0);
//
//                System.out.println("now: " + calendar2 + " " + s_dish.get(i) + "\n");
//                System.out.println("after now: " + calendar + " " + s_dish.get(small) + "\n");
//                System.out.println("i val: " + i);
//
//                if(calendar.compareTo(calendar2)>0)
//                {System.out.println("priority1: " + calendar.get(Calendar.MINUTE));
//                    small = i;
//                    System.out.println("priority: " + s_dish.get(small));
//                    calendar.set(Calendar.HOUR_OF_DAY, calendar2.get(Calendar.HOUR_OF_DAY));
//                    calendar.set(Calendar.MINUTE, calendar2.get(Calendar.MINUTE));
//                    calendar.set(Calendar.SECOND, 0);}}
//
////    if(current_time.compareTo(calendar.getTime())>=0)
////    {calendar.set(Calendar.HOUR_OF_DAY,current_time.getHours());
////    calendar.set(Calendar.MINUTE,current_time.getMinutes() + 1);
////    calendar.set(Calendar.SECOND, 0);}
//
//            System.out.println("hourofday " + calendar.get(Calendar.MINUTE) + " " + s_dish.get(small));
//
//            SharedPreferences sharedPreferences1 = context.getSharedPreferences("orga_order", MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences1.edit();
//            editor.putString("action","ordered_dish");
//            editor.putString("dish", s_dish.get(small));
//            editor.putString("img", s_image.get(small));
//            editor.putString("price", s_price.get(small));
//            editor.putString("email", s_email.get(small));
//            editor.putString("name", s_name.get(small));
//            editor.putString("id", s_id.get(small));
//            editor.putString("hour", String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
//            editor.putString("minute", String.valueOf(calendar.get(Calendar.MINUTE)));
//            editor.apply();}
//
//        else
//        {System.out.println("s_hour.size()==1");
//            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(s_hour.get(0)));
//            calendar.set(Calendar.MINUTE, Integer.parseInt(s_minute.get(0)));
//            calendar.set(Calendar.SECOND, 0);
//            System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
//            System.out.println(calendar.get(Calendar.MINUTE));
//            System.out.println(s_dish.get(0));
////            Intent intent2 = getIntent();
////            Bundle b = intent2.getExtras();
////            System.out.println(b.getString("name"));
//            SharedPreferences sharedPreferences3 = context.getSharedPreferences("orga_key", MODE_PRIVATE);
//            SharedPreferences sharedPreferences1 = context.getSharedPreferences("orga_order", MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences1.edit();
//            editor.putString("action","ordered_dish");
//            editor.putString("dish", s_dish.get(0));
//            editor.putString("img", s_image.get(0));
//            editor.putString("price", s_price.get(0));
//            editor.putString("email", sharedPreferences3.getString("emp_email", ""));
//            editor.putString("name", sharedPreferences3.getString("emp_name", ""));
//            editor.putString("id", sharedPreferences3.getString("key", ""));
//            editor.putString("hour", String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
//            editor.putString("minute", String.valueOf(calendar.get(Calendar.MINUTE)));
//            editor.apply();}
//        if(s_dish.size()!=0)
//        {   AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//            {alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), on_receive_copy(context));
//            }
//            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
//            {alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), on_receive_copy(context));
//            }
//            else
//            {alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), on_receive_copy(context));}}
//    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    PendingIntent on_receive_copy(Context context)
//    {   SharedPreferences sharedPreferences = context.getSharedPreferences("orga_order", MODE_PRIVATE);
//        SharedPreferences sharedPreferences2 = context.getSharedPreferences("orga_key", MODE_PRIVATE);
//        System.out.println("alarmreceive " + sharedPreferences.getString("dish", ""));
//        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://script.google.com/macros/s/AKfycbyiNcLUOtC9Sx9cowD0Hwz_KmMxUpOnTx5iGzvW2iWFuTFSPfxkodgL77i2tjFqgiHu/exec",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        System.out.println("response " + response);
////                            loading.dismiss();
////                            Toast.makeText(AddUser.this,response,Toast.LENGTH_LONG).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
////                  Toast.makeText(AddUser.this,error.toString(),Toast.LENGTH_LONG).show();
//                    }
//                }){
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("action", "ordered_dish");
//                params.put("dish", sharedPreferences.getString("dish", ""));
//                params.put("img", sharedPreferences.getString("img", ""));
//                params.put("email", sharedPreferences.getString("email", ""));
//                params.put("name", sharedPreferences.getString("name", ""));
//                params.put("id", sharedPreferences.getString("id", ""));
//                params.put("price", sharedPreferences.getString("price", ""));
//                params.put("token", sharedPreferences2.getString("token", ""));
////              System.out.println("bbbbbbbbbb " + b.getString("name") + " " + b.getString("price") + " " + b.getString("desc") + " " + b.getString("time") + " " + sharedPreferences.getString("key", ""));
//                return params;
//            }
//
//        };
//
//        int socketTimeout = 30000; // 30 seconds. You can change it
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(policy);
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(stringRequest);
//
//
//        delete_schedule(context);
//        return null;
//    }

}
