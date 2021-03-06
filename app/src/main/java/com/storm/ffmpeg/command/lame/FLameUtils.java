package com.storm.ffmpeg.command.lame;

public class FLameUtils {
    static {
        System.loadLibrary("mp3lame");
    }

    /**
     * Initialize LAME.
     *
     * @param inSampleRate  input sample rate in Hz.
     * @param outChannel    number of channels in input stream.
     * @param outSampleRate output sample rate in Hz.
     * @param outBitrate    brate compression ratio in KHz.
     * @param quality       quality=0..9. 0=best (very slow). 9=worst.<br />
     *                      recommended:<br />
     *                      2 near-best quality, not too slow<br />
     *                      5 good quality, fast<br />
     *                      7 ok quality, really fast
     */
    public native static void init(int inSampleRate, int outChannel, int outSampleRate, int outBitrate, int quality);

    /**
     * Close LAME.
     */
    public native static void close();

    /**
     * Encode buffer to mp3.
     *
     * @param buffer_l PCM data for left channel.
     * @param buffer_r PCM data for right channel.
     * @param samples  number of samples per channel.
     * @param mp3buf   result encoded MP3 stream. You must specified
     *                 "7200 + (1.25 * buffer_l.length)" length array.
     * @return number of bytes output in mp3buf. Can be 0.<br />
     * -1: mp3buf was too small<br />
     * -2: malloc() problem<br />
     * -3: lame_init_params() not called<br />
     * -4: psycho acoustic problems
     */
    public native static int encode(short[] buffer_l, short[] buffer_r, int samples, byte[] mp3buf);

    /**
     * Flush LAME buffer.
     *
     * @param mp3buf result encoded MP3 stream. You must specified at least 7200
     *               bytes.
     * @return number of bytes output to mp3buf. Can be 0.
     */
    public native static int flush(byte[] mp3buf);

    public native static String getLameVersion();


    /**
     * pcm file convert to mp3 file
     *
     * @param sourcePath pcm file path
     * @param mp3Path    mp3 file path
     */
    public native static void encodeFile(String sourcePath, String mp3Path);

    /**
     * @param isBigEndian true: pcm is big Endian ; false: pcm is little Endian. default is false
     */
    public native static void setRawBigEndian(boolean isBigEndian);


    /**
     * wav file conver to wav file
     * @param sourcePath
     * @param mp3Path
     * @param inSamplerate
     * @param inChannel
     * @param outBitrate
     */
    public native static void wavTomp3(String sourcePath, String mp3Path,int inSamplerate,int inChannel,int outBitrate);
    public native static void wav2mp3(String sourcePath, String mp3Path);
    public native static void wavmp3(String sourcePath, String mp3Path,int inChannel,int sample_rate);


}
