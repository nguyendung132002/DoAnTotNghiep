package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;

public class QuangCaoActivity extends mau {
    Toolbar toolbar;
    TextView thongtin;
    ImageView anh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quang_cao);
        anhxa();
        ActionToolBar();
        controler();
    }

    private void controler() {
        String nd = getIntent().getStringExtra("noidung");//main
        String url = getIntent().getStringExtra("url");//main
        thongtin.setText(nd);
        Glide.with(this).load(url).into(anh);
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

    private void anhxa() {
        toolbar = findViewById(R.id.toobar_ct);
        thongtin = findViewById(R.id.thongtin);
        anh = findViewById(R.id.quangcao);
    }
}