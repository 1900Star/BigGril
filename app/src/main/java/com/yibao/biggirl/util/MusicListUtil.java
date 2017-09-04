package com.yibao.biggirl.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.yibao.biggirl.model.music.MusicInfo;

import java.util.ArrayList;

/**
 * Author：Sid
 * Des：${ 音乐列表 }
 * Time:2017/9/3 14:38
 */
public class MusicListUtil {


    /**
     * 从本地获取歌曲的信息，保存在List当中
     *
     * @return
     */
    public static ArrayList<MusicInfo> getMusicList(Context context) {
        Cursor cursor = context.getContentResolver()
                               .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                                      null,
                                      null,
                                      null,
                                      MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        int mId       = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
        int mTitle    = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int mArtist   = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int mAlbum    = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
        int mAlbumID  = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
        int mDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int mSize     = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
        int mUrl      = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        //        int mIsMusic  = cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC);
        ArrayList<MusicInfo> musicInfos = new ArrayList<>();
        for (int i = 0, p = cursor.getCount(); i < p; i++) {
            cursor.moveToNext();
            MusicInfo mp3Info  = new MusicInfo();
            long      id       = cursor.getLong(mId); // 音乐id
            String    title    = cursor.getString(mTitle); // 音乐标题
            String    artist   = cursor.getString(mArtist); // 艺术家
            String    album    = cursor.getString(mAlbum); // 专辑
            long      albumId  = cursor.getInt(mAlbumID);
            long      duration = cursor.getLong(mDuration); // 时长
            long      size     = cursor.getLong(mSize); // 文件大小
            String    url      = cursor.getString(mUrl); // 文件路径

            if (size > 21600) {
                //            int       isMusic  = cursor.getInt(mIsMusic); // 是否为音乐
                mp3Info.setId(id);
                mp3Info.setTitle(title);
                mp3Info.setArtist(artist);
                mp3Info.setAlbum(album);
                mp3Info.setAlbumId(albumId);
                mp3Info.setDuration(duration);
                mp3Info.setSize(size);
                mp3Info.setUrl(url);
                musicInfos.add(mp3Info);
            }
        }
        cursor.close();
            LogUtil.d("SongSize ***** " + musicInfos.size());
        return musicInfos;
    }


}
