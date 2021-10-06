package com.example.cibo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.cibo.R.color.sign_in_vtn_color;

public class employee_recycler extends RecyclerView.Adapter<employee_recycler.ViewHolder> {

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> price = new ArrayList<>();
    private ArrayList<String> bdesc = new ArrayList<>();
    private Context mContext;

    public employee_recycler(Context mContext, ArrayList<String> names, ArrayList<String> images, ArrayList<String>price, ArrayList<String>bdesc) {
        this.names = names;
        this.images = images;
        this.price = price;
        this.bdesc = bdesc;
        this.mContext = mContext;
    }

    @Override
    public @NotNull employee_recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_blayout_listitem, parent, false);
        return new employee_recycler.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(employee_recycler.ViewHolder holder, int position) {

        byte[] bytes = Base64.decode(images.get(position), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.image.setImageBitmap(bitmap);
        holder.name.setText(names.get(position));
        holder.price.setText(price.get(position));
//        holder.name.setBackgroundResource(R.color.white);
//        holder.name.setTextColor(R.color.white);
//        holder.price.setTextColor(R.color.white);
        holder.divider.setBackgroundResource(sign_in_vtn_color);
        holder.layout.setBackgroundResource(R.drawable.edittext_design);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, emp_order.class);
                intent.putExtra("name", names.get(position));
                intent.putExtra("price", price.get(position));
                intent.putExtra("desc", bdesc.get(position));
                intent.putExtra("img", images.get(position));
                intent.putExtra("time", "breakfast");
                mContext.startActivity(intent);
            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, names.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, emp_order.class);
                intent.putExtra("name", names.get(position));
                intent.putExtra("price", price.get(position));
                intent.putExtra("desc", bdesc.get(position));
                intent.putExtra("img", images.get(position));
                intent.putExtra("time", "breakfast");
                mContext.startActivity(intent);
//             new employee_menu().to_menu_expand();
            }
        });
    }


    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView name;
        TextView price;
        ConstraintLayout layout;
        View divider;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            divider = itemView.findViewById(R.id.divider);
            layout = itemView.findViewById(R.id.b_layout_id);
            image = itemView.findViewById(R.id.b_food_image);
            name = itemView.findViewById(R.id.b_food_name);
            price = itemView.findViewById(R.id.b_food_price);
        }
    }



}


class l_employee_recycler extends RecyclerView.Adapter<l_employee_recycler.ViewHolder> {

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> price = new ArrayList<>();
    private ArrayList<String> bdesc = new ArrayList<>();
    private Context mContext;

    public l_employee_recycler(Context mContext, ArrayList<String> names, ArrayList<String> images, ArrayList<String>price, ArrayList<String>bdesc) {
        this.names = names;
        this.images = images;
        this.price = price;
        this.bdesc = bdesc;
        this.mContext = mContext;
    }

    @Override
    public @NotNull l_employee_recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_blayout_listitem, parent, false);
        return new l_employee_recycler.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(l_employee_recycler.ViewHolder holder, int position) {

        byte[] bytes = Base64.decode(images.get(position), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.image.setImageBitmap(bitmap);
        holder.name.setText(names.get(position));
        holder.price.setText(price.get(position));
        holder.divider.setBackgroundResource(sign_in_vtn_color);
        holder.layout.setBackgroundResource(R.drawable.edittext_design);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, emp_order.class);
                intent.putExtra("name", names.get(position));
                intent.putExtra("price", price.get(position));
                intent.putExtra("desc", bdesc.get(position));
                intent.putExtra("img", images.get(position));
                intent.putExtra("time", "lunch");
                mContext.startActivity(intent);
            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, names.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, emp_order.class);
                intent.putExtra("name", names.get(position));
                intent.putExtra("price", price.get(position));
                intent.putExtra("desc", bdesc.get(position));
                intent.putExtra("img", images.get(position));
                intent.putExtra("time", "lunch");
                mContext.startActivity(intent);
//             new organization_profile().to_menu_expand();
            }
        });
    }


    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView name;
        TextView price;
        ConstraintLayout layout;
        View divider;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            divider = itemView.findViewById(R.id.divider);
            layout = itemView.findViewById(R.id.b_layout_id);
            image = itemView.findViewById(R.id.b_food_image);
            name = itemView.findViewById(R.id.b_food_name);
            price = itemView.findViewById(R.id.b_food_price);
        }
    }

}


class d_employee_recycler extends RecyclerView.Adapter<d_employee_recycler.ViewHolder> {

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> price = new ArrayList<>();
    private ArrayList<String> bdesc = new ArrayList<>();
    private Context mContext;

    public d_employee_recycler(Context mContext, ArrayList<String> names, ArrayList<String> images, ArrayList<String>price, ArrayList<String>bdesc) {
        this.names = names;
        this.images = images;
        this.price = price;
        this.bdesc = bdesc;
        this.mContext = mContext;
    }


    @Override
    public @NotNull d_employee_recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_blayout_listitem, parent, false);
        return new d_employee_recycler.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(d_employee_recycler.ViewHolder holder, int position) {

        byte[] bytes = Base64.decode(images.get(position), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.image.setImageBitmap(bitmap);
        holder.name.setText(names.get(position));
        holder.price.setText(price.get(position));
        holder.divider.setBackgroundResource(sign_in_vtn_color);
        holder.layout.setBackgroundResource(R.drawable.edittext_design);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, emp_order.class);
                intent.putExtra("name", names.get(position));
                intent.putExtra("price", price.get(position));
                intent.putExtra("desc", bdesc.get(position));
                intent.putExtra("img", images.get(position));
                intent.putExtra("time", "dinner");
                mContext.startActivity(intent);
            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, names.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, emp_order.class);
                intent.putExtra("name", names.get(position));
                intent.putExtra("price", price.get(position));
                intent.putExtra("desc", bdesc.get(position));
                intent.putExtra("img", images.get(position));
                intent.putExtra("time", "dinner");
                mContext.startActivity(intent);
//             new organization_profile().to_menu_expand();
            }
        });
    }


    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView name;
        TextView price;
        ConstraintLayout layout;
        View divider;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            divider = itemView.findViewById(R.id.divider);
            layout = itemView.findViewById(R.id.b_layout_id);
            image = itemView.findViewById(R.id.b_food_image);
            name = itemView.findViewById(R.id.b_food_name);
            price = itemView.findViewById(R.id.b_food_price);
        }
    }

}


class bev_employee_recycler extends RecyclerView.Adapter<bev_employee_recycler.ViewHolder> {

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> price = new ArrayList<>();
    private ArrayList<String> bdesc = new ArrayList<>();
    private Context mContext;

    public bev_employee_recycler(Context mContext, ArrayList<String> names, ArrayList<String> images, ArrayList<String>price, ArrayList<String>bdesc) {
        this.names = names;
        this.images = images;
        this.price = price;
        this.bdesc = bdesc;
        this.mContext = mContext;
    }


    @Override
    public @NotNull bev_employee_recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_blayout_listitem, parent, false);
        return new bev_employee_recycler.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(bev_employee_recycler.ViewHolder holder, int position) {

        byte[] bytes = Base64.decode(images.get(position), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.image.setImageBitmap(bitmap);
        holder.name.setText(names.get(position));
        holder.price.setText(price.get(position));
        holder.divider.setBackgroundResource(sign_in_vtn_color);
        holder.layout.setBackgroundResource(R.drawable.edittext_design);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, emp_order.class);
                intent.putExtra("name", names.get(position));
                intent.putExtra("price", price.get(position));
                intent.putExtra("desc", bdesc.get(position));
                intent.putExtra("img", images.get(position));
                intent.putExtra("time", "beverage");
                mContext.startActivity(intent);
            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, names.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, emp_order.class);
                intent.putExtra("name", names.get(position));
                intent.putExtra("price", price.get(position));
                intent.putExtra("desc", bdesc.get(position));
                intent.putExtra("img", images.get(position));
                intent.putExtra("time", "beverage");
                mContext.startActivity(intent);
//             new organization_profile().to_menu_expand();
            }
        });
    }


    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView name;
        TextView price;
        ConstraintLayout layout;
        View divider;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            divider = itemView.findViewById(R.id.divider);
            layout = itemView.findViewById(R.id.b_layout_id);
            image = itemView.findViewById(R.id.b_food_image);
            name = itemView.findViewById(R.id.b_food_name);
            price = itemView.findViewById(R.id.b_food_price);
        }
    }

}

class ordered extends RecyclerView.Adapter<ordered.ViewHolder> {

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> price = new ArrayList<>();
    private Context mContext;
    String s_name, s_email, s_dish, s_id;

    public ordered(Context mContext, ArrayList<String> names, ArrayList<String> images, ArrayList<String>price) {
        this.names = names;
        this.images = images;
        this.price = price;
        this.mContext = mContext;
    }


    @Override
    public @NotNull ordered.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emp_orders_list, parent, false);
        return new ordered.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ordered.ViewHolder holder, int position) {

        byte[] bytes = Base64.decode(images.get(position), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.image.setImageBitmap(bitmap);
        holder.name.setText(names.get(position));
        holder.price.setText(price.get(position));
        holder.name.setBackgroundResource(R.color.main_edit);
        holder.price.setBackgroundResource(R.color.main_edit);
        holder.image.setBackgroundResource(R.color.main_edit);
        holder.delete.setBackgroundResource(R.color.main_edit);
        holder.divider.setBackgroundResource(sign_in_vtn_color);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("orga_key", Context.MODE_PRIVATE);
                s_name = sharedPreferences.getString("emp_name", "");
                s_email = sharedPreferences.getString("emp_email", "");
                s_dish = names.get(position);
                s_id = sharedPreferences.getString("key", "");
                new delete().execute();

            }
        });
    }

    class delete extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            mContext.startActivity(new Intent(mContext, employee_orders.class));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject db = null;
            JSONArray array = null;
            int flag = 0;
            SharedPreferences sharedPreferences = mContext.getSharedPreferences("orga_key", Context.MODE_PRIVATE);
            try {
                db = sheet_comms.readAllData("sheet4");
                if (db != null) {
                    array = db.getJSONArray("records");}
                if(array!=null)
                {for(int i=0;i<array.length();i++)
                {if(sharedPreferences.getString("key", "").equals(array.getJSONObject(i).get("id").toString()) && sharedPreferences.getString("emp_email", "").equals(array.getJSONObject(i).get("email").toString()) && sharedPreferences.getString("emp_name", "").equals(array.getJSONObject(i).get("name_").toString()))
                {flag = 1;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://script.google.com/macros/s/AKfycbyiNcLUOtC9Sx9cowD0Hwz_KmMxUpOnTx5iGzvW2iWFuTFSPfxkodgL77i2tjFqgiHu/exec",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//                            loading.dismiss();
//                            Toast.makeText(AddUser.this,response,Toast.LENGTH_LONG).show();
                                    System.out.println("sheet4" + response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println("sheet4" + error);
//                            Toast.makeText(AddUser.this,error.toString(),Toast.LENGTH_LONG).show();
                                }
                            }){
                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("action","delete_order");
                            params.put("dish", s_dish);
                            params.put("email", sharedPreferences.getString("emp_email", ""));
                            params.put("name", sharedPreferences.getString("emp_name", ""));
                            params.put("id", sharedPreferences.getString("key", ""));
                            System.out.println("bbbbbbbbbb " + s_dish + " " + sharedPreferences.getString("emp_email", "") + " " + sharedPreferences.getString("emp_name", "") + " " + sharedPreferences.getString("key", ""));
                            return params;
                        }

                    };

                    int socketTimeout = 30000; // 30 seconds. You can change it
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    stringRequest.setRetryPolicy(policy);
                    RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                    requestQueue.add(stringRequest);
                    break;}}

                }

                if(flag==0)
                {db = sheet_comms.readAllData("sheet5");
                if (db != null) {
                    array = db.getJSONArray("records");}
                if(array!=null)
                {for(int i=0;i<array.length();i++)
                {if(sharedPreferences.getString("key", "").equals(array.getJSONObject(i).get("id").toString()) && sharedPreferences.getString("emp_email", "").equals(array.getJSONObject(i).get("email").toString()) && sharedPreferences.getString("emp_name", "").equals(array.getJSONObject(i).get("name").toString()))
                {flag = 1;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://script.google.com/macros/s/AKfycbyiNcLUOtC9Sx9cowD0Hwz_KmMxUpOnTx5iGzvW2iWFuTFSPfxkodgL77i2tjFqgiHu/exec",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//                            loading.dismiss();
//                            Toast.makeText(AddUser.this,response,Toast.LENGTH_LONG).show();
                                    System.out.println("sheet5" + response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println("sheet5" + error);
//                            Toast.makeText(AddUser.this,error.toString(),Toast.LENGTH_LONG).show();
                                }
                            }){
                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("action","delete_scheduled2");
                            params.put("dish", s_dish);
                            params.put("email", sharedPreferences.getString("emp_email", ""));
                            params.put("name", sharedPreferences.getString("emp_name", ""));
                            params.put("id", sharedPreferences.getString("key", ""));
                            System.out.println("bbbbbbbbbb " + s_dish + " " + sharedPreferences.getString("emp_email", "") + " " + sharedPreferences.getString("emp_name", "") + " " + sharedPreferences.getString("key", ""));                            return params;
                        }

                    };

                    int socketTimeout = 30000; // 30 seconds. You can change it
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    stringRequest.setRetryPolicy(policy);
                    RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                    requestQueue.add(stringRequest);break;}
                    }

                }}

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView name;
        TextView price;
        ImageButton delete;
        View divider;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            divider = itemView.findViewById(R.id.emp_divider);
            image = itemView.findViewById(R.id.row_view_image);
            name = itemView.findViewById(R.id.row_view_prod_name);
            price = itemView.findViewById(R.id.row_view_price);
            delete = itemView.findViewById(R.id.eo_delete);
        }
    }

}



class chef_order extends RecyclerView.Adapter<chef_order.ViewHolder> {

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> email = new ArrayList<>();
    private ArrayList<String> c_name = new ArrayList<>();
    private ArrayList<String> token = new ArrayList<>();
    private ArrayList<String> id = new ArrayList<>();
    private Context mContext;

    public chef_order(Context mContext, ArrayList<String> names, ArrayList<String> images, ArrayList<String>email, ArrayList<String>c_name, ArrayList<String>token, ArrayList<String>id) {
        this.names = names;
        this.images = images;
        this.email = email;
        this.c_name = c_name;
        this.token = token;
        this.id = id;
        this.mContext = mContext;
    }


    @Override
    public @NotNull chef_order.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new chef_order.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(chef_order.ViewHolder holder, int position) {

        byte[] bytes = Base64.decode(images.get(position), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.image.setImageBitmap(bitmap);
        holder.name.setText(names.get(position));
        holder.email.setText(email.get(position));
        holder.c_name.setText(c_name.get(position));
        holder.image.setBackgroundResource(R.color.main_edit);
        holder.c_name.setBackgroundResource(R.color.main_edit);
        holder.email.setBackgroundResource(R.color.main_edit);
        holder.name.setBackgroundResource(R.color.main_edit);
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(holder.cb.isChecked())
            {chef_screen.re_write(position, token.get(position), names.get(position), email.get(position), images.get(position), c_name.get(position), id.get(position));}}
        });

    }


    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView name;
        TextView email;
        TextView c_name;
        CheckBox cb;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.row_view_image);
            name = itemView.findViewById(R.id.row_view_prod_name);
            email = itemView.findViewById(R.id.row_view_price);
            c_name = itemView.findViewById(R.id.row_view_address);
            cb = itemView.findViewById(R.id.checkbox);
        }
    }

}