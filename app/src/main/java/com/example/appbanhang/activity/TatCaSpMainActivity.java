package com.example.appbanhang.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.adapter.SanPhamAllAdapter;
import com.example.appbanhang.databinding.ActivityTatCaSpMainBinding;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appbanhang.model.SanPhamMoi;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitCilent;
import com.example.appbanhang.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TatCaSpMainActivity extends mau {

    ActivityTatCaSpMainBinding binding;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    List<SanPhamMoi> mangSpmoi;
    SanPhamAllAdapter spAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTatCaSpMainBinding.inflate(getLayoutInflater());
        apiBanHang = RetrofitCilent.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toobar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toobar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSphamall();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.viewAll.setLayoutManager(layoutManager);
        binding.viewAll.setHasFixedSize(true);
    }
    private void getSphamall() {
        compositeDisposable.add(apiBanHang.getSpMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sanPhamMoiModel -> {
                            if(sanPhamMoiModel.isSuccess()){
                                mangSpmoi = sanPhamMoiModel.getResult();
                                spAdapter = new SanPhamAllAdapter(getApplicationContext(),mangSpmoi);
                                binding.viewAll.setAdapter(spAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Không Kết nối được với sever"+throwable.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                ));
    }
}