package com.storm.ffmpeg.command;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.storm.ffmpeg.command.ffmpeg.AudioCommands;
import com.storm.ffmpeg.command.ffmpeg.FFmpegCommands;
import com.storm.ffmpeg.command.ffmpeg.FFmpegRun;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String dir = "/storage/emulated/0/affmpeg/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MOUNT_FORMAT_FILESYSTEMS}, 100);
        findViewById(R.id.btnCutAudio).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String src = dir + "bgm.wav";
        String dest = dir + "bgm_lame_mp3.mp3";
        String[] commands = AudioCommands.wavToMp3(src,dest);
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
