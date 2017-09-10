package com.duongkk.zingmp3lossless.apis;

import com.blankj.utilcode.util.LogUtils;
import com.duongkk.zingmp3lossless.interfaces.OnResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MyPC on 11/6/2016.
 */

public class CallBackCustomNoDelay<T> implements Callback<T> {
    OnResponse<T> t;
    public CallBackCustomNoDelay( OnResponse<T> t) {
        this.t = t;
    }
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            t.onResponse(response.body());
        } else {
            LogUtils.e("Error onRespone " + response.code());

        }
    }
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        LogUtils.e("error - callback " + t.getMessage());
    }
}
