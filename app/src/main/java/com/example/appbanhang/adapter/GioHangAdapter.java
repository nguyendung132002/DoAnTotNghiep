package com.example.appbanhang.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Interface.ImageClick;
import com.example.appbanhang.R;
import com.example.appbanhang.model.EventBus.TinhTongEvent;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

import io.paperdb.Paper;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHold> {
    Context context;
    List<GioHang> gioHangList;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public GioHangAdapter.MyViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false);
        return new MyViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangAdapter.MyViewHold holder, int position) {
        GioHang gioHang = gioHangList.get(position);
        holder.gh_tensp.setText(gioHang.getTensp());
        holder.gh_soluong.setText(gioHang.getSoluong()+" ");
        holder.sltk.setText("Số lượng trong kho còn: "+ gioHang.getSltonkho()+"");
       // Glide.with(context).load(gioHang.getHinhsp()).into(holder.itemgh_image);
        if (gioHang.getHinhsp().contains("http")) {
            Glide.with(context).load(gioHang.getHinhsp()).into(holder.itemgh_image);
        }
        else {
            String hinh = Utils.BASE_URL + "images/" + gioHang.getHinhsp();
            Glide.with(context).load(hinh).into(holder.itemgh_image);

        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.gh_gia.setText("Giá: "+ decimalFormat.format(gioHang.getGiasp()) + "đ");
        long gia = gioHang.getSoluong() * gioHang.getGiasp();
        holder.gh_giasp2.setText(decimalFormat.format(gia));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //Utils.mangmuahang.add(gioHang);
                    Utils.manggiohang.get(holder.getAdapterPosition()).setCheck(true);
                    if (!Utils.mangmuahang.contains(gioHang)){
                        Utils.mangmuahang.add(gioHang);
                    }
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }else {
                    Utils.manggiohang.get(holder.getAdapterPosition()).setCheck(false);
                    for (int i = 0; i<Utils.mangmuahang.size(); i++){
                        if (Utils.mangmuahang.get(i).getIdsp() == gioHang.getIdsp()){
                            Utils.mangmuahang.remove(i);
                            EventBus.getDefault().postSticky(new TinhTongEvent());

                        }
                    }
                }
            }
        });
        holder.checkBox.setChecked(gioHang.isCheck());
        holder.setListenner(new ImageClick(){
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                Log.d("TAG","onImageClick" + pos +"..."+giatri);
                if(giatri ==1){
                    if (gioHangList.get(pos).getSoluong()>1){
                        int soluongmoi = gioHangList.get(pos).getSoluong()-1;
                        gioHangList.get(pos).setSoluong(soluongmoi);

                        holder.gh_soluong.setText(gioHangList.get(pos).getSoluong()+" ");
                        long gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGiasp();
                        holder.gh_giasp2.setText(decimalFormat.format(gia));
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    }else if(gioHangList.get(pos).getSoluong() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xóa sán phẩm này khỏi giỏ hàng không?");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utils.mangmuahang.remove(gioHang);
                                Utils.manggiohang.remove(pos);
                                Paper.book().write("giohang",Utils.manggiohang);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongEvent());
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                        }
                        });
                        builder.show();
                    }
                } else if (giatri == 2) {
                    if (gioHangList.get(pos).getSoluong() < gioHang.getSltonkho()) {
                        int soluongmoi = gioHangList.get(pos).getSoluong() + 1;
                        gioHangList.get(pos).setSoluong(soluongmoi);

                        holder.gh_soluong.setText(gioHangList.get(pos).getSoluong()+" ");
                        long gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGiasp();
                        holder.gh_giasp2.setText(decimalFormat.format(gia));
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    }else{
                        Toast.makeText(context.getApplicationContext(), "Số lượng bạn muốn mua đã hơn số lượng hàng còn trong kho",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class MyViewHold extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView itemgh_image, imgtru,imgcong;
        TextView gh_tensp, gh_gia, gh_soluong, gh_giasp2,sltk;
        ImageClick listenner;
        CheckBox checkBox;
        public MyViewHold(@NonNull View itemView){
            super(itemView);
            itemgh_image = itemView.findViewById(R.id.itemgh_image);
            sltk = itemView.findViewById(R.id.sltk);
            gh_tensp = itemView.findViewById(R.id.itemgh_tensp);
            gh_gia = itemView.findViewById(R.id.itemgh_gia);
            gh_soluong = itemView.findViewById(R.id.itemgh_sluong);
            gh_giasp2 = itemView.findViewById(R.id.itemgh_giasp2);
            imgtru = itemView.findViewById(R.id.itemgh_tru);
            imgcong = itemView.findViewById(R.id.itemgh_cong);
            checkBox = itemView.findViewById(R.id.item_giohang_check);
            // event click imang cong tru
            imgcong.setOnClickListener(this);
            imgtru.setOnClickListener(this);

        }

        public void setListenner(ImageClick listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View v) {
            if(v == imgtru){
                listenner.onImageClick(v,getAdapterPosition(),1);
            } else if (v == imgcong) {
                listenner.onImageClick(v,getAdapterPosition(),2);

            }
        }
    }
}
