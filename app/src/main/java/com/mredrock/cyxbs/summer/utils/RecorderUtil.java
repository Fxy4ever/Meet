package com.mredrock.cyxbs.summer.utils;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecorderUtil {
    private MediaRecorder mRecorder;
    private File soundFile;
    private String filename;

    public RecorderUtil() {
        mRecorder = new MediaRecorder();
    }

    public void recordStart(){
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toasts.show("SD卡不存在，请插入SD卡！");
            return;
        }
        try {
            soundFile = new File(Environment.getExternalStorageDirectory()
                    .getCanonicalFile() + "/sound"+System.currentTimeMillis()/1000+".mp3");
            filename = "sound"+System.currentTimeMillis()/1000+".mp3";
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile(soundFile.getAbsolutePath());
            mRecorder.prepare();
            mRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recordStop(VoiceCallback callback){
        if (soundFile != null && soundFile.exists()&&mRecorder!=null) {
            // 停止录音
            mRecorder.stop();
            // 释放资源
            mRecorder.release();
            mRecorder = null;
            callback.Succeed(filename,soundFile.getPath());
        }
    }

    public interface VoiceCallback{
        void Succeed(String fileName,String filePath);
    }
}
