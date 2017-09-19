package com.duongkk.zingmp3.model;

import com.blankj.utilcode.util.LogUtils;
import com.duongkk.zingmp3.view.activities.MainToolActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
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

    public void getLinkToolDownload(final String url, final String q) {
        Call<ResponseBody> getZing = getmApi().downloadToolHighQ(q,url);
        getZing.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    try {
                        String result =response.body().string();
                        if(q.equals("lossless")) {
                            if (result.contains("http://mp3.zing.vn")) {
                                mZingCallBack.onFail(new Exception(result));
                                return;
                            }
                            mZingCallBack.onGetLinkDownloadSucess(result.replace("Location: ",""));
                        }else if(q.equals("320")) {
                            if(result.contains("Acc vip hết hạn rồi")) {
                                mZingCallBack.onFail(new Exception(result));
                                return;
                            }
                            mZingCallBack.onGetLinkDownloadSucess(String.format(MainToolActivity.url_fomat,"320",url));
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    mZingCallBack.onFail(new Exception("No information was found"));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mZingCallBack.onFail(new Exception(t.getMessage()));
            }
        });

    }

    public void getLinkToolDownloadN(String url) {
        Call<ResponseBody> getZing = getmApi().downloadToolHighQ("320",url);
        getZing.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    try {
                        String result =response.body().string();
                        if(result.contains("http://mp3.zing.vn")) {
                            mZingCallBack.onFail(new Exception("No information was found"));
                            return;
                        }
                        mZingCallBack.onGetLinkDownloadSucess(result.replace("Location: ",""));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    mZingCallBack.onFail(new Exception("No information was found"));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mZingCallBack.onFail(new Exception(t.getMessage()));
            }
        });

    }
}
