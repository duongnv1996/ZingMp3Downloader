package com.duongkk.zingmp3lossless.view.activities;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.LogUtils;
import com.duongkk.zingmp3lossless.R;
import com.duongkk.zingmp3lossless.model.ZingModel;
import com.duongkk.zingmp3lossless.presenter.MainPresenter;
import com.duongkk.zingmp3lossless.view.customviews.ProgressDialogCustom;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IMainViewCallback {
    @BindView(R.id.btn_128)
    Button btn128;
    @BindView(R.id.btn_320)
    Button btn320;
    @BindView(R.id.btn_lossless)
    Button btnLossless;
    @BindView(R.id.tv_song)
    TextView tvSong;
    @BindView(R.id.tv_auth)
    TextView tvAuth;
    @BindView(R.id.ll_link)
    LinearLayout llLink;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.fab_open_download)
    FloatingActionButton fabOpenDownload;
    private MainPresenter mainPresenter;
    private ZingModel model;
    private ProgressDialogCustom progressDialog;
    private DownloadManager downloadManager;
    private long downloadReference;
    private String url;
    private String ext = ".mp3";
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {

                //check if the broadcast message is for our enqueued download
                long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                if (referenceId == downloadReference) {

                    Toast toast = Toast.makeText(context,
                            " Download Complete", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                }


                boolean successful = intent.getBooleanExtra("success", false);
                if (successful) {
                    String name = intent.getStringExtra("name");
                    String path = intent.getStringExtra("path");
                    if (path != null) {
                        LogUtils.e(name + "" + path);
//                        if (dialog.isShowing()) dialog.cancel();
//                        CommonUtils.createNotificationWithMsg(getContext(), getString(R.string.download_success) + " : " + name, path);
//                        Crouton.makeText(getActivity(), getString(R.string.download_success), Style.CONFIRM, llRoot).show();
                    } else {
//                        Crouton.makeText(getActivity(), getString(R.string.download_success), Style.CONFIRM, llRoot).show();
                    }
                }

            } else {
//                if (dialog.isShowing()) dialog.cancel();

//                Crouton.makeText(getActivity(), getString(R.string.download_failt), Style.ALERT, llRoot).show();

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainPresenter = new MainPresenter(this);
        progressDialog = new ProgressDialogCustom(this);
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent);
            }
        }
    }


    private void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            progressDialog.showDialog();
            mainPresenter.getInfor(sharedText);
        }
    }


    @Override
    public void onGetInforSuccess(ZingModel model) {
        progressDialog.hideDialog();
        this.model = model;


        llRoot.setVisibility(View.VISIBLE);
        llLink.setVisibility(View.VISIBLE);
        tvAuth.setText(model.getArtist());
        tvSong.setText(model.getTitle());
    }

    @Override
    public void onFail(Exception e) {
        progressDialog.hideDialog();
        LogUtils.e(e.getMessage());
        Toast.makeText(this, "Could not get information!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetLinkSuccess(String url) {
        progressDialog.hideDialog();
        checkPermissionToDownload(url, model.getTitle() + ext);
    }

    private void checkPermissionToDownload(String url, String name) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    10000);
        } else {
            downloadFile(url, name);
        }
    }

    private void downloadFile(String url, String name) {
        this.url = url;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(name.replace(ext,""));
        request.setDescription(name);
        request.setDestinationInExternalPublicDir("/Download", name);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadReference = downloadManager.enqueue(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10000) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED || grantResults[1] == PackageManager.PERMISSION_DENIED) {
                showDialogPermission();
            } else {
                if (model != null && url != null)
                    downloadFile(url, model.getTitle());

            }
        }
    }

    private void showDialogPermission() {
        MaterialDialog materialDialog = new MaterialDialog.Builder(this).title(getString(R.string.error))
                .content("Bạn cần cấp quyền truy cập ứng dụng để tiếp tục sử dụng dịch vụ")
                .positiveColor(Color.GRAY)
                .positiveText("Đồng ý")
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                10000);
                    }
                }).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter i = new IntentFilter("downloading");
        registerReceiver(receiver, i);
    }

    @Override
    protected void onStop() {
        progressDialog.hideDialog();
        super.onStop();
        unregisterReceiver(receiver);
    }

    @OnClick({R.id.btn_128, R.id.btn_320, R.id.btn_lossless})
    public void onViewClicked(View view) {
        if (model == null) return;
        switch (view.getId()) {
            case R.id.btn_128:
                mainPresenter.getLink(model.getLink_download().getM128());
                break;
            case R.id.btn_320:
                mainPresenter.getLink(model.getLink_download().getM320());
                break;
            case R.id.btn_lossless:
                mainPresenter.getLink(model.getLink_download().getLossless());
                ext = ".flac";
                break;
        }
        progressDialog.showDialog();
    }

    @OnClick({R.id.fab, R.id.fab_open_download})
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
            case R.id.fab_open_download:
                startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
                break;
        }
    }
}
