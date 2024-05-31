package com.example.appbanhang.retrofit;



import com.example.appbanhang.model.NotiResponse;
import com.example.appbanhang.model.NotiSendData;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIPushNofication {
    @Headers(
            {
                    "Content-Type: application/json",
                    "Authorization: key=AAAA9L1WK3I:APA91bHnS31gS1x5AFOLLUw0Mwu-X7ARccrj41pl0gHBI_XCM6PmWj5Y8YrVMtuoGuXB_RyVeIqtVAnVowzrEUUaqqvE7tqLQz3aQdBYBIhwCssTNneNnqe_BV8e9gsJtM4FZN4m5aQk"
            }
    )
    @POST("fcm/send")
    Observable<NotiResponse> sendNofitication(@Body NotiSendData data);
}
