package com.duongkk.zingmp3lossless;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by DuongKK on 9/11/2017.
 */

public class AppController extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
