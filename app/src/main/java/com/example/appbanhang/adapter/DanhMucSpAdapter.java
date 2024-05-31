package com.example.appbanhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Interface.ItemClickListener;
import com.example.appbanhang.R;
import com.example.appbanhang.activity.DienThoaiActivity2;
import com.example.appbanhang.activity.DongHoMainActivity;
import com.example.appbanhang.activity.LapTopActivity2;
import com.example.appbanhang.activity.PhuKienActivity2;
import com.example.appbanhang.activity.TaiNgheMainActivity;
import com.example.appbanhang.activity.TiviMainActivity;
import com.example.appbanhang.model.DanhMucSp;

import java.util.List;

public class DanhMucSpAdapter extends RecyclerView.Adapter<DanhMucSpAdapter.MyViewHolder> {
    Context context;
    List<DanhMucSp> array;

    public DanhMucSpAdapter(Context context, List<DanhMucSp> array) {
        this.context = context;
        this.array = array;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DanhMucSp loaisp = array.get(position);
        holder.txtten.setText(loaisp.getTendanhmuccon());

        Glide.with(context).load(loaisp.getHinhanhdmcon()).into(holder.imhinhanh);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {

                if (!isLongClick) {
                    switch (pos) {
                        case 0:
                            Intent dienthoai = new Intent(context, DienThoaiActivity2.class);
                            dienthoai.putExtra("loai", 2);
                            dienthoai.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(dienthoai);
                            break;
                        case 1:
                            Intent intent = new Intent(context, LapTopActivity2.class);
                            intent.putExtra("loai", 1);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            break;
                        case 2:
                            Intent tainghe = new Intent(context, TaiNgheMainActivity.class);
                            tainghe.putExtra("loai", 3);
                            tainghe.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(tainghe);
                            break;
                        case 3:
                            Intent dongho = new Intent(context, DongHoMainActivity.class);
                            dongho.putExtra("loai", 4);
                            dongho.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(dongho);
                            break;
                        case 4:
                            Intent tivi = new Intent(context, TiviMainActivity.class);
                            tivi.putExtra("loai", 5);
                            tivi.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(tivi);
                            break;
                        case 5:
                            Intent phukien = new Intent(context, PhuKienActivity2.class);
                            phukien.putExtra("loai", 6);
                            phukien.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(phukien);
                            break;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtten;
        ImageView imhinhanh;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtten = itemView.findViewById(R.id.txtten);
            imhinhanh = itemView.findViewById(R.id.imhinhanh);
            itemView.setOnClickListener(this);
            // ánh xạ
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }
}