package com.storm.ffmpeg.command.ffmpeg;

import android.util.Log;

public class FFmpegCommands {

    /**
     * 裁剪音频
     */
    public static String[] cutIntoMusic(String musicUrl, long second, String outUrl) {
        Log.e("SLog", musicUrl + "---" + second + "---" + outUrl);
        String[] commands = new String[10];
        commands[0] = "ffmpeg";
        commands[1] = "-i";
        commands[2] = musicUrl;
        commands[3] = "-ss";
        commands[4] = "00:00:10";
        commands[5] = "-t";
        commands[6] = String.valueOf(second);
        commands[7] = "-acodec";
        commands[8] = "copy";
        commands[9] = outUrl;
        return commands;
    }
}
