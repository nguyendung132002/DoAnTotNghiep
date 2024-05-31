package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.appbanhang.R;

import io.paperdb.Paper;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Paper.init(this);
        Thread thread = new Thread(){
            @Override
            public void run(){
               try{
                   sleep(3500);

               } catch (InterruptedException e) {

               }finally {
                   if(Paper.book().read("user") == null){
                       startActivity(new Intent(splash.this,DangNhapActivity.class));
                       finish();
                   }else{
                       startActivity(new Intent(splash.this,MainActivity.class));
                       finish();
                   }
               }
            }
        };thread.start();
    }
}