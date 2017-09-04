package com.yibao.biggirl.mvp.music;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.yibao.biggirl.R;
import com.yibao.biggirl.service.AudioPlayService;

/**
 * Author：Sid
 * Des：${音乐通知栏}
 * Time:2017/9/3 02:12
 */
public class MusicNoification {


    private static RemoteViews remoteView;

    public static void openMusicNotification(Context context,
                                             boolean isPlaying,
                                             String songName,
                                             String artistName)
    {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.smartisan);
        remoteView = new RemoteViews(context.getPackageName(), R.layout.notify);
        remoteView.setTextViewText(R.id.widget_title, songName);
        remoteView.setTextViewText(R.id.widget_artist, artistName);
        //修改自定义View中的图片(两种方法)
        remoteView.setImageViewResource(R.id.widget_album, R.drawable.btn_playing_play);
        remoteView.setImageViewResource(R.id.widget_close, R.drawable.btn_playing_play);
        remoteView.setImageViewResource(R.id.widget_prev, R.drawable.btn_playing_prev);
        remoteView.setImageViewResource(R.id.widget_next, R.drawable.btn_playing_next);
        updateNotificationUi(isPlaying);
        //        remoteView.setImageViewBitmap(R.id.widget_album,
        //                                      BitmapFactory.decodeResource(getResources(),
        //                                                                   R.mipmap.ic_launcher));
        setAciton(context, remoteView);
        builder.setContent(remoteView);
        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        notificationManager.notify(100, notification);


    }

    public static void updateNotificationUi(boolean isPlaying) {
        if (isPlaying) {
            remoteView.setImageViewResource(R.id.widget_play, R.drawable.btn_playing_play);
        } else {
            remoteView.setImageViewResource(R.id.widget_play, R.drawable.btn_playing_pause);

        }
    }

    private static void setAciton(Context context, RemoteViews remoteViews) {
        Intent intent = new Intent(AudioPlayService.ACTION_MUSIC);

        //Root
        intent.putExtra(AudioPlayService.BUTTON_ID, AudioPlayService.ROOT);
        PendingIntent intentRoot = PendingIntent.getBroadcast(context,
                                                              AudioPlayService.ROOT,
                                                              intent,
                                                              PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notice, intentRoot);

        //Pre
        intent.putExtra(AudioPlayService.BUTTON_ID, AudioPlayService.PREV);
        PendingIntent intentPrev = PendingIntent.getBroadcast(context,
                                                              AudioPlayService.PREV,
                                                              intent,
                                                              PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_prev, intentPrev);

        //Play
        intent.putExtra(AudioPlayService.BUTTON_ID, AudioPlayService.PLAY);
        PendingIntent intentPlay = PendingIntent.getBroadcast(context,
                                                              AudioPlayService.PLAY,
                                                              intent,
                                                              PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_play, intentPlay);
        //Next
        intent.putExtra(AudioPlayService.BUTTON_ID, AudioPlayService.NEXT);
        PendingIntent intentNext = PendingIntent.getBroadcast(context,
                                                              AudioPlayService.NEXT,
                                                              intent,
                                                              PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_next, intentNext);
        //Close
        intent.putExtra(AudioPlayService.BUTTON_ID, AudioPlayService.CLOSE);
        PendingIntent intentClose = PendingIntent.getBroadcast(context,
                                                               AudioPlayService.CLOSE,
                                                               intent,
                                                               PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_close, intentClose);


    }


}
