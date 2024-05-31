package com.example.appbanhang.retrofit;

import io.reactivex.rxjava3.core.Observable;

import com.example.appbanhang.model.DanhMucSpModel;
import com.example.appbanhang.model.DonHangModel;
import com.example.appbanhang.model.LoaiSpModel;
import com.example.appbanhang.model.MeetingModel;
import com.example.appbanhang.model.MessageModel;
import com.example.appbanhang.model.QuangCaoModel;
import com.example.appbanhang.model.SanPhamMoiModel;
import com.example.appbanhang.model.ThongKeModel;
import com.example.appbanhang.model.UserModel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiBanHang {
    @GET("getmeeting.php")
    Observable<MeetingModel> getMeeting();


    @GET("getquangcao.php")
    Observable<QuangCaoModel> getquangcao();
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();
    @GET("getdanhmucsanpham.php")
    Observable<DanhMucSpModel> getdanhmuc();
    @GET("banchaynhat.php")
    Observable<SanPhamMoiModel> gethangbanchay();
    @GET("hangmoi.php")
    Observable<SanPhamMoiModel> gethangmoi();
    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSpMoi();
    @GET("getdanhgia.php")
    Observable<SanPhamMoiModel> getDanhGia(@Query("id") int id);

    @GET("getdanhgia.php")
    Observable<SanPhamMoiModel> getDanhGiaa();
    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getSanPham(
            @Field("page") int page,
            @Field("loai") int loai
    );
    @POST("updatetoken.php")
    @FormUrlEncoded
    Observable<MessageModel> updateToken(
            @Field("id") int id,
            @Field("token") String token
    );
    @POST("momo.php")
    @FormUrlEncoded
    Observable<MessageModel> updateMomo(
            @Field("id") int id,
            @Field("token") String token
    );
    @POST("dangki.php")
    @FormUrlEncoded
    Observable<UserModel> dangKi(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("username") String username,
            @Field("mobile") String mobile,
            @Field("hinhanh") String hinhanh,
//            @Field("gioitinh") String gioitinh,
//            @Field("ngaysinh") String ngaysinh,
            @Field("uid") String uid
    );
    @POST("capnhattt.php")
    @FormUrlEncoded
    Observable<UserModel> capnhattt(
            @Field("email") String email,
            @Field("username") String username,
           // @Field("mobile") String mobile,
            @Field("hinhanh") String hinhanh,
//            @Field("gioitinh") String gioitinh,
//            @Field("ngaysinh") String ngaysinh,
            @Field("uid") String uid
    );
    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangNhap(
            @Field("email") String email,
            @Field("pass") String pass
    );
    @POST("reset.php")
    @FormUrlEncoded
    Observable<UserModel> resetPass(
            @Field("email") String email
    );
    @POST("donhang.php")
    @FormUrlEncoded
    Observable<MessageModel> creatOder(
            @Field("email") String email,
            @Field("sdt") String sdt,
            @Field("tongtien") String tongtien,
            @Field("iduser") int id,
            @Field("diachi") String diachi,
            @Field("soluong") int soluong,
            @Field("chitiet") String chitiet
    );
    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
            @Field("iduser") int id
    );
    @GET("thongke.php")
    Observable<ThongKeModel> getthongke();
    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> search(
            @Field("search") String search
    );
    @POST("gettoken.php")
    @FormUrlEncoded
    Observable<UserModel> gettoken(
            @Field("status") int status
    );
    @POST("updateorder.php")
    @FormUrlEncoded
    Observable<MessageModel> updateOrder(
            @Field("id") int id,
            @Field("trangthai") int trangthai
    );
    @POST("gettoken.php")
    @FormUrlEncoded
    Observable<UserModel> gettokenn(
            @Field("status") int status,
            @Field("iduser") int iduser
    );
    @POST("updatedanhgiasao.php")
    @FormUrlEncoded
    Call<MessageModel> updatedanhgiasao(
            @Field("idsp") int idsp,
            @Field("iddonhang") int iddonhang,
            @Field("danhgiasao") int danhgiasao

);
    @POST("updatebinhluan.php")
    @FormUrlEncoded
    Call<MessageModel> updatebinhluan(
            @Field("idsp") int idsp,
            @Field("iddonhang") int iddonhang,
            @Field("binhluan") String binhluan

    );
    @Multipart
    @POST("upload.php")
    Call<MessageModel> uploadFile(@Part MultipartBody.Part file);
    @Multipart
    @POST("uploadavatar.php")
    Call<MessageModel> uploadFileAvatar(@Part MultipartBody.Part file);
}
