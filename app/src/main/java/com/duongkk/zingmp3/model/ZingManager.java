package com.duongkk.zingmp3.model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DuongKK on 9/11/2017.
 */

public class ZingManager extends BaseManager {
    public ZingManager(IZingCallback mZingCallBack) {
        this.mZingCallBack = mZingCallBack;
    }

    private IZingCallback mZingCallBack;

    public void getInfor(String url) {
        Call<ZingModel> getZing = getmApi().getLink(url);
        getZing.enqueue(new Callback<ZingModel>() {
            @Override
            public void onResponse(Call<ZingModel> call, Response<ZingModel> response) {
                if (response.body() != null) {
                    mZingCallBack.onSucess(response.body());
                } else {
                    mZingCallBack.onFail(new Exception("No information was found"));
                }
            }

            @Override
            public void onFailure(Call<ZingModel> call, Throwable t) {
                mZingCallBack.onFail(new Exception(t.getMessage()));
            }
        });

    }

    public void getLinkDownload(String url) {
        Call<String> getZing = getmApi().download(url);
        getZing.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    mZingCallBack.onGetLinkDownloadSucess(response.body());
                } else {
                    mZingCallBack.onFail(new Exception("No information was found"));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mZingCallBack.onFail(new Exception(t.getMessage()));
            }
        });

    }
}
