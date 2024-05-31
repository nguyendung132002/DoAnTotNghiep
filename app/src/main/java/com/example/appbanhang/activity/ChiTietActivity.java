package com.example.appbanhang.activity;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.DanhGiaAdapter;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.model.SanPhamMoi;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitCilent;
import com.example.appbanhang.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChiTietActivity extends mau {
TextView tensp,giasp,mota, cauhinh, thongbao,soluongdaban,slkho;
Button btnthem,xemvideo;
RecyclerView danhgia;
ImageView imghinhanh,close;
Spinner spinner;
YouTubePlayerView playerView;
DanhGiaAdapter danhGiaAdapter;
ApiBanHang apiBanHang;
CompositeDisposable compositeDisposable = new CompositeDisposable();
Toolbar toolbar;
SanPhamMoi sanPhamMoi;
List<SanPhamMoi> itemList;

//so luong hang co trong gio hang
NotificationBadge badge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        apiBanHang = RetrofitCilent.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        initView();
        ActionToolBar();
        intiData();
        intiControl();
        getdanhgia();
    }



private void getdanhgia() {
    int idsp = sanPhamMoi.getId();
    Log.e("mang danh giaasfsd",sanPhamMoi.getId()+"");

        compositeDisposable.add(apiBanHang.getDanhGia(idsp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()) {
                                itemList = sanPhamMoiModel.getResult();
                                Log.e("mang danh gia",idsp+",,,"+ sanPhamMoi.getUsername()+",,,"+ sanPhamMoi.getHinhanhuser()+",,,"+sanPhamMoi.getDanhgiasao()+",,,"+sanPhamMoi.getBinhluan());

                                danhGiaAdapter = new DanhGiaAdapter(getApplicationContext(), itemList);
                                danhgia.setAdapter(danhGiaAdapter);
                            }
                        },
                        throwable -> {
                            Log.e("loi hien danh  gia", throwable.getMessage());
                        }));
    }
    private void intiControl() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themGioHang();
                Paper.book().write("giohang",Utils.manggiohang);
            }
        });
        xemvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close.setVisibility(View.VISIBLE);
                playerView.setVisibility(View.VISIBLE);
                playerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        String videoId = sanPhamMoi.getLinkvideo();
                        youTubePlayer.loadVideo(videoId,0);
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playerView.getYouTubePlayerWhenReady(YouTubePlayer::pause);
                        close.setVisibility(View.GONE);
                        playerView.setVisibility(View.GONE);
                    }
                });
            }
        });


    }

    private void themGioHang() {
        if(Utils.manggiohang.size()>0){
            boolean flag = false;
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            for(int i=0 ; i< Utils.manggiohang.size(); i++){
                if(Utils.manggiohang.get(i).getIdsp() == sanPhamMoi.getId()){
                    Utils.manggiohang.get(i).setSoluong(soluong +Utils.manggiohang.get(i).getSoluong());
                    flag = true;
                }
            }
            if(flag == false){
                //neu san pham chua ton tai tro gio hang tạo một đối tượng mới của lớp GioHang từ sản phẩm mới và thêm vào giỏ hàng
                long gia = Long.parseLong(sanPhamMoi.getGiasp()) ;
                GioHang gioHang = new GioHang();
                gioHang.setGiasp(gia);
                gioHang.setSoluong(soluong);
                gioHang.setIdsp(sanPhamMoi.getId());
                gioHang.setTensp(sanPhamMoi.getTensp());
                gioHang.setHinhsp(sanPhamMoi.getHinhanh());
                gioHang.setSltonkho(sanPhamMoi.getSltonkho());
                Utils.manggiohang.add(gioHang);
            }
        }else {
            //neu gio hang rong tao tạo một đối tượng mới của lớp GioHang từ sản phẩm mới và thêm vào giỏ hàng
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            long gia = Long.parseLong(sanPhamMoi.getGiasp()) ;
            GioHang gioHang = new GioHang();
            gioHang.setGiasp(gia);
            gioHang.setSoluong(soluong);
            gioHang.setIdsp(sanPhamMoi.getId());
            gioHang.setTensp(sanPhamMoi.getTensp());
            gioHang.setHinhsp(sanPhamMoi.getHinhanh());
            gioHang.setSltonkho(sanPhamMoi.getSltonkho());
            Utils.manggiohang.add(gioHang);

        }
        int totalItem =0;
        for (int i = 0; i< Utils.manggiohang.size();i++){
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    private void intiData() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        tensp.setText( sanPhamMoi.getTensp());
        mota.setText(sanPhamMoi.getMota());
        slkho.setText("Kho còn: "+sanPhamMoi.getSltonkho()+"");
        soluongdaban.setText("Số lượng đã bán: "+sanPhamMoi.getTong()+"");
        cauhinh.setText("Cấu hình "+ sanPhamMoi.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Giá: "+ decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasp()))+ "đ");



       // Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanh()).into(imghinhanh);
        if (sanPhamMoi.getHinhanh().contains("http")) {
            Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanh()).into(imghinhanh);
        }
        else {
            String hinh = Utils.BASE_URL + "images/" + sanPhamMoi.getHinhanh();
            Glide.with(getApplicationContext()).load(hinh).into(imghinhanh);

        }

        if(sanPhamMoi.getSltonkho()>=1){
            List<Integer> so = new ArrayList<>();
            for (int i =1;i<sanPhamMoi.getSltonkho()+1;i++){
                so.add(i);
            }
            ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,so);
            spinner.setAdapter(adapterspin);
        }else {
            Toast.makeText(getApplicationContext(),"Mặt hàng này hiện đã hết!",Toast.LENGTH_LONG).show();
            thongbao.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.GONE);
            btnthem.setVisibility(View.GONE);
        }


    }

    private void ActionToolBar() {

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }


    private void initView() {
        playerView = findViewById(R.id.youtobe);


        danhgia = findViewById(R.id.recycleview_phanhoi);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        danhgia.setLayoutManager(layoutManager);
        danhgia.setHasFixedSize(true);
        itemList = new ArrayList<>();

        close = findViewById(R.id.back);
        soluongdaban = findViewById(R.id.sldb);
        slkho = findViewById(R.id.sltk);
        cauhinh = findViewById(R.id.cauhinh);
        xemvideo = findViewById(R.id.xemvideo);
        thongbao = findViewById(R.id.thongbao);
        tensp = findViewById(R.id.txttensp);
        giasp = findViewById(R.id.txtgiasp);
        mota = findViewById(R.id.txtchitiet);
        btnthem = findViewById(R.id.btn_themvaogiohang);
        spinner = findViewById(R.id.spinner);
        imghinhanh = findViewById(R.id.imgchitiet);
        toolbar = findViewById(R.id.toobar_ct);
        badge = findViewById(R.id.menu_sl);
        FrameLayout frameLayoutgiohang = findViewById(R.id.framegh);
        frameLayoutgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent giohang = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(giohang);
            }
        });
        if(Utils.manggiohang != null){
            int totalItem =0;
            for (int i = 0; i< Utils.manggiohang.size();i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.manggiohang != null){
            int totalItem =0;
            for (int i = 0; i< Utils.manggiohang.size();i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}