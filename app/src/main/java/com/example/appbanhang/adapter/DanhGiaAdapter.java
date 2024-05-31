package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.model.SanPhamMoi;
import com.example.appbanhang.utils.Utils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DanhGiaAdapter extends RecyclerView.Adapter<DanhGiaAdapter.MyViewHold> {
    Context context;
    List<SanPhamMoi> array;

    public DanhGiaAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_review_with_user,parent,false);
        return new MyViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHold holder, int position) {

        SanPhamMoi item = array.get(position);
        holder.usename.setText(item.getUsername()+"");

        holder.ratingBar.setIsIndicator(true);
        holder.ratingBar.setRating(item.getDanhgiasao());
        holder.text.setText("Đánh giá: "+item.getBinhluan());

        if (item.getHinhanhuser().contains("http")) {
            Glide.with(context).load(item.getHinhanhuser()).into(holder.avatar);
        }
        else {
            String avatar = Utils.BASE_URL + "images/" + item.getHinhanhuser();
            Glide.with(context).load(avatar).into(holder.avatar);
        }

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHold extends RecyclerView.ViewHolder {
ImageView avatarr;
CircleImageView avatar;
TextView text, usename;
RatingBar ratingBar;
        public MyViewHold(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.image_pic);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            text = itemView.findViewById(R.id.text_review);
            usename = itemView.findViewById(R.id.text_title);
        }
    }
}
