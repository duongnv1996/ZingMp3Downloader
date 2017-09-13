package com.duongkk.zingmp3.apis;


import com.duongkk.zingmp3.model.ZingModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by MyPC on 9/12/2016.
 */
public interface ZingAPI {


    @FormUrlEncoded
    @POST(ApiConstants.API_GETLINK)
    Call<ZingModel> getLink(@Field("submit") String url);

    @FormUrlEncoded
    @POST(ApiConstants.API_DOWNLOAD)
    Call<String> download(@Field("submit") String url
          );

}
