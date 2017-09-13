package com.duongkk.zingmp3.model;

import com.blankj.utilcode.util.SPUtils;
import com.duongkk.zingmp3.apis.ApiUtils;
import com.duongkk.zingmp3.apis.ZingAPI;

/**
 * Created by DuongKK on 9/11/2017.
 */

public class BaseManager {
    private ZingAPI mApi;
    private SPUtils mSetting;

    public BaseManager(){
        mApi = ApiUtils.getRootApi().create(ZingAPI.class);
        mSetting = new SPUtils("setting");

    }

    public ZingAPI getmApi() {
        return mApi;
    }

    public SPUtils getmSetting() {
        return mSetting;
    }
}
