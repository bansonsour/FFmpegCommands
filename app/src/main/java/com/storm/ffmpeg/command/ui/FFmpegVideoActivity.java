package com.storm.ffmpeg.command.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.storm.ffmpeg.command.R;
import com.storm.ffmpeg.command.ffmpeg.AudioCommands;
import com.storm.ffmpeg.command.ffmpeg.FFmpegRun;

public class FFmpegVideoActivity extends AppCompatActivity {

    private String dir = "/storage/emulated/0/affmpeg/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ffmpeg_video);
        findViewById(R.id.btnCutAudio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cutAudio();
            }
        });
    }


    private void cutAudio() {
        String src = dir + "bgm.mp3";
        String dest = dir + "bgm_short.mp3";
        String[] commands = AudioCommands.cutAudio(src, dest, "00:00:00", 10);
        FFmpegRun.execute(commands, new FFmpegRun.FFmpegRunListener() {
            @Override
            public void onStart() {
                Log.i("test", "onStart");
            }

            @Override
            public void onEnd(int result) {
                Log.i("test", "onEnd");
            }
        });
    }
}
