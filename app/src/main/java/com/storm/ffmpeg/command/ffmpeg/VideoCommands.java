package com.storm.ffmpeg.command.ffmpeg;

import java.util.ArrayList;
import java.util.List;

/**
 * 视屏命令集合
 */
public class VideoCommands {

    /**
     * 提取单独的音频
     */
    public static String[] extractAudio(String src, String dest) {

        List<String> _commands = new ArrayList<>();
        _commands.add("ffmpeg");
        _commands.add("-i");
        _commands.add(src);
        _commands.add("-vcodec");
        _commands.add("copy");
        _commands.add("-an");
        _commands.add("-y");
        _commands.add(dest);

        String[] commands = new String[_commands.size()];
        for (int i = 0; i < _commands.size(); i++) {
            commands[i] = _commands.get(i);
        }
        return commands;
    }


    /**
     * 音频，视频合成
     */
    public static String[] composeVideo(String srcVideo, String srcAudio, String dest, long second) {


        List<String> _commands = new ArrayList<>();
        _commands.add("ffmpeg");
        _commands.add("-i");
        _commands.add(srcVideo);
        _commands.add("-i");
        _commands.add(srcAudio);
        _commands.add("-ss");
        _commands.add("00:00:00");
        _commands.add("-t");
        _commands.add(String.valueOf(second));
        _commands.add("-vcodec");
        _commands.add("copy");
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
     * mp4转ts
     */
    public static String[] mp4ToTs(String src, String dest, boolean flip) {


        ArrayList<String> _commands = new ArrayList<>();
        _commands.add("ffmpeg");
        _commands.add("-i");
        _commands.add(src);
        if (flip) {
            _commands.add("-vf");
            //hflip左右翻转，vflip上下翻转
            _commands.add("hflip");
        }
        _commands.add("-b");
        _commands.add(String.valueOf(2 * 1024 * 1024));
        _commands.add("-s");
        _commands.add("720x1280");
        _commands.add("-acodec");
        _commands.add("copy");
//        _commands.add("-vcodec");
//        _commands.add("copy");
        _commands.add(dest);
        String[] commands = new String[_commands.size()];
        for (int i = 0; i < _commands.size(); i++) {
            commands[i] = _commands.get(i);
        }
        return commands;
    }

    /**
     * ts拼接视频
     */
    public static String[] concatTsVideo(String src, String dest) {//-f concat -i list.txt -c copy concat.mp4


        ArrayList<String> _commands = new ArrayList<>();
        _commands.add("ffmpeg");
        _commands.add("-i");
        _commands.add("concat:" + src);
        _commands.add("-b");
        _commands.add(String.valueOf(2 * 1024 * 1024));
        _commands.add("-s");
        _commands.add("720x1280");
        _commands.add("-acodec");
        _commands.add("copy");
        _commands.add("-vcodec");
        _commands.add("copy");
        _commands.add(dest);

        String[] commands = new String[_commands.size()];
        for (int i = 0; i < _commands.size(); i++) {
            commands[i] = _commands.get(i);
        }
        return commands;
    }

}
