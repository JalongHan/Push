package haoqu.com.push.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import haoqu.com.push.Consts;
import haoqu.com.push.JSONModel.ActionMessageBean;
import haoqu.com.push.R;
import haoqu.com.push.activity.MainActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by apple on 16/10/28.
 */

public class HeartBeatService extends Service{

    private int id = 0;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {


        Timer timer = new Timer(true);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                final okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(Consts.baseUrl + Consts.appIndex + Consts.weid)
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i(TAG, "onFailure: ");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        String result = response.body().string();
                        Log.i("onResponse:", "onRespone: "+result);
                        if (response.body() != null ) {
                            ActionMessageBean actionMessageBean = JSON.parseObject(result,ActionMessageBean.class);

                            //如果有通知内容,就解析发送通知栏通知.
                            NotificationCompat.Builder mBuilder =
                                    new NotificationCompat.Builder(HeartBeatService.this)
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setContentTitle("您有新消息").setNumber(++id)
                                            .setDefaults(Notification.DEFAULT_VIBRATE)
                                            .setAutoCancel(true)
                                            .setContentText(actionMessageBean.getError());

                            Intent resultIntent = new Intent(HeartBeatService.this, MainActivity.class);
                            TaskStackBuilder stackBuilder = TaskStackBuilder.create(HeartBeatService.this);
                            stackBuilder.addParentStack(MainActivity.class);
                            stackBuilder.addNextIntent(resultIntent);
                            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                            mBuilder.setContentIntent(resultPendingIntent);
                            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            mNotificationManager.notify(0,mBuilder.build());





                        }


                    }
                });






            }
        },0,20*1000);



        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
