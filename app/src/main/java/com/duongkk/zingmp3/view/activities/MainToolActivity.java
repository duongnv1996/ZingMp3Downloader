package com.duongkk.zingmp3.view.activities;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.LogUtils;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.duongkk.zingmp3.R;
import com.duongkk.zingmp3.apis.DownloadService;
import com.duongkk.zingmp3.model.ZingModel;
import com.duongkk.zingmp3.presenter.MainPresenter;
import com.duongkk.zingmp3.utils.CommonUtils;
import com.duongkk.zingmp3.view.customviews.ProgressDialogCustom;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainToolActivity extends AppCompatActivity implements IMainViewCallback {
    @BindView(R.id.btn_128)
    FrameLayout btn128;
    @BindView(R.id.btn_320)
    FrameLayout btn320;
    @BindView(R.id.btn_lossless)
    FrameLayout btnLossless;
    @BindView(R.id.tv_song)
    TextView tvSong;
    @BindView(R.id.tv_auth)
    TextView tvAuth;
    @BindView(R.id.ll_root)
    RelativeLayout llRoot;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.tv_128)
    TextView tv128;
    @BindView(R.id.tv_320)
    TextView tv320;
    @BindView(R.id.tv_ll)
    TextView tvLl;
    @BindView(R.id.faq)
    LinearLayout faq;
    @BindView(R.id.ll_rate)
    LinearLayout llRate;
    @BindView(R.id.img_header)
    ImageView imgHeader;
    @BindView(R.id.ll_infor)
    LinearLayout llInfor;

    private MainPresenter mainPresenter;
    private ZingModel model;
    private ProgressDialogCustom progressDialog;
    private DownloadManager downloadManager;
    private String url;
    private String ext = ".mp3";
    private InterstitialAd mInterstitialAd;
    private String linkMp3;
    private boolean is128 = false;
    private MaterialDialog dialog;
    private String quality = "128";
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0));
                DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                Cursor cursor = manager.query(query);
                if (cursor.moveToFirst()) {
                    if (cursor.getCount() > 0) {
                        int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            String file = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                            // So something here on success
                            LogUtils.e(file);
                        } else {
                            int message = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_REASON));
                            // So something here on failed.
                            LogUtils.e(message);

                        }
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Answers.getInstance().logContentView(new ContentViewEvent());
        mainPresenter = new MainPresenter(this);
        progressDialog = new ProgressDialogCustom(this);
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        //todo Add ADS
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-4447279115464296~5831517351");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4447279115464296/1108360637");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // showInterstitial();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                loadInterstitial();
            }
        });
        loadInterstitial();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent);
                return;
            }
        }
        llRoot.setVisibility(View.GONE);
    }


    private void showInterstitial() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
        }
    }

    private void loadInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("1C8067A9CD67109A760B8802C99C0F4D")
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            progressDialog.showDialog();
            mainPresenter.getInfor(sharedText);
            linkMp3 = sharedText;
        } else {
            showError();
        }
    }


    @Override
    public void onGetInforSuccess(ZingModel model2) {
        progressDialog.hideDialog();
        this.model = model2;
        tvAuth.setText(model.getArtist());
        tvSong.setText(model.getTitle());
        llRoot.setVisibility(View.VISIBLE);
        if (model.getThumbnail() != null && !model.getThumbnail().isEmpty()) {
            Picasso.with(this).load(model.getThumbnail()).into(img);
        } else {
//            Picasso.with(this).load(R.drawable.q).into(img);
        }
        fab.setVisibility(View.GONE);
        imgHeader.setVisibility(View.GONE);
        llInfor.setVisibility(View.GONE);
    }

    @Override
    public void onFail(Exception e) {
        progressDialog.hideDialog();
        LogUtils.e(e.getMessage());
        showError();
        Crashlytics.log("Link: " + linkMp3 + "\nerror: " + e.getMessage() + "\nos: " + android.os.Build.MODEL);
        Crashlytics.setInt("current_level", 3);
        Crashlytics.setString("last_UI_action", "logged_in");
    }

    private void showError() {
        if (dialog == null) {
            dialog = new MaterialDialog.Builder(this).title(getString(R.string.error))
                    .content("Không thể lấy thông tin bài hát. Vui lòng thử lại!")
                    .positiveColor(Color.GRAY)
                    .positiveText("Đồng ý")
                    .cancelable(false).build();

        }
        try {
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter i = new IntentFilter();
        i.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        i.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        registerReceiver(receiver,i);
    }

    @Override
    public void onGetLinkSuccess(String url) {
        progressDialog.hideDialog();
        this.url = url;
        checkPermissionToDownload(url, model.getTitle() + ext,quality);
        if (!is128) {
            showInterstitial();
        }
    }

    private void checkPermissionToDownload(String url, String name,String quality) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    10000);
        } else {
            this.quality = quality;
            downloadFile(url, name,quality);
        }
    }

    private void downloadFile(String url, String name,String quality) {
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//        request.setTitle(name.replace(ext, ""));
//        request.setMimeType("audio/MP3");
//        request.setDescription(name);
//        request.setDestinationInExternalPublicDir("/Music", name);
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        downloadManager.enqueue(request);

        Intent intent = new Intent(this,DownloadService.class);
        intent.putExtra("url",url);
        intent.putExtra("q",quality);
        intent.putExtra("name",name);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10000) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                showDialogPermission();
            } else {
                if (model != null && url != null)
                    downloadFile(url, model.getTitle(),quality);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showInterstitial();
    }

    private void showDialogPermission() {
        new MaterialDialog.Builder(this).title(getString(R.string.error))
                .content("Bạn cần cấp quyền truy cập ứng dụng để tiếp tục sử dụng dịch vụ")
                .positiveColor(Color.GRAY)
                .positiveText("Đồng ý")
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ActivityCompat.requestPermissions(MainToolActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                10000);
                    }
                }).show();
    }

    @Override
    protected void onStop() {
        progressDialog.hideDialog();
        super.onStop();
    }

    public final static String url_fomat = "%s";

    @OnClick({R.id.btn_128, R.id.btn_320, R.id.btn_lossless})
    public void onViewClicked(View view) {
        ext = ".mp3";
        is128 = false;
        switch (view.getId()) {
            case R.id.btn_128:
                is128 = true;
                quality ="128";
                onGetLinkSuccess(String.format(url_fomat, linkMp3));
                break;
            case R.id.btn_320:
//                onGetLinkSuccess(String.format(url_fomat,"320",linkMp3));
                progressDialog.showDialog();
                quality ="320";
                mainPresenter.getLinkTool(linkMp3, "320");
                break;
            case R.id.btn_lossless:
                progressDialog.showDialog();
                quality ="lossless";
                mainPresenter.getLinkTool(linkMp3, "lossless");
                ext = ".flac";
                break;
        }

    }

    @OnClick({R.id.fab})
    public void onViewFabClicked(View view) {
        switch (view.getId()) {
            case R.id.fab:
                try {
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.zing.mp3");
                    startActivity(intent);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    //
    @OnClick({R.id.faq, R.id.ll_rate})
    public void onViewLayoutClicked(View view) {
        switch (view.getId()) {
            case R.id.faq:
                CommonUtils.shareEmail("duongkk.dev@gmail.com", this);
                break;
            case R.id.ll_rate:
                CommonUtils.launchMarket(this, getPackageName());
                break;
        }
    }


}
