package com.example.cibo;

import android.os.StrictMode;
import androidx.annotation.NonNull;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class sheet_comms {

    public static final String url= "https://script.google.com/macros/s/AKfycbyiNcLUOtC9Sx9cowD0Hwz_KmMxUpOnTx5iGzvW2iWFuTFSPfxkodgL77i2tjFqgiHu/exec?";
    public static Response response;

    public static JSONObject org_regis(String oname, String oemail, String obranch, String opassword, int id)
    {
        OkHttpClient client = new OkHttpClient();
        System.out.println("lllll " + url + "opt=register&" + "oname=" + oname + "&oemail=" + oemail + "&branch=" + obranch + "&password=" + opassword + "&id=" + id);
        Request request = new Request.Builder().url(url + "opt=register&" + "oname=" + oname + "&oemail=" + oemail + "&branch=" + obranch + "&password=" + opassword + "&id=" + id).build();

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

        try{response = client.newCall(request).execute();
            return new JSONObject(response.body().string());

        }catch (@NonNull IOException | JSONException e){System.out.println(e);}

        return null;

    }

    public static JSONObject readAllData(String sheet_name) throws IOException {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url+"opt=readAll" + "&sheetname=" + sheet_name)
                    .build();
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {
            System.out.println("messed up " + e);
            System.out.println(url+"opt=readAll");
        }
        return null;
    }

    public static JSONObject insert_chef(String to_do, String name, String email, String password, String id)
    {

        OkHttpClient client = new OkHttpClient();
        System.out.println("lllll " + url + "opt=add_chef" + "&name=" + name + "&email=" + email + "&password=" + password + "&id=" + id);
        Request request = new Request.Builder().url(url + "opt=" + to_do + "&name=" + name + "&email=" + email + "&password=" + password + "&id=" + id).build();

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

        try{response = client.newCall(request).execute();
            return new JSONObject(response.body().string());

        }catch (@NonNull IOException | JSONException e){System.out.println(e);}

        return null;

    }

//    public static JSONObject delete_menu(String name, String price, String desc, String id, String time)
//    {
//
//        OkHttpClient client = new OkHttpClient();
//        System.out.println("lllll " + url + "opt=" + "delete_menu" + "&name=" + name + "&price=" + price + "&desc=" + desc + "&id=" + id + "&time=" + time);
////        Request request = new Request.Builder().url(url + "opt=" + "delete_menu" + "&name=" + name + "&price=" + price + "&desc=" + desc + "&id=" + id + "&time=" + time).build();
//        RequestBody formBody = new FormBody.Builder()
//                .add("message", "Your message")
//                .build();
//        Request request = new Request.Builder()
//                .url("http://www.foo.bar/index.php")
//                .post(formBody)
//                .build();
//        int SDK_INT = android.os.Build.VERSION.SDK_INT;
//        if (SDK_INT > 8)
//        {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//                    .permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//
//        }
//
//        try{response = client.newCall(request).execute();
//            return new JSONObject(response.body().string());
//
//        }catch (@NonNull IOException | JSONException e){System.out.println(e);}
//
//        return null;
//
//    }



}
