package com.example.appbanhang.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.model.Item;
import com.example.appbanhang.model.MessageModel;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitCilent;
import com.example.appbanhang.utils.Utils;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChitietdonhangdamuaAdapter extends RecyclerView.Adapter<ChitietdonhangdamuaAdapter.MyViewHold> {
    Context context;
    List<Item> itemList;
    ApiBanHang apiBanHang;

    public ChitietdonhangdamuaAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
        apiBanHang = RetrofitCilent.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
    }

    @NonNull
    @Override
    public MyViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitietdonhang, parent, false);

        return new MyViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHold holder, int position) {
        Item item = itemList.get(position);
        holder.txtten.setText(item.getTensp() + "");
        holder.txtsoluong.setText("Số Lượng: " + item.getSoluong());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgia.setText("Giá: " + decimalFormat.format(item.getGia()) + "đ");
        //Glide.with(context).load(item.getHinhanh()).into(holder.imagechitiet);
        if (item.getHinhanh().contains("http")) {
            Glide.with(context).load(item.getHinhanh()).into(holder.imagechitiet);
        } else {
            String hinh = Utils.BASE_URL + "images/" + item.getHinhanh();
            Glide.with(context).load(hinh).into(holder.imagechitiet);

        }
        if (item.getTrangthai() == 0 || item.getTrangthai() == 1 || item.getTrangthai() == 2 || item.getTrangthai() == 4) {
            holder.thongbao.setVisibility(View.VISIBLE);
            holder.lay.setVisibility(View.GONE);
        } else {
            holder.ratingBar.setRating(item.getDanhgiasao());
            holder.editText.setText(item.getBinhluan());

            if (item.getDanhgiasao() > 0) {
                holder.send.setVisibility(View.GONE);
                holder.sua.setVisibility(View.VISIBLE);
            }
            holder.send.setOnClickListener(v -> {
                float rating = holder.ratingBar.getRating();
                int ratingInt = Math.round(rating);
                String comment = holder.editText.getText().toString();
                holder.editText.clearFocus();
                Log.e("danhgia", ratingInt + "" + ",,," + "" + item.getIdsp() + ",,," + item.getIddonhang() + ",,," + comment);
                if (ratingInt <= 0) {
                    Toast.makeText(context, "vui lòng chọn số sao nhiều hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (comment.isEmpty()) {
                    Toast.makeText(context, "vui lòng nhập đánh giá của bạn trước khi gửi", Toast.LENGTH_SHORT).show();
                    return;
                }
                //an ban phi sau khi an send
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(holder.editText.getWindowToken(), 0);
                // Gửi dữ liệu đánh giá và bình luận lên API
                Call<MessageModel> call = apiBanHang.updatedanhgiasao(
                        item.getIdsp(),
                        item.getIddonhang(),
                        ratingInt

                );
                call.enqueue(new Callback<MessageModel>() {
                    @Override
                    public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                        if (response.isSuccessful()) {

                        } else {
                            Toast.makeText(context, "Failed to submit review", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageModel> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                Call<MessageModel> binhluan = apiBanHang.updatebinhluan(
                        item.getIdsp(),
                        item.getIddonhang(),
                        comment

                );
                binhluan.enqueue(new Callback<MessageModel>() {
                    @Override
                    public void onResponse(Call<MessageModel> binhluan, Response<MessageModel> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context.getApplicationContext(), "Bạn đã đánh giá đơn hàng thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Failed to submit review", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageModel> binhluan, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                holder.sua.setVisibility(View.VISIBLE);
                holder.send.setVisibility(View.GONE);
            });
            holder.sua.setOnClickListener(v -> {
                float rating = holder.ratingBar.getRating();
                int ratingInt = Math.round(rating);
                String comment = holder.editText.getText().toString();
                holder.editText.clearFocus();
                Log.e("danhgia", ratingInt + "" + ",,," + "" + item.getIdsp() + ",,," + item.getIddonhang() + ",,," + comment);
                if (ratingInt <= 0) {
                    Toast.makeText(context, "vui lòng chọn số sao nhiều hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (comment.isEmpty()) {
                    Toast.makeText(context, "vui lòng nhập đánh giá của bạn trước khi gửi", Toast.LENGTH_SHORT).show();
                    return;
                }
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(holder.editText.getWindowToken(), 0);

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn sửa đánh giá của sản phẩm  không?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Gửi dữ liệu đánh giá và bình luận lên API
                        Call<MessageModel> call = apiBanHang.updatedanhgiasao(
                                item.getIdsp(),
                                item.getIddonhang(),
                                ratingInt
                        );
                        call.enqueue(new Callback<MessageModel>() {
                            @Override
                            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                                if (response.isSuccessful()) {

                                } else {
                                    Toast.makeText(context, "Failed to submit review", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<MessageModel> call, Throwable t) {
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        Call<MessageModel> binhluan = apiBanHang.updatebinhluan(
                                item.getIdsp(),
                                item.getIddonhang(),
                                comment
                        );
                        binhluan.enqueue(new Callback<MessageModel>() {
                            @Override
                            public void onResponse(Call<MessageModel> binhluan, Response<MessageModel> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(context.getApplicationContext(), "Bạn đã sửa đánh giá đơn hàng thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Failed to submit review", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<MessageModel> binhluan, Throwable t) {
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.ratingBar.setRating(item.getDanhgiasao());
                        holder.editText.setText(item.getBinhluan());
                        dialog.dismiss();
                    }
                });
                builder.show();
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHold extends RecyclerView.ViewHolder {
        ImageView imagechitiet, send, sua;
        RatingBar ratingBar;
        LinearLayout lay,thongbao;
        EditText editText;
        TextView txtten, txtsoluong, txtgia, diachi;

        public MyViewHold(@NonNull View itemView) {
            super(itemView);
            imagechitiet = itemView.findViewById(R.id.item_imgchitiet);
            lay = itemView.findViewById(R.id.laydanhgia);
            thongbao = itemView.findViewById(R.id.thongbaodanhgia);
            diachi = itemView.findViewById(R.id.diachi_donhang);
            txtten = itemView.findViewById(R.id.item_tenspchitiet);
            txtgia = itemView.findViewById(R.id.item_giachitiet);
            txtsoluong = itemView.findViewById(R.id.item_soluongchitiet);
            editText = itemView.findViewById(R.id.danhgia);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            send = itemView.findViewById(R.id.btn_send);
            sua = itemView.findViewById(R.id.btn_edit);
        }
    }
}
