package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.GioHangAdapter;
import com.example.appbanhang.model.EventBus.TinhTongEvent;
import com.example.appbanhang.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;


public class GioHangActivity extends mau {
    TextView giohangtrong, tongtien;
    ImageView back;
    Toolbar toolbar;
    RecyclerView recyclerView;
    LinearLayout thongbao;
    Button btnmua;
    AppCompatButton xoasanpham;
    long tong;
    GioHangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        Anhxa();
        intiControl();
        if (Utils.mangmuahang != null) {
            Utils.mangmuahang.clear();
        }
        //xoasanpham();
        tongtiensp();

    }

    private void xoasanpham() {
        xoasanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void tongtiensp() {
        tong = 0;
        for (int i = 0; i < Utils.mangmuahang.size(); i++) {
            tong = tong + (Utils.mangmuahang.get(i).getGiasp() * Utils.mangmuahang.get(i).getSoluong());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien.setText(decimalFormat.format(tong) + "đ");
    }

    private void intiControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (Utils.manggiohang.size() == 0) {
            giohangtrong.setVisibility(View.VISIBLE);
        } else {
            adapter = new GioHangAdapter(getApplicationContext(), Utils.manggiohang);
            recyclerView.setAdapter(adapter);
        }

        btnmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tong == 0) {
                    btnmua.setAlpha(0.3f);
                    recyclerView.setAlpha(0.3f);
                    thongbao.setVisibility(View.VISIBLE);
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnmua.setAlpha(1.0f);
                            recyclerView.setAlpha(1.0f);
                            thongbao.setVisibility(View.GONE);
                        }
                    });
                } else {
                    Intent intent = new Intent(getApplicationContext(), ThanhToanActivity.class);
                    intent.putExtra("tongtien", tong);
                    // Utils.manggiohang.clear();
                    startActivity(intent);
                }
        }
    });
}


    private void Anhxa() {
        giohangtrong = findViewById(R.id.txtgiohangtrong);
        tongtien = findViewById(R.id.txttongtien);
        thongbao = findViewById(R.id.thongbao);
        back = findViewById(R.id.back);
        toolbar = findViewById(R.id.toobar_gh);
        recyclerView = findViewById(R.id.recycleviewgiohang);
        btnmua = findViewById(R.id.btn_muahang);
        // xoasanpham = findViewById(R.id.btn_xoasp);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventTinhTien(TinhTongEvent event) {
        if (event != null) {
            tongtiensp();
        }
    }
}