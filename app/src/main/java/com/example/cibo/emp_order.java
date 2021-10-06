package com.example.cibo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cibo.databinding.ActivityMainBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class emp_order extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    JSONObject db = null;
    JSONArray array = null;
    ArrayList<String> s_dish = new ArrayList<>();
    ArrayList<String> s_image = new ArrayList<>();
    ArrayList<String> s_price = new ArrayList<>();
    ArrayList<String> s_email = new ArrayList<>();
    ArrayList<String> s_name = new ArrayList<>();
    ArrayList<String> s_id = new ArrayList<>();
    ArrayList<String> s_token = new ArrayList<>();
    ArrayList<String> s_hour = new ArrayList<>();
    ArrayList<String> s_minute = new ArrayList<>();
    ImageView img;
    TextView name, price, desc;
    Button order;
    EditText hh, mm;
    int hour, minute;
    private Calendar calendar;
    AlarmManager alarmManager;
    private ActivityMainBinding binding;
    private MaterialTimePicker picker;
    String img_string, time_of_food, which_time;
    Context context;
    Bundle bundle;
    RelativeLayout relativeLayout;
    ProgressBar progressBar;
    Date breakfast_end_time, breakfast_start_time, lunch_start_time, lunch_end_time, dinner_start_time, dinner_end_time, current_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_order);
        context = this;
        hh = findViewById(R.id.hh);
        mm = findViewById(R.id.mm);
        relativeLayout = findViewById(R.id.eo_relative);
        progressBar = findViewById(R.id.eo_progress);
        bundle = savedInstanceState;
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        img = findViewById(R.id.eo_image);
        name = findViewById(R.id.eo_f_name);
        price = findViewById(R.id.eo_price);
        desc = findViewById(R.id.eo_desc);
        order = findViewById(R.id.place_order_btn);

        Intent intent2 = getIntent();
        Bundle b = intent2.getExtras();
        img_string = b.getString("img");
        byte[] bytes = Base64.decode(img_string, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        img.setImageBitmap(bitmap);
        name.setText(b.getString("name"));
        price.setText(b.getString("price"));
        desc.setText(b.getString("desc"));
        time_of_food = b.getString("time");

        SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String c_string_time = sdf.format(new Date());

        try {
            breakfast_start_time = sdf.parse("08:00:00");
            breakfast_end_time = sdf.parse("11:00:00");

            lunch_start_time = sdf.parse("12:00:00");
            lunch_end_time = sdf.parse("15:00:00");

            dinner_start_time = sdf.parse("19:00:00");
            dinner_end_time = sdf.parse("22:00:00");

            current_time = sdf.parse(c_string_time);
            assert current_time != null;



//            if(current_time.compareTo(breakfast_start_time)<=0 && time_of_food.equals("breakfast"))
//            {order.setText("Schedule order");
//            order.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    DialogFragment timepicker = new TimePickerFragment();
//                    timepicker.show(getSupportFragmentManager(), "time picker");
//                }
//            });}
//
//            if(current_time.compareTo(breakfast_end_time)>=0 && time_of_food.equals("breakfast"))
//            {order.setText("Breakfast counter closed");
//            order.setClickable(false);
//            order.setFocusable(false);}



        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(current_time + " time_of_food");

        switch (time_of_food)
        {case "breakfast":
        {which_time = "breakfast";
        System.out.println(time_of_food + " breakfast");
        if(current_time.compareTo(breakfast_start_time)<=0)
        {order.setText("Schedule order");
            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment timepicker = new TimePickerFragment();
                    timepicker.show(getSupportFragmentManager(), "time picker");
                }
            });}

            if(current_time.compareTo(breakfast_start_time)>=0 && current_time.compareTo(breakfast_end_time)<=0)
            {order.setText("Place order now");
                order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new breakload().execute();
                        startActivity(new Intent(emp_order.this, employee_menu.class));
                    }
                });
            }

            if(current_time.compareTo(breakfast_end_time)>=0)
            {order.setText("Breakfast counter closed");
                order.setClickable(false);
                order.setFocusable(false);}break;}

            case "lunch":
            {which_time = "lunch";
            System.out.println("lunch");
                if(current_time.compareTo(lunch_start_time)<=0)
                {order.setText("Schedule order");
                    order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DialogFragment timepicker = new TimePickerFragment();
                            timepicker.show(getSupportFragmentManager(), "time picker");
                        }
                    });}

                if(current_time.compareTo(lunch_start_time)>=0 && current_time.compareTo(lunch_end_time)<=0)
                {order.setText("Place order now");
                    order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new lunchload().execute();
                            startActivity(new Intent(emp_order.this, employee_menu.class));}
                    });
                }

                if(current_time.compareTo(lunch_end_time)>=0)
                {order.setText("lunch counter closed");
                    order.setClickable(false);
                    order.setFocusable(false);}break;}

            case "dinner":
            {which_time = "dinner";
            if(current_time.compareTo(dinner_start_time)<=0)
            {order.setText("Schedule order");
                order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogFragment timepicker = new TimePickerFragment();
                        timepicker.show(getSupportFragmentManager(), "time picker");}
                });}

                else if(current_time.compareTo(dinner_start_time)>=0 && current_time.compareTo(dinner_end_time)<=0)
                {order.setText("Place order now");
                    order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new dinnerload().execute();
                            startActivity(new Intent(emp_order.this, employee_menu.class));
                        }
                    });
                }

                else if(current_time.compareTo(dinner_end_time)>=0)
                {order.setText("dinner counter closed");
                    order.setClickable(false);
                    order.setFocusable(false);}break;}

        case "beverage":
        {order.setText("Place order now");
            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new bevload().execute();
                    startActivity(new Intent(emp_order.this, employee_menu.class));}
            });
        }}
    }

    private void set_alarm() {

        Calendar calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent1 = new Intent(emp_order.this, alarmreceiver.class);


    }

    private void showTimePicker() {

        picker = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H).setHour(12).setMinute(0).setTitleText("Select Alar Time").build();
        picker.show(getSupportFragmentManager(), "foxandroid");

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
                calendar.set(Calendar.MINUTE, picker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTimeSet(TimePicker timePicker, int hourofday, int minute) {
        Calendar calendar = Calendar.getInstance();
        Intent intent2 = getIntent();
        Bundle b = intent2.getExtras();
        byte[] bytes = Base64.decode(img_string, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
        hh.setText(Integer.toString(hourofday));
        mm.setText(Integer.toString(minute));
        switch (which_time)
        { case "breakfast":
        {Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        calendar2.set(Calendar.HOUR_OF_DAY, 7);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);

        calendar1.set(Calendar.HOUR_OF_DAY, hourofday);
        calendar1.set(Calendar.MINUTE, minute);
        calendar1.set(Calendar.SECOND, 0);

            if(calendar1.compareTo(calendar)<0 && calendar2.compareTo(calendar1)<0)
            {System.out.println("inside on timeset");
            calendar.set(Calendar.HOUR_OF_DAY, hourofday);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);

                StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://script.google.com/macros/s/AKfycbyiNcLUOtC9Sx9cowD0Hwz_KmMxUpOnTx5iGzvW2iWFuTFSPfxkodgL77i2tjFqgiHu/exec",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                            loading.dismiss();
//                            Toast.makeText(AddUser.this,response,Toast.LENGTH_LONG).show();
                                System.out.println("xxxxxxxxxxxxx" + response);
                                new asynctack().execute();
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
                        params.put("action","scheduled_order");
                        params.put("dish", b.getString("name"));
                        params.put("img", b.getString("img"));
                        params.put("price", b.getString("price"));
                        params.put("email", sharedPreferences.getString("emp_email", ""));
                        params.put("name", sharedPreferences.getString("emp_name", ""));
                        params.put("id", sharedPreferences.getString("key", ""));
                        params.put("token", sharedPreferences.getString("token", ""));
                        params.put("hour", String.valueOf(hourofday));
                        params.put("minute", String.valueOf(minute));
//              System.out.println("bbbbbbbbbb " + b.getString("name") + " " + b.getString("price") + " " + b.getString("desc") + " " + b.getString("time") + " " + sharedPreferences.getString("key", ""));
                        return params;
                    }

                };

                int socketTimeout = 30000; // 30 seconds. You can change it
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                RequestQueue requestQueue = Volley.newRequestQueue(emp_order.this);
                requestQueue.add(stringRequest);

                System.out.println(calendar + " calendar");

                this.hour = hourofday;
                this.minute = minute;}
            else
                Toast.makeText(this, "set between 08:00 to 11:00", Toast.LENGTH_SHORT).show();
        break;}

            case "lunch":
            {Calendar calendar1 = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 15);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar1.set(Calendar.HOUR_OF_DAY, hourofday);
            calendar1.set(Calendar.MINUTE, minute);
            calendar1.set(Calendar.SECOND, 0);
                Calendar calendar2 = Calendar.getInstance();
                calendar2.set(Calendar.HOUR_OF_DAY, 11);
                calendar2.set(Calendar.MINUTE, 59);
                calendar2.set(Calendar.SECOND, 59);

                if(calendar1.compareTo(calendar)<0 && calendar2.compareTo(calendar1)<0)
                {System.out.println("inside on timeset");
                calendar.set(Calendar.HOUR_OF_DAY, hourofday);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://script.google.com/macros/s/AKfycbyiNcLUOtC9Sx9cowD0Hwz_KmMxUpOnTx5iGzvW2iWFuTFSPfxkodgL77i2tjFqgiHu/exec",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//                            loading.dismiss();
//                            Toast.makeText(AddUser.this,response,Toast.LENGTH_LONG).show();
                                    System.out.println("xxxxxxxxxxxxx" + response);
                                    new asynctack().execute();
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
                            params.put("action","scheduled_order");
                            params.put("dish", b.getString("name"));
                            params.put("img", b.getString("img"));
                            params.put("price", b.getString("price"));
                            params.put("email", sharedPreferences.getString("emp_email", ""));
                            params.put("name", sharedPreferences.getString("emp_name", ""));
                            params.put("id", sharedPreferences.getString("key", ""));
                            params.put("token", sharedPreferences.getString("token", ""));
                            params.put("hour", String.valueOf(hourofday));
                            params.put("minute", String.valueOf(minute));
//              System.out.println("bbbbbbbbbb " + b.getString("name") + " " + b.getString("price") + " " + b.getString("desc") + " " + b.getString("time") + " " + sharedPreferences.getString("key", ""));
                            return params;
                        }

                    };

                    int socketTimeout = 30000; // 30 seconds. You can change it
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    stringRequest.setRetryPolicy(policy);
                    RequestQueue requestQueue = Volley.newRequestQueue(emp_order.this);
                    requestQueue.add(stringRequest);

                    System.out.println(calendar + " calendar");

                    this.hour = hourofday;
                    this.minute = minute;}
                else
                    Toast.makeText(this, "set between 12:00 to 15:00", Toast.LENGTH_SHORT).show();break;}

            case "dinner":
            {Calendar calendar1 = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar1.set(Calendar.HOUR_OF_DAY, hourofday);
                calendar1.set(Calendar.MINUTE, minute);
                calendar1.set(Calendar.SECOND, 0);

                calendar2.set(Calendar.HOUR_OF_DAY, 18);
                calendar2.set(Calendar.MINUTE, 59);
                calendar2.set(Calendar.SECOND, 59);
                if(calendar1.compareTo(calendar)<0 && calendar2.compareTo(calendar1)<0)
                {System.out.println("inside on timeset");
                calendar.set(Calendar.HOUR_OF_DAY, hourofday);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://script.google.com/macros/s/AKfycbyiNcLUOtC9Sx9cowD0Hwz_KmMxUpOnTx5iGzvW2iWFuTFSPfxkodgL77i2tjFqgiHu/exec",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//                            loading.dismiss();
//                            Toast.makeText(AddUser.this,response,Toast.LENGTH_LONG).show();
                                    System.out.println("xxxxxxxxxxxxx" + response);
                                    new asynctack().execute();
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
                            params.put("action","scheduled_order");
                            params.put("dish", b.getString("name"));
                            params.put("img", b.getString("img"));
                            params.put("price", b.getString("price"));
                            params.put("email", sharedPreferences.getString("emp_email", ""));
                            params.put("name", sharedPreferences.getString("emp_name", ""));
                            params.put("id", sharedPreferences.getString("key", ""));
                            params.put("token", sharedPreferences.getString("token", ""));
                            params.put("hour", String.valueOf(hourofday));
                            params.put("minute", String.valueOf(minute));
//              System.out.println("bbbbbbbbbb " + b.getString("name") + " " + b.getString("price") + " " + b.getString("desc") + " " + b.getString("time") + " " + sharedPreferences.getString("key", ""));
                            return params;
                        }

                    };

                    int socketTimeout = 30000; // 30 seconds. You can change it
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    stringRequest.setRetryPolicy(policy);
                    RequestQueue requestQueue = Volley.newRequestQueue(emp_order.this);
                    requestQueue.add(stringRequest);

                    System.out.println(calendar + " calendar");

                    this.hour = hourofday;
                    this.minute = minute;}
                else
                    Toast.makeText(this, "set between 19:00 to 22:00", Toast.LENGTH_SHORT).show();break;}

        }

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startAlarm(Calendar calendar) {
        System.out.println(calendar + " ++++++++");
//        SharedPreferences sharedPreferences = getSharedPreferences("orga_order", Context.MODE_PRIVATE);

//        System.out.println(sharedPreferences.getString("dish", ""));
//        System.out.println(sharedPreferences.getString("img", ""));
//        System.out.println(sharedPreferences.getString("email", ""));
//        System.out.println(sharedPreferences.getString("name", ""));
//        System.out.println(sharedPreferences.getString("id", ""));
//        System.out.println(sharedPreferences.getString("price", ""));


        AlarmManager alarmManager = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this.context, alarmreceiver.class);
        System.out.println("alarm scheduled " + calendar);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 1, intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
}
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
}
        else
        {alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);}
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void get_the_earliest_schedule() {
    SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
    try {
        db = sheet_comms.readAllData("Sheet5");
        if (db != null) {
            array = db.getJSONArray("records");}

        for(int i=0;i<array.length();i++)
        {if(sharedPreferences.getString("key", "").equals(array.getJSONObject(i).get("id").toString()) && sharedPreferences.getString("emp_name", "").equals(array.getJSONObject(i).get("name").toString()) && sharedPreferences.getString("emp_email", "").equals(array.getJSONObject(i).get("email").toString()))
        {s_dish.add(array.getJSONObject(i).get("dish").toString());
        s_price.add(array.getJSONObject(i).get("price").toString());
        s_image.add(array.getJSONObject(i).get("img").toString());
        s_email.add(array.getJSONObject(i).get("email").toString());
        s_name.add(array.getJSONObject(i).get("name").toString());
        s_id.add(array.getJSONObject(i).get("id").toString());
        s_token.add(array.getJSONObject(i).get("token").toString());
        s_hour.add(array.getJSONObject(i).get("hour").toString());
        s_minute.add(array.getJSONObject(i).get("minute").toString());}}

    } catch (IOException | JSONException e) {
        e.printStackTrace();
    }
    Calendar calendar = Calendar.getInstance();
    Calendar calendar2 = Calendar.getInstance();
    int small = 0;
    if(s_hour.size()!=1)
    {calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(s_hour.get(0)));
    calendar.set(Calendar.MINUTE, Integer.parseInt(s_minute.get(0)));
    calendar.set(Calendar.SECOND, 0);

    for(int i=0;i<s_hour.size();i++)
    {calendar2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(s_hour.get(i)));
    calendar2.set(Calendar.MINUTE, Integer.parseInt(s_minute.get(i)));
    calendar2.set(Calendar.SECOND, 0);

    System.out.println("now: " + calendar2 + " " + s_dish.get(i) + "\n");
    System.out.println("after now: " + calendar + " " + s_dish.get(small) + "\n");
    System.out.println("i val: " + i);

    if(calendar.compareTo(calendar2)>0)
    {System.out.println("priority1: " + calendar.get(Calendar.MINUTE));
    small = i;
    System.out.println("priority: " + s_dish.get(small));
    calendar.set(Calendar.HOUR_OF_DAY, calendar2.get(Calendar.HOUR_OF_DAY));
    calendar.set(Calendar.MINUTE, calendar2.get(Calendar.MINUTE));
    calendar.set(Calendar.SECOND, 0);}}

//    if(current_time.compareTo(calendar.getTime())>=0)
//    {calendar.set(Calendar.HOUR_OF_DAY,current_time.getHours());
//    calendar.set(Calendar.MINUTE,current_time.getMinutes() + 1);
//    calendar.set(Calendar.SECOND, 0);}

    System.out.println("hourofday " + calendar.get(Calendar.MINUTE) + " " + s_dish.get(small));

    SharedPreferences sharedPreferences1 = getSharedPreferences("orga_order", MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences1.edit();
    editor.putString("action","ordered_dish");
    editor.putString("dish", s_dish.get(small));
    editor.putString("img", s_image.get(small));
    editor.putString("price", s_price.get(small));
    editor.putString("email", s_email.get(small));
    editor.putString("name", s_name.get(small));
    editor.putString("id", s_id.get(small));
    editor.putString("hour", String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
    editor.putString("minute", String.valueOf(calendar.get(Calendar.MINUTE)));
    editor.apply();}

    if(s_hour.size()==0)
    {   System.out.println("s_hour.size()==0");
        calendar.set(Calendar.HOUR_OF_DAY, this.hour);
        calendar.set(Calendar.MINUTE, this.minute);
        calendar.set(Calendar.SECOND, 0);
        Intent intent2 = getIntent();
        Bundle b = intent2.getExtras();
        SharedPreferences sharedPreferences3 = getSharedPreferences("orga_key", MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = getSharedPreferences("orga_order", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString("action","ordered_dish");
        editor.putString("dish", b.getString("name"));
        editor.putString("img", b.getString("img"));
        editor.putString("price", b.getString("price"));
        editor.putString("email", sharedPreferences3.getString("emp_email", ""));
        editor.putString("name", sharedPreferences3.getString("emp_name", ""));
        editor.putString("id", sharedPreferences3.getString("key", ""));
        editor.putString("hour", String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
        editor.putString("minute", String.valueOf(calendar.get(Calendar.MINUTE)));
        editor.apply();
    }

    if(s_hour.size()==1)
    {   System.out.println("s_hour.size()==1");
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(s_hour.get(0)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(s_minute.get(0)));
        calendar.set(Calendar.SECOND, 0);
        System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
        System.out.println(calendar.get(Calendar.MINUTE));
        System.out.println(s_dish.get(0));
        Intent intent2 = getIntent();
        Bundle b = intent2.getExtras();
        System.out.println(b.getString("name"));
        SharedPreferences sharedPreferences3 = getSharedPreferences("orga_key", MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = getSharedPreferences("orga_order", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString("action","ordered_dish");
        editor.putString("dish", b.getString("name"));
        editor.putString("img", b.getString("img"));
        editor.putString("price", b.getString("price"));
        editor.putString("email", sharedPreferences3.getString("emp_email", ""));
        editor.putString("name", sharedPreferences3.getString("emp_name", ""));
        editor.putString("id", sharedPreferences3.getString("key", ""));
        editor.putString("hour", String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
        editor.putString("minute", String.valueOf(calendar.get(Calendar.MINUTE)));
        editor.apply();}
    if(s_dish.size()!=0)
    {startAlarm(calendar);}

}

    class asynctack extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            startActivity(new Intent(emp_order.this, employee_menu.class));
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            get_the_earliest_schedule();
        }
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void get_the_earliest_schedule2(SharedPreferences sharedPreferences, SharedPreferences sharedPreferences1, Context context2) {
        this.context = context2;
//        SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
        try {
            db = sheet_comms.readAllData("Sheet5");
            if (db != null) {
                array = db.getJSONArray("records");}
            if(array!=null)
            {for(int i=0;i<array.length();i++)
            {if(sharedPreferences.getString("key", "").equals(array.getJSONObject(i).get("id").toString()) && sharedPreferences.getString("emp_name", "").equals(array.getJSONObject(i).get("name").toString()) && sharedPreferences.getString("emp_email", "").equals(array.getJSONObject(i).get("email").toString()))
            {s_dish.add(array.getJSONObject(i).get("dish").toString());
                s_price.add(array.getJSONObject(i).get("price").toString());
                s_image.add(array.getJSONObject(i).get("img").toString());
                s_email.add(array.getJSONObject(i).get("email").toString());
                s_name.add(array.getJSONObject(i).get("name").toString());
                s_id.add(array.getJSONObject(i).get("id").toString());
                s_token.add(array.getJSONObject(i).get("token").toString());
                s_hour.add(array.getJSONObject(i).get("hour").toString());
                s_minute.add(array.getJSONObject(i).get("minute").toString());}}}

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int small = 0;
        if(s_hour.size()!=1 && s_hour.size()!=0)
        {calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(s_hour.get(0)));
            calendar.set(Calendar.MINUTE, Integer.parseInt(s_minute.get(0)));
            calendar.set(Calendar.SECOND, 0);

            for(int i=0;i<s_hour.size();i++)
            {calendar2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(s_hour.get(i)));
                calendar2.set(Calendar.MINUTE, Integer.parseInt(s_minute.get(i)));
                calendar2.set(Calendar.SECOND, 0);

                System.out.println("now: " + calendar2 + " " + s_dish.get(i) + "\n");
                System.out.println("after now: " + calendar + " " + s_dish.get(small) + "\n");
                System.out.println("i val: " + i);

                if(calendar.compareTo(calendar2)>0)
                {System.out.println("priority1: " + calendar.get(Calendar.MINUTE));
                    small = i;
                    System.out.println("priority: " + s_dish.get(small));
                    calendar.set(Calendar.HOUR_OF_DAY, calendar2.get(Calendar.HOUR_OF_DAY));
                    calendar.set(Calendar.MINUTE, calendar2.get(Calendar.MINUTE));
                    calendar.set(Calendar.SECOND, 0);}}

//    if(current_time.compareTo(calendar.getTime())>=0)
//    {calendar.set(Calendar.HOUR_OF_DAY,current_time.getHours());
//    calendar.set(Calendar.MINUTE,current_time.getMinutes() + 1);
//    calendar.set(Calendar.SECOND, 0);}

            System.out.println("hourofday " + calendar.get(Calendar.MINUTE) + " " + s_dish.get(small));

            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.putString("action","ordered_dish");
            editor.putString("dish", s_dish.get(small));
            editor.putString("img", s_image.get(small));
            editor.putString("price", s_price.get(small));
            editor.putString("email", s_email.get(small));
            editor.putString("name", s_name.get(small));
            editor.putString("id", s_id.get(small));
            editor.putString("hour", String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
            editor.putString("minute", String.valueOf(calendar.get(Calendar.MINUTE)));
            editor.apply();}

//        if(s_hour.size()==0)
//        {   System.out.println("s_hour.size()==0");
//            calendar.set(Calendar.HOUR_OF_DAY, this.hour);
//            calendar.set(Calendar.MINUTE, this.minute);
//            calendar.set(Calendar.SECOND, 0);
////            SharedPreferences sharedPreferences3 = getSharedPreferences("orga_key", MODE_PRIVATE);
////            SharedPreferences sharedPreferences1 = getSharedPreferences("orga_order", MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences1.edit();
//            editor.putString("action","ordered_dish");
//            editor.putString("dish", b.getString("name"));
//            editor.putString("img", b.getString("img"));
//            editor.putString("price", b.getString("price"));
//            editor.putString("email", sharedPreferences.getString("emp_email", ""));
//            editor.putString("name", sharedPreferences.getString("emp_name", ""));
//            editor.putString("id", sharedPreferences.getString("key", ""));
//            editor.putString("hour", String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
//            editor.putString("minute", String.valueOf(calendar.get(Calendar.MINUTE)));
//            editor.apply();
//        }

        if(s_hour.size()==1)
        {   System.out.println("s_hour.size()==1");
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(s_hour.get(0)));
            calendar.set(Calendar.MINUTE, Integer.parseInt(s_minute.get(0)));
            calendar.set(Calendar.SECOND, 0);
            System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
            System.out.println(calendar.get(Calendar.MINUTE));
            System.out.println(s_dish.get(0));
//            System.out.println(b.getString("name"));
//            SharedPreferences sharedPreferences3 = getSharedPreferences("orga_key", MODE_PRIVATE);
//            SharedPreferences sharedPreferences1 = getSharedPreferences("orga_order", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.putString("action","ordered_dish");
            editor.putString("dish", s_dish.get(0));
            editor.putString("img", s_image.get(0));
            editor.putString("price", s_price.get(0));
            editor.putString("email", sharedPreferences.getString("emp_email", ""));
            editor.putString("name", sharedPreferences.getString("emp_name", ""));
            editor.putString("id", sharedPreferences.getString("key", ""));
            editor.putString("hour", String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
            editor.putString("minute", String.valueOf(calendar.get(Calendar.MINUTE)));
            editor.apply();}
        if(s_dish.size()!=0)
        {startAlarm(calendar);}

    }

    class breakload extends AsyncTask<Void, Void, Void>
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
            SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
            Intent intent2 = getIntent();
            Bundle b = intent2.getExtras();
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
                    params.put("action","ordered_dish");
                    params.put("dish", b.getString("name"));
                    params.put("img", b.getString("img"));
                    params.put("price", b.getString("price"));
                    params.put("email", sharedPreferences.getString("emp_email", ""));
                    params.put("name", sharedPreferences.getString("emp_name", ""));
                    params.put("id", sharedPreferences.getString("key", ""));
                    params.put("token", sharedPreferences.getString("token", ""));
//                        System.out.println("bbbbbbbbbb " + b.getString("name") + " " + b.getString("price") + " " + b.getString("desc") + " " + b.getString("time") + " " + sharedPreferences.getString("key", ""));
                    return params;
                }

            };

            int socketTimeout = 30000; // 30 seconds. You can change it
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            RequestQueue requestQueue = Volley.newRequestQueue(emp_order.this);
            requestQueue.add(stringRequest);
            return null;
        }
    }

    class bevload extends AsyncTask<Void, Void, Void>
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
            SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
            Intent intent2 = getIntent();
            Bundle b = intent2.getExtras();
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
                    params.put("action","ordered_dish");
                    params.put("dish", b.getString("name"));
                    params.put("img", b.getString("img"));
                    params.put("price", b.getString("price"));
                    params.put("email", sharedPreferences.getString("emp_email", ""));
                    params.put("name", sharedPreferences.getString("emp_name", ""));
                    params.put("id", sharedPreferences.getString("key", ""));
                    params.put("token", sharedPreferences.getString("token", ""));
//                        System.out.println("bbbbbbbbbb " + b.getString("name") + " " + b.getString("price") + " " + b.getString("desc") + " " + b.getString("time") + " " + sharedPreferences.getString("key", ""));
                    return params;
                }

            };

            int socketTimeout = 30000; // 30 seconds. You can change it
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            RequestQueue requestQueue = Volley.newRequestQueue(emp_order.this);
            requestQueue.add(stringRequest);
            return null;
        }
    }

    class dinnerload extends AsyncTask<Void, Void, Void>
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
            SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
            Intent intent2 = getIntent();
            Bundle b = intent2.getExtras();
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
                    params.put("action","ordered_dish");
                    params.put("dish", b.getString("name"));
                    params.put("img", b.getString("img"));
                    params.put("price", b.getString("price"));
                    params.put("email", sharedPreferences.getString("emp_email", ""));
                    params.put("name", sharedPreferences.getString("emp_name", ""));
                    params.put("id", sharedPreferences.getString("key", ""));
                    params.put("token", sharedPreferences.getString("token", ""));
//                        System.out.println("bbbbbbbbbb " + b.getString("name") + " " + b.getString("price") + " " + b.getString("desc") + " " + b.getString("time") + " " + sharedPreferences.getString("key", ""));
                    return params;
                }

            };

            int socketTimeout = 30000; // 30 seconds. You can change it
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            RequestQueue requestQueue = Volley.newRequestQueue(emp_order.this);
            requestQueue.add(stringRequest);
            return null;
        }
    }

    class lunchload extends AsyncTask<Void, Void, Void>
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
            SharedPreferences sharedPreferences = getSharedPreferences("orga_key", MODE_PRIVATE);
            Intent intent2 = getIntent();
            Bundle b = intent2.getExtras();
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
                    params.put("action","ordered_dish");
                    params.put("dish", b.getString("name"));
                    params.put("img", b.getString("img"));
                    params.put("price", b.getString("price"));
                    params.put("email", sharedPreferences.getString("emp_email", ""));
                    params.put("name", sharedPreferences.getString("emp_name", ""));
                    params.put("id", sharedPreferences.getString("key", ""));
                    params.put("token", sharedPreferences.getString("token", ""));
//                        System.out.println("bbbbbbbbbb " + b.getString("name") + " " + b.getString("price") + " " + b.getString("desc") + " " + b.getString("time") + " " + sharedPreferences.getString("key", ""));
                    return params;
                }

            };

            int socketTimeout = 30000; // 30 seconds. You can change it
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            RequestQueue requestQueue = Volley.newRequestQueue(emp_order.this);
            requestQueue.add(stringRequest);
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(emp_order.this, employee_menu.class));
    }
}