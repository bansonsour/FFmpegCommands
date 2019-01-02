package com.storm.ffmpeg.command.ffmpeg;

import java.util.ArrayList;
import java.util.List;

/**
 * 音视频命令集合
 */
public class FFmpegCommands {

    /**
     * 修改音频音量
     */
    public static String[] getVersion(String dest) {
        List<String> _commands = new ArrayList<>();
        _commands.add("ffmpeg");
        _commands.add("-version");
//        _commands.add(">");
//        _commands.add(dest);
        String[] commands = new String[_commands.size()];
        for (int i = 0; i < _commands.size(); i++) {
            commands[i] = _commands.get(i);
        }

        return commands;
    }

}
