package com.storm.ffmpeg.command.ffmpeg;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 音频命令集合
 */
public class AudioCommands {

    /**
     * 裁剪音频
     */
    public static String[] cutAudio(String src, String dest, String start, long second) {
        Log.e("dzh", src + "---" + second + "---" + dest);

        List<String> _commands = new ArrayList<>();
        _commands.add("ffmpeg");
        _commands.add("-i");
        _commands.add(src);
        _commands.add("-ss");
        _commands.add(start);
        _commands.add("-t");
        _commands.add(String.valueOf(second));
        _commands.add("-acodec");
        _commands.add("copy");
        _commands.add(dest);

        String[] commands = new String[_commands.size()];
        for (int i = 0; i < _commands.size(); i++) {
            commands[i] = _commands.get(i);
        }
        return commands;
    }


    /**
     * 提取单独的音频
     */
    public static String[] extractAudio(String src, String dest) {

        List<String> _commands = new ArrayList<>();
        _commands.add("ffmpeg");
        _commands.add("-i");
        _commands.add(src);
        _commands.add("-acodec");
        _commands.add("copy");
        _commands.add("-vn");
        _commands.add("-y");
        _commands.add(dest);

        String[] commands = new String[_commands.size()];
        for (int i = 0; i < _commands.size(); i++) {
            commands[i] = _commands.get(i);
        }
        return commands;
    }


    /**
     * 音频混合
     */
    public static String[] mixAudio(String src1, String src2, String dest) {

        List<String> _commands = new ArrayList<>();
        _commands.add("ffmpeg");
        _commands.add("-i");
        _commands.add(src1);
        _commands.add("-i");
        _commands.add(src2);
        _commands.add("-filter_complex");
        _commands.add("amix=inputs=2:duration=first:dropout_transition=2");
        _commands.add("-strict");
        _commands.add("-2");
        _commands.add(dest);

        String[] commands = new String[_commands.size()];
        for (int i = 0; i < _commands.size(); i++) {
            commands[i] = _commands.get(i);
        }
        return commands;
    }

    /**
     * mp3转wav
     * @return 转换命令
     */
    public static String[] mp3ToWav(String src, String dest) {

        ArrayList<String> _commands = new ArrayList<>();
        _commands.add("ffmpeg");
        _commands.add("-i");
        _commands.add(src);
        _commands.add("-ar");
        _commands.add("8000");
        _commands.add("-ab");
        _commands.add("12.2k");
        _commands.add("-ac");
        _commands.add("1");
        _commands.add("-f");
        _commands.add("wav");
        _commands.add(dest);

        String[] commands = new String[_commands.size()];
        for (int i = 0; i < _commands.size(); i++) {
            commands[i] = _commands.get(i);
        }
        return commands;
    }

    /**
     * 拼接
     */
    public static String[] concatAudio(List<String> srcList, String dest) {

        if (srcList == null || srcList.size() < 2) return null;
        ArrayList<String> _commands = new ArrayList<>();
        _commands.add("ffmpeg");

        for (int i = 0; i < srcList.size(); i++) {
            _commands.add("-i");
            _commands.add(srcList.get(i));
        }
        _commands.add("-filter_complex");
        _commands.add("[0:0] [1:0]  concat=n=" + srcList.size() + ":v=0:a=1[out]");
        _commands.add("-map");
        _commands.add("[out]");
        _commands.add(dest);

        String[] commands = new String[_commands.size()];
        for (int i = 0; i < _commands.size(); i++) {
            commands[i] = _commands.get(i);
        }
        return commands;
    }


    /**
     * 修改音频音量
     */
    public static String[] changeAudioVol(String src, int vol, String dest) {
        List<String> _commands = new ArrayList<>();
        _commands.add("ffmpeg");
        _commands.add("-i");
        _commands.add(src);
        _commands.add("-vol");
        _commands.add(String.valueOf(vol));
        _commands.add(dest);

        String[] commands = new String[_commands.size()];
        for (int i = 0; i < _commands.size(); i++) {
            commands[i] = _commands.get(i);
        }

        return commands;
    }


    /**
     * 修改音频音量
     */
    public static String[] wavToMp3(String src, String dest) {

//        ffmepg -i input.wav -f mp3 -acodec libmp3lame -y output.mp3
//        ffmepg -i input.wav -f mp2 output.mp3
//        ffmpeg -i input.wav -codec:a libmp3lame -qscale:a 2 output.mp3

//        ffmpeg -i test.wav -f mp3 -acodec libmp3lame -y wav2mp3.mp3

        List<String> _commands = new ArrayList<>();
        _commands.add("ffmpeg");
        _commands.add("-i");
        _commands.add(src);
        _commands.add("-f");
        _commands.add("-acodec");
        _commands.add("libmp3lame");
        _commands.add("-y");
        _commands.add(dest);

        String[] commands = new String[_commands.size()];
        for (int i = 0; i < _commands.size(); i++) {
            commands[i] = _commands.get(i);
        }

        return commands;
    }


}
