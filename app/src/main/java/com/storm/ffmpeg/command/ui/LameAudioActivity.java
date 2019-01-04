package com.storm.ffmpeg.command.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.storm.ffmpeg.command.R;
import com.storm.ffmpeg.command.lame.FLameUtils;
import com.zlm.hp.audio.AudioFileReader;
import com.zlm.hp.audio.TrackInfo;
import com.zlm.hp.audio.utils.AudioUtil;

import java.io.File;

public class LameAudioActivity extends AppCompatActivity {


    public static int NUM_CHANNELS = 1;
    public static int SAMPLE_RATE = 44100;
    public static int BITRATE = 128;//KHz
    public static int QUALITY = 7;

    private String dir = "/storage/emulated/0/affmpeg/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lame_audio);
        findViewById(R.id.btnCutAudio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wavToMp3();
            }
        });
    }


    private void wavToMp3() {
//        String src = dir + "0_p_record.wav";
        String src = "/storage/emulated/0/aaaa/sh.wav";
        String dest = dir + "sh_test.mp3";

        File audioFile = new File(src);
        if (!audioFile.exists()) {
            Log.i("test", "元文件不存在");
            return;
        }

        AudioFileReader audioFileReader = AudioUtil.getAudioFileReaderByFilePath(audioFile.getAbsolutePath());

        if (audioFileReader == null) {
            Log.i("test", "AudioFileReader 为空");
            return;
        }

        TrackInfo trackInfo = audioFileReader.read(audioFile);

        if (trackInfo == null) {
            Log.i("test", "TrackInfo 为空");
            return;
        }

        NUM_CHANNELS = trackInfo.getChannels();
        SAMPLE_RATE=trackInfo.getSampleRate();
        BITRATE=trackInfo.getBitrate();


        Log.i("test", "开始转换");
        FLameUtils.init(SAMPLE_RATE, NUM_CHANNELS, SAMPLE_RATE, BITRATE, QUALITY);
//        FLameUtils.wavTomp3(src, dest,SAMPLE_RATE,NUM_CHANNELS,SAMPLE_RATE);
//        FLameUtils.wav2mp3(src, dest);
        FLameUtils.wavmp3(src, dest,NUM_CHANNELS,SAMPLE_RATE);
//        FLameUtils.wavTomp3(src, dest,8000,2,8000);
        FLameUtils.close();
        Log.i("test", "转化完成的MP3地址=》" + dest);

    }
}
