package com.example.appbanhang.utils;

import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
   public static final String BASE_URL="http://192.168.1.3/banhang/";//cùng địa chỉ mạng internet
    //public static final String BASE_URL="http://192.168.1.5/banhang/";
   // public static final String BASE_URL="http://172.20.10.3/banhang/";
    // nho ca doi file reset.php

    public static List<GioHang> manggiohang;
    public static List<GioHang> mangmuahang = new ArrayList<>();
    public static User user_current = new User();
    public static String ID_RECEIVED;
    public static final String SENDID = "idsend";
    public static final String MESS = "message";
    public static final String DATATIME = "datatime";
    public static final String RECEIVEDID = "idreceived";
    public static final String PATH_CHAT = "chat";
    // chuyển trạng thái đơn hàng về chuỗi
 public static String statusOder(int status){
  String result = "";
  switch (status){
   case 0:
    result = "Đơn hàng đang được xử lý";
    break;
   case 1:
    result = "Đơn hàng đã chấp nhận";
    break;
   case 2:
    result = "Đơn hàng đã giao cho đơn vị vận chuyển";
    break;
   case 3:
    result = "Đơn hàng đã giao thành công";
    break;
   case 4:
    result = "Đơn hàng đã bị hủy";
    break;
  }
  return result;
 }
}
