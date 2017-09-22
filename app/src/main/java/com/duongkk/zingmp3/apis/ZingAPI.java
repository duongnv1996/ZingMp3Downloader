package com.duongkk.zingmp3.apis;


import com.duongkk.zingmp3.model.ZingModel;
import com.duongkk.zingmp3.model.ZingMp3Reponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @GET(ApiConstants.API_DOWNLOAD_TOOL)
    Call<ResponseBody> downloadToolHighQ(@Query("q") String quality, @Query("link") String link
    );
    @GET("{url}")
    Call<ZingMp3Reponse> getInforZingMp3(@Path(value = "url",encoded = true) String url
    );
}
