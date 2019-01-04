package com.storm.ffmpeg.command;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.storm.ffmpeg.command.ffmpeg.AudioCommands;
import com.storm.ffmpeg.command.ffmpeg.FFmpegCommands;
import com.storm.ffmpeg.command.ffmpeg.FFmpegRun;
import com.storm.ffmpeg.command.ui.FFmpegAudioActivity;
import com.storm.ffmpeg.command.ui.FFmpegVideoActivity;
import com.storm.ffmpeg.command.ui.LameAudioActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String dir = "/storage/emulated/0/affmpeg/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MOUNT_FORMAT_FILESYSTEMS}, 100);
        findViewById(R.id.FFmpegAudio).setOnClickListener(this);
        findViewById(R.id.FFmpegVideo).setOnClickListener(this);
        findViewById(R.id.LameAudio).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.FFmpegAudio:
                startActivity(new Intent(this, FFmpegAudioActivity.class));
                break;
            case R.id.FFmpegVideo:
                startActivity(new Intent(this, FFmpegVideoActivity.class));
                break;
            case R.id.LameAudio:
                startActivity(new Intent(this, LameAudioActivity.class));
                break;
        }
    }
}
