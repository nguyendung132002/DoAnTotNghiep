package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.fragment.ViewerFragment;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitCilent;
import com.example.appbanhang.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import live.videosdk.rtc.android.Meeting;
import live.videosdk.rtc.android.VideoSDK;
import live.videosdk.rtc.android.listeners.MeetingEventListener;

public class MeetingActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Meeting meeting;
    TextView textmeeting;
    ApiBanHang apiBanHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        apiBanHang = RetrofitCilent.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        textmeeting = findViewById(R.id.textmeeting);
        getMeetingIdFromServer();
    }

    private void getMeetingIdFromServer() {
        compositeDisposable.add(apiBanHang.getMeeting()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meetingModel -> {
                            if (meetingModel.isSuccess()) {
                                String token = meetingModel.getResult().get(0).getToken();
                                String meetingId = meetingModel.getResult().get(0).getMeetingid();

                               // Log.e("loiiii",token.toString()+", "+ meetingId.toString()+"");

                                String mode = "VIEWER";
                                boolean streamEnable = mode.equals("CONFERENCE");
                                //khoi tao video
                                VideoSDK.initialize(getApplicationContext());
                                // Configuration VideoSDK with Token
                                VideoSDK.config(token);

                                // Initialize VideoSDK Meeting
                                meeting = VideoSDK.initMeeting(
                                        MeetingActivity.this, meetingId, "Live Bán Hàng",
                                        streamEnable, streamEnable, null, mode, false, null);

                                // join Meeting
                                meeting.join();
                                meeting.addEventListener(new MeetingEventListener() {
                                    @Override
                                    public void onMeetingJoined() {

                                        if (meeting != null) {
                                            if (mode.equals("VIEWER")) {
                                                textmeeting.setVisibility(View.GONE);
                                                getSupportFragmentManager()
                                                        .beginTransaction()
                                                        //thay the layout
                                                        .replace(R.id.mainLayout, new ViewerFragment(), "viewerFragment")
                                                        .commit();
                                            }
                                        }
                                    }
                                });
                            }
                       },
                        throwable -> {
                            Log.d("getmeeting", throwable.getMessage());
                        }
                ));
    }
    public Meeting getMeeting() {
        return meeting;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}