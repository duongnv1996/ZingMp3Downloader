package com.duongkk.zingmp3.model;

/**
 * Created by DuongKK on 9/11/2017.
 */

public interface IZingCallback {
    void onSucess(ZingModel zingModel);
    void onFail(Exception e);
    void onGetLinkDownloadSucess(String url);
}
