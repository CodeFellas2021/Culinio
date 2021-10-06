package com.example.cibo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class chef_details_recycler extends RecyclerView.Adapter<chef_details_recycler.ViewHolder>{

    ArrayList<String> c_name = new ArrayList<>();
    ArrayList<String> c_email = new ArrayList<>();
    ArrayList<String> c_password = new ArrayList<>();
    String id;
    private Context mContext;
    int pos;

    public chef_details_recycler(Context mContext, ArrayList<String> c_name, ArrayList<String> c_email, ArrayList<String> password, String id, RelativeLayout layout) {
        this.c_name = c_name;
        this.c_email = c_email;
        this.c_password = password;
        this.id = id;
        this.mContext = mContext;

    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chef_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull chef_details_recycler.ViewHolder holder, int position) {
    System.out.println("oooooo " + c_name.get(position));
    holder.name.setText(c_name.get(position));
    holder.email.setText(c_email.get(position));
    holder.name.setBackgroundResource(R.color.main_edit);
    holder.email.setBackgroundResource(R.color.main_edit);
    holder.btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pos = position;
            new load().execute();
        }
    });
    }

    @Override
    public int getItemCount() {
        return c_name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
        {TextView name, email;
        ImageButton btn;
        RelativeLayout parentlayout;
    public ViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.cll_chef_name);
        email = itemView.findViewById(R.id.cll_chef_email);
        btn = itemView.findViewById(R.id.cll_delete);
        parentlayout = itemView.findViewById(R.id.cll_parent_layout);
    }
}

class load extends AsyncTask<Void, Void, Void>
{

    @Override
    protected Void doInBackground(Void... voids) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("orga_key", MODE_PRIVATE);
        sheet_comms.insert_chef("delete_chef", c_name.get(pos), c_email.get(pos), c_password.get(pos), sharedPreferences.getString("key", ""));
        return null;
    }
    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        mContext.startActivity(new Intent(mContext, chef_details.class));
    }
}

}

