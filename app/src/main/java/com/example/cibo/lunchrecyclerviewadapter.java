package com.example.cibo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import static com.example.cibo.R.color.sign_in_vtn_color;

class lunchrecyclerviewadapter extends RecyclerView.Adapter<lunchrecyclerviewadapter.ViewHolder>{

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> price = new ArrayList<>();
    private ArrayList<String> desc = new ArrayList<>();
    private ArrayList<String> ltime = new ArrayList<>();
    private Context mContext;

    public lunchrecyclerviewadapter(Context mContext, ArrayList<String> names, ArrayList<String> images, ArrayList<String> price, ArrayList<String> desc, ArrayList<String> ltime) {
        this.names = names;
        this.images = images;
        this.price = price;
        this.desc = desc;
        this.ltime = ltime;
        this.mContext = mContext;
    }


    @Override
    public @NotNull ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_blayout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(lunchrecyclerviewadapter.ViewHolder holder, int position) {
//        Glide.with(mContext)
//                .asBitmap()
//                .load(images.get(position))
//                .into(holder.image);
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
                Intent intent = new Intent(mContext, menu_expand.class);
                intent.putExtra("name", names.get(position));
                intent.putExtra("price", price.get(position));
                intent.putExtra("desc", desc.get(position));
                intent.putExtra("img", images.get(position));
                intent.putExtra("time", ltime.get(position));
                mContext.startActivity(intent);
            }
        });

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, menu_expand.class);
                intent.putExtra("name", names.get(position));
                intent.putExtra("price", price.get(position));
                intent.putExtra("desc", desc.get(position));
                intent.putExtra("img", images.get(position));
                intent.putExtra("time", ltime.get(position));
                mContext.startActivity(intent);

//                Toast.makeText(mContext, names.get(position), Toast.LENGTH_SHORT).show();
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

