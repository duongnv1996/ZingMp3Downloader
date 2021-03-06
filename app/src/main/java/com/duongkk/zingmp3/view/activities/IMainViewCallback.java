package com.duongkk.zingmp3.view.activities;

import com.duongkk.zingmp3.model.ZingModel;

/**
 * Created by DuongKK on 9/11/2017.
 */

public interface IMainViewCallback {
    void onGetInforSuccess(ZingModel model);
    void onFail(Exception e);
    void onGetLinkSuccess(String url);
}
