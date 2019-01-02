package com.storm.ffmpeg.command.ffmpeg;

import android.os.AsyncTask;

public class FFmpegRun {


    static {
        System.loadLibrary("ffmpeg");
        System.loadLibrary("ffcommand");
    }


    public native static int run(String[] commands);


    public static void execute(String[] commands, final FFmpegRunListener fFmpegRunListener) {
        new AsyncTask<String[], Integer, Integer>() {
            @Override
            protected void onPreExecute() {
                if (fFmpegRunListener != null) {
                    fFmpegRunListener.onStart();
                }
            }

            @Override
            protected Integer doInBackground(String[]... params) {
                return run(params[0]);
            }

            @Override
            protected void onPostExecute(Integer integer) {
                if (fFmpegRunListener != null) {
                    fFmpegRunListener.onEnd(integer);
                }
            }
        }.execute(commands);
    }

    public interface FFmpegRunListener{
        void onStart();
        void onEnd(int result);
    }
}
