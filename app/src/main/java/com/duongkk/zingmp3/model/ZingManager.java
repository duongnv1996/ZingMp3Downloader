package com.duongkk.zingmp3.model;

import android.os.AsyncTask;

import com.duongkk.zingmp3.view.activities.MainToolActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DuongKK on 9/11/2017.
 */

public class ZingManager extends BaseManager {
    Document doc;

    public ZingManager(IZingCallback mZingCallBack) {
        this.mZingCallBack = mZingCallBack;
    }

    private IZingCallback mZingCallBack;

    public void getInfor(final String url) {
        new AsyncTask<Void, String, String>() {
            String urlInfo;
            String urlThumbnail;
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    doc = Jsoup.connect(url).get();
                    Element element = doc.getElementById("zplayerjs-wrapper");
                    if (element != null) {
                        urlInfo = element.attr("data-xml");
                    }
                    Elements elements = doc.getElementsByTag("meta");
                    for (Element e : elements) {
                        if (e.attr("property").equals("og:image")) {
                            urlThumbnail = e.attr("content");
                            break;
                        }
                    }
                    return urlInfo;
                } catch (IOException e) {
                    e.printStackTrace();
                    mZingCallBack.onFail(e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    ZingMp3Reponse model = new ZingMp3Reponse();
                    ZingMp3Reponse.Data data = new ZingMp3Reponse.Data();
                    String[] splits = url.split("/");
                    data.setName(splits[splits.length - 2].replace("-", " "));    //todo change index 4 -> length -2
                    data.setArtist("Tải xuống");
                    data.setThumb(urlThumbnail);
                    List<ZingMp3Reponse.Data> list = new ArrayList<ZingMp3Reponse.Data>();
                    list.add(data);
                    model.setData(list);
                    mZingCallBack.onSucess(model);
                } catch (Exception e) {
                    mZingCallBack.onFail(e);
                }

            }
        }.execute();
    }

    public void getLinkDownload(String url) {
        Call<String> getZing = getmApi().download(url);
        getZing.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    mZingCallBack.onGetLinkDownloadSucess(response.body());
                } else {
                    mZingCallBack.onFail(new Exception("body null"));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mZingCallBack.onFail(new Exception(t.getMessage()));
            }
        });

    }

    public void getLinkToolDownload(final String url, final String q) {
        Call<ResponseBody> getZing = getmApi().downloadToolHighQ(q, url);
        getZing.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    if (response.body().contentType().type().equals("audio")) {
                        mZingCallBack.onGetLinkDownloadSucess(String.format(MainToolActivity.url_fomat,  url));
                        return;
                    }
                    try {
                        mZingCallBack.onFail(new Exception("Not audio response"+response.body().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    try {
//                        String result = response.body().string();
//                        if (q.equals("lossless")) {
//                            if (result.contains("zmp3-mp3-lossless.zadn.vn")) {
//                                mZingCallBack.onGetLinkDownloadSucess(String.format(MainToolActivity.url_fomat, "lossless", url));
//                                return;
//                            }
//                            mZingCallBack.onFail(new Exception(result));
//                        } else if (q.equals("320")) {
//                            if (result.contains("Acc vip hết hạn rồi")) {
//                                mZingCallBack.onFail(new Exception(result));
//                                return;
//                            }
//                            mZingCallBack.onGetLinkDownloadSucess(String.format(MainToolActivity.url_fomat, "320", url));
//                        }
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                } else {
                    mZingCallBack.onFail(new Exception("Không thể kết nối"));
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mZingCallBack.onFail(new Exception(t.getMessage()));
            }
        });

    }

}
