package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.DanhMucSpAdapter;
import com.example.appbanhang.adapter.LoaiSpAdapter;
import com.example.appbanhang.adapter.SanPhamBanChayAdapter;
import com.example.appbanhang.adapter.SanPhamMoiAdapter;
import com.example.appbanhang.model.DanhMucSp;
import com.example.appbanhang.model.DanhMucSpModel;
import com.example.appbanhang.model.Loaisp;
import com.example.appbanhang.model.SanPhamMoi;
import com.example.appbanhang.model.SanPhamMoiModel;
import com.example.appbanhang.model.User;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitCilent;
import com.example.appbanhang.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends mau {
    Toolbar toolbar;
    EditText editTextSeach;
    LinearLayout linearLayoutSeach, an, layAi;
    //ViewFlipper viewFlipper;
    ImageSlider imageSlider;
    NavigationView navigationView;
    RecyclerView recyclerViewManhinhchinh, recyclerViewdanhmucsp, recyclerViewhangmoinhat;
    ListView listViewManhinhchinh;
    DrawerLayout drawerLayout;
    LoaiSpAdapter loaiSpAdapter;
    SanPhamBanChayAdapter banChayAdapter;
    List<Loaisp> mangloaisp;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    List<SanPhamMoi> mangSpmoi;
    List<DanhMucSp> danhMucSpList;
    SanPhamMoiAdapter spAdapter;
    DanhMucSpAdapter danhMucSpAdapter;
    NotificationBadge badge;
    FrameLayout frameLayout;
    ImageView back, login, imageMess, setting, live;
    TextView username, viewall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanHang = RetrofitCilent.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Paper.init(this);
        if (Paper.book().read("user") != null) {
            User user = Paper.book().read("user");
            Utils.user_current = user;
        }
        getToken();
        Anhxa();
        ActionBar();
        if (isConnected(this)) {
            check();
            ActionViewFlipper();
            getLoaiSanPham();
            //getSpMoi();
            getdanhmucSp();
            getSphangmoi();
            getSphangbanchay();
            getEvenClick();// menu
        } else {
            Toast.makeText(getApplicationContext(), "Không có kết nối Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void getToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (!TextUtils.isEmpty(s)) {
                            compositeDisposable.add(apiBanHang.updateToken(Utils.user_current.getId(), s)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            messageModel -> {

                                            },
                                            throwable -> {
                                                Log.d("log", throwable.getMessage());
                                            }
                                    ));
                        }
                    }
                });
        compositeDisposable.add(apiBanHang.gettoken(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if (userModel.isSuccess()) {
                                Utils.ID_RECEIVED = String.valueOf(userModel.getResult().get(0).getId());
                            }
                        },
                        throwable -> {

                        }
                ));
    }

    private void check() {
        Toast.makeText(getApplicationContext(), "Xin chào bạn: " + Utils.user_current.getUsername().toUpperCase(), Toast.LENGTH_SHORT).show();
    }

    private void getEvenClick() {
        listViewManhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        Intent donhangdamua = new Intent(getApplicationContext(), XemDonActivity.class);
                        startActivity(donhangdamua);
                        break;
                    case 2:
                        Intent thongtin = new Intent(getApplicationContext(), ThongTinMainActivity.class);
                        startActivity(thongtin);
                        break;
                    case 3:
                        Intent lienhe = new Intent(getApplicationContext(), LienHeMainActivity.class);
                        startActivity(lienhe);
                        break;
                    case 4:
                        Intent thongke = new Intent(MainActivity.this, ThongKeMainActivity.class);
                        startActivity(thongke);
                        break;
                    case 5:
                        Paper.book().delete("user");
                        Paper.book().delete("giohang");
                        FirebaseAuth.getInstance().signOut();
                        Intent dangxuat = new Intent(getApplicationContext(), DangNhapActivity.class);
                        startActivity(dangxuat);
                        break;
                }
            }
        });
    }

    private void getSphangmoi() {
        compositeDisposable.add(apiBanHang.gethangmoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()) {
                                mangSpmoi = sanPhamMoiModel.getResult();
                                spAdapter = new SanPhamMoiAdapter(getApplicationContext(), mangSpmoi);
                                recyclerViewhangmoinhat.setAdapter(spAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không Kết nối được với sever" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void getSphangbanchay() {
        compositeDisposable.add(apiBanHang.gethangbanchay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()) {
                                mangSpmoi = sanPhamMoiModel.getResult();
                                banChayAdapter = new SanPhamBanChayAdapter(getApplicationContext(), mangSpmoi);
                                recyclerViewManhinhchinh.setAdapter(banChayAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không Kết nối được với sever" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void getdanhmucSp() {
        compositeDisposable.add(apiBanHang.getdanhmuc()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(danhMucSpModel -> {
                            if (danhMucSpModel.isSuccess()) {
                                danhMucSpList = danhMucSpModel.getResult();
                                danhMucSpAdapter = new DanhMucSpAdapter(getApplicationContext(), danhMucSpList);
                                recyclerViewdanhmucsp.setAdapter(danhMucSpAdapter);
                            }

                        }
                ));
    }

    private void getLoaiSanPham() {
        compositeDisposable.add(apiBanHang.getLoaiSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSpModel -> {
                            if (loaiSpModel.isSuccess()) {
                                mangloaisp = loaiSpModel.getResult();
                                mangloaisp.add(new Loaisp("Thống kê", "https://tse3.mm.bing.net/th?id=OIP.PWAttRDtQyfSAyS8dUi_DQAAAA&pid=Api&P=0&h=180"));
                                mangloaisp.add(new Loaisp("Đăng Xuất", "data:image/jpeg;base64,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"));
                                loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(), mangloaisp);
                                listViewManhinhchinh.setAdapter(loaiSpAdapter);
                            }
                        }
                ));
    }

    private void ActionViewFlipper() {
        List<SlideModel> imagelist = new ArrayList<>();
        compositeDisposable.add(apiBanHang.getquangcao()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        quangCaoModel -> {
                            if (quangCaoModel.isSuccess()) {
                                for (int i = 0; i < quangCaoModel.getResult().size(); i++) {
                                    imagelist.add(new SlideModel(quangCaoModel.getResult().get(i).getUrl(), null));
                                }
                                imageSlider.setImageList(imagelist, ScaleTypes.CENTER_CROP);
                                imageSlider.setItemClickListener(new ItemClickListener() {
                                    @Override
                                    public void onItemSelected(int i) {
                                        Intent quangcao = new Intent(getApplicationContext(), QuangCaoActivity.class);
                                        quangcao.putExtra("noidung", quangCaoModel.getResult().get(i).getthongtin());
                                        quangcao.putExtra("url", quangCaoModel.getResult().get(i).getUrl());
                                        startActivity(quangcao);
                                    }

                                    @Override
                                    public void doubleClick(int i) {

                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "Co loi!", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Log.d("log", throwable.getMessage());
                        }
                ));


        // List<SlideModel> imagelist = new ArrayList<>();
        // imagelist.add(new SlideModel("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png", null));//null chú thích ảnh
        // imagelist.add(new SlideModel("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png",null));
        // imagelist.add(new SlideModel("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png",null));
        //imageSlider.setImageList(imagelist, ScaleTypes.CENTER_CROP);
//        List<String> mangquancao = new ArrayList<>();
//        mangquancao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png");
//        mangquancao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png");
//        mangquancao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png");
//        for(int i =0 ; i<mangquancao.size();i++){
//            ImageView imageView = new ImageView(getApplicationContext());
//            Glide.with(getApplicationContext()).load(mangquancao.get(i)).into(imageView);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            viewFlipper.addView(imageView);
//        }
//        viewFlipper.setFlipInterval(3000);
//        viewFlipper.setAutoStart(true);
//        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
//        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
//        viewFlipper.setInAnimation(slide_in);
//        viewFlipper.setOutAnimation(slide_out);
    }

    private void ActionBar() {
        username.setText(Utils.user_current.getUsername().toUpperCase() + "");
    }

    private void Anhxa() {
        imageSlider = findViewById(R.id.image_slider);
        layAi = findViewById(R.id.lay_ai);
        back = findViewById(R.id.back);
        live = findViewById(R.id.image_live);
        setting = findViewById(R.id.img_setting);
        viewall = findViewById(R.id.all);
        an = findViewById(R.id.an);
        username = findViewById(R.id.username);
        editTextSeach = findViewById(R.id.editTextText);
        login = findViewById(R.id.imglogin);
        linearLayoutSeach = findViewById(R.id.search);
        // imgsearch = findViewById(R.id.imgsearch);
        imageMess = findViewById(R.id.image_mess);
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        //viewFlipper = findViewById(R.id.viewlipper);

        recyclerViewdanhmucsp = findViewById(R.id.recycleview_danhmucsanpham);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerViewdanhmucsp.setLayoutManager(layoutManager3);
        recyclerViewdanhmucsp.setHasFixedSize(true);

        recyclerViewhangmoinhat = findViewById(R.id.recycleview_moi);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerViewhangmoinhat.setLayoutManager(layoutManager2);
        recyclerViewhangmoinhat.setHasFixedSize(true);

        recyclerViewManhinhchinh = findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerViewManhinhchinh.setLayoutManager(layoutManager);
        recyclerViewManhinhchinh.setHasFixedSize(true);


        listViewManhinhchinh = findViewById(R.id.listviewmanhinhchinh);
        navigationView = findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);
        badge = findViewById(R.id.menu_sl);
        frameLayout = findViewById(R.id.frametc);
        mangloaisp = new ArrayList<>();
        mangSpmoi = new ArrayList<>();
        if (Paper.book().read("giohang") != null) {
            Utils.manggiohang = Paper.book().read("giohang");
        }
        if (Utils.manggiohang == null) {
            Utils.manggiohang = new ArrayList<>();
        } else {
            int totalItem = 0;
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });
        layAi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatai = new Intent(getApplicationContext(), ChatAiActivity.class);
                startActivity(chatai);
            }
        });
        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent live = new Intent(getApplicationContext(), MeetingActivity.class);
                startActivity(live);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                an.setVisibility(View.GONE);
            }
        });
        imageMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mess = new Intent(getApplicationContext(), ChatMainActivity.class);
                startActivity(mess);
            }
        });
        linearLayoutSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(getApplicationContext(), SearchActivity.class);
                search.putExtra("Text_search", editTextSeach.getText().toString().trim());
                startActivity(search);
                editTextSeach.clearFocus();
                editTextSeach.setText("");
            }
        });
        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TatCaSpMainActivity.class);
                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().delete("user");
                Paper.book().delete("giohang");
                FirebaseAuth.getInstance().signOut();
                Intent login = new Intent(getApplicationContext(), DangNhapActivity.class);
                startActivity(login);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for (int i = 0; i < Utils.manggiohang.size(); i++) {
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); // nho them quyen vao khong bi loi
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

}