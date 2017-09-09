package com.yibao.biggirl.util;

import android.content.ContentUris;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author：Sid
 * Des：${歌曲的时间格式处理}
 * Time:2017/8/12 18:39
 */
public class StringUtil {
    private static final int HOUR = 60 * 60 * 1000;
    private static final int MIN  = 60 * 1000;
    private static final int SEC  = 1000;

    //解析时间
    public static String parseDuration(int duration) {
        int hour = duration / HOUR;
        int min  = duration % HOUR / MIN;
        int sec  = duration % MIN / SEC;
        if (hour == 0) {

            return String.format("%02d:%02d", min, sec);
        } else {
            return String.format("%02d:%02d:%02d", hour, min, sec);
        }
    }

    public static String getSongName(String songName) {
        if (songName.contains("_")) {
            songName = songName.substring(songName.indexOf("_") + 1, songName.length());
        }
        return songName;
    }

    public static Uri getAlbulm(long albulmId) {

        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"),
                                          albulmId);

    }

    //yyyy-MM-dd HH:mm:ss
    public static String getCurrentTime() {
        long             time   = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        Date             date   = new Date(time);
        return format.format(date);

    }

}
