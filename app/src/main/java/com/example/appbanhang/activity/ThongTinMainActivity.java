package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.model.User;
import com.example.appbanhang.utils.Utils;

import java.util.List;

public class ThongTinMainActivity extends mau {
    Toolbar toolbar;
    TextView email, sdt, ten, ngaysinh, gioitinh;
    Button capnhat;
    ImageView hinhanh;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tinmain);
        anhxa();
        initControl();
    }

    private void anhxa() {
        toolbar = findViewById(R.id.toobar);
        ten = findViewById(R.id.ten);
        capnhat = findViewById(R.id.btn_capnhat);
        email = findViewById(R.id.email);
        sdt = findViewById(R.id.sdt);
        hinhanh = findViewById(R.id.img_avatar);
        //gioitinh = findViewById(R.id.gioitinh);
        //ngaysinh = findViewById(R.id.ngaysinh);
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CapnhatThongTinMainActivity.class);
               // intent.putExtra("sua", user);
                startActivity(intent);
            }
        });
        email.setText(Utils.user_current.getEmail());
        ten.setText(Utils.user_current.getUsername());
        sdt.setText(Utils.user_current.getMobile());
        if (Utils.user_current.getHinhanh() == null) {
            hinhanh.setImageResource(R.drawable.ic_person_24);
        } else if (Utils.user_current.getHinhanh().contains("http")) {
            Glide.with(getApplicationContext()).load(Utils.user_current.getHinhanh()).into(hinhanh);
        } else {
            String hinh = Utils.BASE_URL + "images/" + Utils.user_current.getHinhanh();
            Glide.with(getApplicationContext()).load(hinh).into(hinhanh);
        }
//        if (Utils.user_current.getGioitinh() == null) {
//            gioitinh.setText("Bạn chưa thiết lập");
//        }
//        if (Utils.user_current.getNgaysinh() == null) {
//            ngaysinh.setText("Bạn chưa thiết lập");
//        }
    }

}