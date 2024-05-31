package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.Interface.ItemClickListener;
import com.example.appbanhang.R;
import com.example.appbanhang.model.DonHang;
import com.example.appbanhang.model.EventBus.DonHangEvent;
import com.example.appbanhang.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class DonHangDaMuaAdapter extends RecyclerView.Adapter<DonHangDaMuaAdapter.MyViewHold> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<DonHang> listdonhang;

    public DonHangDaMuaAdapter(Context context, List<DonHang> listdonhang) {
        this.context = context;
        this.listdonhang = listdonhang;
    }

    @NonNull
    @Override
    public MyViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent, false);

        return new MyViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHold holder, int position) {
        DonHang donHang = listdonhang.get(position);
        holder.txtdonhang.setText("Mã đơn hàng: " +donHang.getId()+"");
        holder.diachi.setText("Địa chỉ: " + donHang.getDiachi());
        holder.trangthai.setText(Utils.statusOder(donHang.getTrangthai()));
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txttongtien.setText(decimalFormat.format(donHang.getTongtien()) + "đ");
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.reChitiet.getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());
        // adapter chi tiet don hang da mua
        ChitietdonhangdamuaAdapter chitietdonhangdamuaAdapter = new ChitietdonhangdamuaAdapter(context, donHang.getItem());
        holder.reChitiet.setLayoutManager(layoutManager);
        holder.reChitiet.setAdapter(chitietdonhangdamuaAdapter);
        holder.reChitiet.setRecycledViewPool(viewPool);
        holder.setListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if(isLongClick){
                    EventBus.getDefault().postSticky(new DonHangEvent(donHang));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdonhang.size();
    }

    public class MyViewHold extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView txtdonhang, txttongtien, diachi,trangthai;
        RecyclerView reChitiet;
        ItemClickListener listener;

        public MyViewHold(@NonNull View itemView) {
            super(itemView);
            txtdonhang = itemView.findViewById(R.id.iddonhang);
            trangthai = itemView.findViewById(R.id.tinhtrang);
            txttongtien = itemView.findViewById(R.id.idtongtiendonhang);
            diachi = itemView.findViewById(R.id.diachi_donhang);
            reChitiet = itemView.findViewById(R.id.recycleview_chitetdonhang);
            itemView.setOnLongClickListener(this);
        }
        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }
        @Override
        public boolean onLongClick(View v) {
            listener.onClick(v, getAdapterPosition(), true);
            return false;
        }
    }
}
