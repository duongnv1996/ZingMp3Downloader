package com.duongkk.zingmp3.presenter;

import com.duongkk.zingmp3.model.IZingCallback;
import com.duongkk.zingmp3.model.ZingManager;
import com.duongkk.zingmp3.model.ZingModel;
import com.duongkk.zingmp3.model.ZingMp3Reponse;
import com.duongkk.zingmp3.view.activities.IMainViewCallback;

/**
 * Created by DuongKK on 9/11/2017.
 */

public class MainPresenter implements IZingCallback {
    private ZingManager mZingManager;
    private IMainViewCallback mMainViewCallBack;

    public MainPresenter(IMainViewCallback iMainViewCallback) {
        mZingManager = new ZingManager(this);
        this.mMainViewCallBack = iMainViewCallback;
    }

    public void getInfor(String url) {
        mZingManager.getInfor(url);
    }

    public void getLink(String url) {
        mZingManager.getLinkDownload(url);
    }

    public void getLinkTool(String url, String quality) {
        mZingManager.getLinkToolDownload(url, quality);
    }

    @Override
    public void onSucess(ZingModel zingModel) {
        if (zingModel.getResponse().getMsgCode() != 1) {
            mMainViewCallBack.onFail(new Exception(zingModel.getResponse().getMsg()));
            return;
        }
        mMainViewCallBack.onGetInforSuccess(zingModel);
    }

    @Override
    public void onSucess(ZingMp3Reponse zingModel) {
        if (zingModel != null) {
            ZingModel model = new ZingModel();
            model.setTitle(zingModel.getData().get(0).getName());
            model.setArtist(zingModel.getData().get(0).getArtist());
            model.setThumbnail(zingModel.getData().get(0).getThumb());
            mMainViewCallBack.onGetInforSuccess(model);
        } else {
            mMainViewCallBack.onFail(new Exception("Khong the lay thong tin bai hat"));
        }
    }

    @Override
    public void onFail(Exception e) {
        mMainViewCallBack.onFail(e);
    }

    @Override
    public void onGetLinkDownloadSucess(String url) {
        mMainViewCallBack.onGetLinkSuccess(url);
    }
}
