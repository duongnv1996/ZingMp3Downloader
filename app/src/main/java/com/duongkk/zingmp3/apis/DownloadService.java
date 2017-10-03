package com.duongkk.zingmp3.apis;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.duongkk.zingmp3.R;
import com.duongkk.zingmp3.utils.CommonUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by DuongKK on 9/25/2017.
 */

public class DownloadService extends IntentService {
    public DownloadService() {
        super("Download Service");
    }

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private int totalFileSize;
    private String name, quality;

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getStringExtra("url");
        name = intent.getStringExtra("name");
        quality = intent.getStringExtra("q");
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Đang tải bài hát vào thư mục /Music")
                .setContentText(name)
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());
        initDownload(url, quality);

    }

    private void initDownload(String url, String quality) {
        LogUtils.e(url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://htstar.design/")
                .build();
        ZingAPI retrofitInterface = retrofit.create(ZingAPI.class);
        final Call<ResponseBody> request = retrofitInterface.getDownload(quality, url);
        request.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    downloadFile(response.body());
                } catch (IOException e) {
                    e.printStackTrace();
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LogUtils.e(t.getMessage());
            }
        });
    }

    private void downloadFile(ResponseBody body) throws IOException {
        if (body == null) {
            Toast.makeText(getApplicationContext(), "Không thể download bài hát này!", Toast.LENGTH_SHORT).show();
            return;
        }
        int count;
        byte data[] = new byte[1024 * 4];
        long fileSize = body.contentLength();
        InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);
        File outputFile = new File(CommonUtils.getFolder(getApplicationContext()),  name );
        OutputStream output = new FileOutputStream(outputFile);
        long total = 0;
        long startTime = System.currentTimeMillis();
        int timeCount = 1;
        while ((count = bis.read(data)) != -1) {
            total += count;
            totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
            double current = Math.round(total / (Math.pow(1024, 2)));
            int progress = (int) ((total * 100) / fileSize);
            long currentTime = System.currentTimeMillis() - startTime;
            Download download = new Download();
            download.setTotalFileSize(totalFileSize);
            if (currentTime > 1000 * timeCount) {
                download.setCurrentFileSize((int) current);
                download.setProgress(progress);
                sendNotification(download);
                timeCount++;
            }
            output.write(data, 0, count);
        }
        onDownloadComplete();
        output.flush();
        output.close();
        bis.close();

    }

    private void sendNotification(Download download) {
        ///   sendIntent(download);
        notificationBuilder.setProgress(100, download.getProgress(), false);
        notificationBuilder.setContentText("Downloading file " + download.getCurrentFileSize() + "/" + totalFileSize + " MB");
        notificationManager.notify(0, notificationBuilder.build());
    }

//    private void sendIntent(Download download){
//
//        Intent intent = new Intent(MainActivity.MESSAGE_PROGRESS);
//        intent.putExtra("download",download);
//        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
//    }

    private void onDownloadComplete() {

        Download download = new Download();
        download.setProgress(100);
//        sendIntent(download);
        notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentTitle("Bài hát đã được lưu tại /Music");
        notificationBuilder.setContentText(name);
        notificationManager.notify(0, notificationBuilder.build());

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }

}