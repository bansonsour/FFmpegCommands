#include <stdbool.h>
#include "com_storm_ffmpeg_command_lame_FLameUtils.h"


lame_t lame;
bool bigEndian;



short swap_bytes(short w) {
    return (0xff00u & (w << 8)) | (0x00ffu & (w >> 8));
}


short toLittleEndian(bool bigEndian, short c) {
    if (bigEndian == true) {
        return swap_bytes(c);
    }
    return c;
}


/*
 * Class:     com_kecson_lame4android_Lame
 * Method:    close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_storm_ffmpeg_command_lame_FLameUtils_init(
        JNIEnv *env, jclass cls, jint inSampleRate, jint outChannel,
        jint outSampleRate, jint outBitrate, jint quality) {
    if (lame != NULL) {
        lame_close(lame);
        lame = NULL;
    }


    LOGD("Init lame version is %s", get_lame_version());
    LOGD("Init parameters: inSampleRate= %d, outChannel=%d,  outSampleRate=%d, outBitrate=%d, quality=%d",
         inSampleRate, outChannel, outSampleRate, outBitrate, quality);

    lame = lame_init();
    lame_set_in_samplerate(lame, inSampleRate);
    lame_set_num_channels(lame, outChannel);
    lame_set_out_samplerate(lame, outSampleRate);
    lame_set_brate(lame, outBitrate);
//    lame_set_mode(lame,MONO);
//    lame_set_VBR(lame, vbr_default);
    lame_set_quality(lame, quality);
    lame_init_params(lame);

}


/*
 * Class:     com_kecson_lame4android_Lame
 * Method:    close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_storm_ffmpeg_command_lame_FLameUtils_close(
        JNIEnv *env, jclass cls) {
    lame_close(lame);
    lame = NULL;
}


/*
 * Class:     com_kecson_lame4android_Lame
 * Method:    encode
 * Signature: ([S[SI[B)I
 */
JNIEXPORT jint JNICALL Java_com_storm_ffmpeg_command_lame_FLameUtils_encode(
        JNIEnv *env, jclass cls, jshortArray buffer_l, jshortArray buffer_r,
        jint samples, jbyteArray mp3buf) {

    jshort *j_buffer_l = (*env)->GetShortArrayElements(env, buffer_l, NULL);
    jshort *j_buffer_r = (*env)->GetShortArrayElements(env, buffer_r, NULL);

    if (bigEndian == true) {
        *j_buffer_l = toLittleEndian(bigEndian, j_buffer_l);
        *j_buffer_r = toLittleEndian(bigEndian, j_buffer_r);
    }


    const jsize mp3buf_size = (*env)->GetArrayLength(env, mp3buf);
    jbyte *j_mp3buf = (*env)->GetByteArrayElements(env, mp3buf, NULL);

    int result = lame_encode_buffer(lame, j_buffer_l, j_buffer_r,
                                    samples, j_mp3buf, mp3buf_size);

    (*env)->ReleaseShortArrayElements(env, buffer_l, j_buffer_l, 0);
    (*env)->ReleaseShortArrayElements(env, buffer_r, j_buffer_r, 0);
    (*env)->ReleaseByteArrayElements(env, mp3buf, j_mp3buf, 0);
    return result;
}


/*
 * Class:     com_kecson_lame4android_Lame
 * Method:    flush
 * Signature: ([B)I
 */
JNIEXPORT jint JNICALL Java_com_storm_ffmpeg_command_lame_FLameUtils_flush(
        JNIEnv *env, jclass cls, jbyteArray mp3buf) {
    const jsize mp3buf_size = (*env)->GetArrayLength(env, mp3buf);
    jbyte *j_mp3buf = (*env)->GetByteArrayElements(env, mp3buf, NULL);

    int result = lame_encode_flush(lame, j_mp3buf, mp3buf_size);

    (*env)->ReleaseByteArrayElements(env, mp3buf, j_mp3buf, 0);

    return result;
}


/*
 * Class:     com_kecson_lame4android_Lame
 * Method:    getLameVersion
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_storm_ffmpeg_command_lame_FLameUtils_getLameVersion(JNIEnv *env, jclass cls) {
    return get_lame_version();
}


#define BUFFER_SIZE 8192
#define be_short(s) ((short) ((unsigned short) (s) << 8) | ((unsigned short) (s) >> 8))

JNIEXPORT void JNICALL Java_com_storm_ffmpeg_command_lame_FLameUtils_setRawBigEndian(JNIEnv *env, jclass cls,
                                                                         jboolean isBigEndian) {
    bigEndian = isBigEndian;
}

int read_samples(FILE *input_file, short *input) {
    int nb_read = fread(input, 1, sizeof(short), input_file) / sizeof(short);

    int i = 0;
    while (i < nb_read) {
        input[i] = be_short(input[i]);
        input[i] = toLittleEndian(bigEndian, input[i]);
        i++;
    }

    return nb_read;
}


/*
 * Class:     com_kecson_lame4android_Lame
 * Method:    encodeFile
 * Signature: (Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT void JNICALL Java_com_storm_ffmpeg_command_lame_FLameUtils_encodeFile(JNIEnv *env, jclass cls,
                                                                    jstring in_source_path,
                                                                    jstring in_target_path) {
    const char *source_path, *target_path;
    source_path = (*env)->GetStringUTFChars(env, in_source_path, NULL);
    target_path = (*env)->GetStringUTFChars(env, in_target_path, NULL);

    FILE *input_file, *output_file;
    input_file = fopen(source_path, "rb");
    output_file = fopen(target_path, "wb");

    short input[BUFFER_SIZE];
    char output[BUFFER_SIZE];
    int nb_read = 0;
    int nb_write = 0;
    int nb_total = 0;

    LOGD("Encoding started");
    while ((nb_read = read_samples(input_file, input))) {
        nb_write = lame_encode_buffer(lame, input, input, nb_read, output, BUFFER_SIZE);
        fwrite(output, nb_write, 1, output_file);
        nb_total += nb_write;
    }
    LOGD("Encoded %d bytes", nb_total);

    nb_write = lame_encode_flush(lame, output, BUFFER_SIZE);
    fwrite(output, nb_write, 1, output_file);
    LOGD("Flushed %d bytes", nb_write);

    //lame_mp3_tags_fid(lame, output_file);
    fclose(input_file);
    fclose(output_file);
}


/**
 * 返回值 char* 这个代表char数组的首地址
 *  Jstring2CStr 把java中的jstring的类型转化成一个c语言中的char 字符串
 */
char* Jstring2CStr(JNIEnv* env, jstring jstr) {
    char* rtn = NULL;
    jclass clsstring = (*env)->FindClass(env, "java/lang/String"); //String
    jstring strencode = (*env)->NewStringUTF(env, "GB2312"); // 得到一个java字符串 "GB2312"
    jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes",
                                        "(Ljava/lang/String;)[B"); //[ String.getBytes("gb2312");
    jbyteArray barr = (jbyteArray)(*env)->CallObjectMethod(env, jstr, mid,
                                                           strencode); // String .getByte("GB2312");
    jsize alen = (*env)->GetArrayLength(env, barr); // byte数组的长度
    jbyte* ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char*) malloc(alen + 1); //"\0"
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    (*env)->ReleaseByteArrayElements(env, barr, ba, 0); //
    return rtn;
}

int flag = 0;
/**参考地址 ：https://www.jianshu.com/p/fb531239cd79
 *
 * wav转换mp3  --(单声道)可以正常使用
 */
JNIEXPORT void JNICALL Java_com_storm_ffmpeg_command_lame_FLameUtils_wavTomp3
        (JNIEnv * env, jobject obj, jstring jwav, jstring jmp3, jint inSamplerate, jint inChannel, jint outBitrate) {

    char* cwav =Jstring2CStr(env,jwav) ;
    char* cmp3=Jstring2CStr(env,jmp3);

    //1.打开 wav,MP3文件
    FILE* fwav = fopen(cwav,"rb");
    fseek(fwav, 4*1024, SEEK_CUR);
    FILE* fmp3 = fopen(cmp3,"wb+");

    int channel = inChannel;//单声道

    short int wav_buffer[8192*channel];
    unsigned char mp3_buffer[8192];

    //1.初始化lame的编码器
    lame_t lame =  lame_init();

    //2. 设置lame mp3编码的采样率
    lame_set_in_samplerate(lame , inSamplerate);
    lame_set_out_samplerate(lame, inSamplerate);
    lame_set_num_channels(lame,channel);
    lame_set_mode(lame, MONO);
    // 3. 设置MP3的编码方式
    lame_set_VBR(lame, vbr_default);
    lame_init_params(lame);
    int read ; int write; //代表读了多少个次 和写了多少次
    int total=0; // 当前读的wav文件的byte数目
    do{
        if(flag==404){
            return;
        }
        read = fread(wav_buffer,sizeof(short int)*channel, 8192,fwav);
        total +=  read* sizeof(short int)*channel;
        if(read!=0){
            write = lame_encode_buffer(lame, wav_buffer, NULL, read, mp3_buffer, 8192);
            //write = lame_encode_buffer_interleaved(lame,wav_buffer,read,mp3_buffer,8192);
        }else{
            write = lame_encode_flush(lame,mp3_buffer,8192);
        }
        //把转化后的mp3数据写到文件里
        fwrite(mp3_buffer,1,write,fmp3);

    }while(read!=0);
    lame_mp3_tags_fid(lame,fmp3);
    lame_close(lame);
    fclose(fwav);
    fclose(fmp3);
}



//此方法有问题，双声道的wav可以正常转，单声道的转换速度很快！
JNIEXPORT void JNICALL Java_com_storm_ffmpeg_command_lame_FLameUtils_wav2mp3(
        JNIEnv *env, jobject obj, jstring jwav, jstring jmp3) {

//    return encode(env, glf, buffer_l, buffer_r, samples, mp3buf);
    LOGI("test","Java_com_storm_ffmpeg_command_lame_FLameUtils_wav2mp3");
    char* cwav =Jstring2CStr(env,jwav) ;
    char* cmp3=Jstring2CStr(env,jmp3);
    LOGI("wav = %s", cwav);
    LOGI("mp3 = %s", cmp3);

    //1.打开 wav,MP3文件
    FILE* fwav = fopen(cwav,"rb");
    FILE* fmp3 = fopen(cmp3,"wb");

    short int wav_buffer[8192*2];
    unsigned char mp3_buffer[8192];

    //1.初始化lame的编码器
    lame_t lame =  lame_init();
    //2. 设置lame mp3编码的采样率
    lame_set_in_samplerate(lame , 44100);
//    lame_set_in_samplerate(lame , 8000);
    lame_set_num_channels(lame,2);
    // 3. 设置MP3的编码方式
    lame_set_VBR(lame, vbr_default);
    lame_init_params(lame);
    LOGI("lame init finish");
    int read ; int write; //代表读了多少个次 和写了多少次
    int total=0; // 当前读的wav文件的byte数目
    do{
        if(flag==404){
            return;
        }
        read = fread(wav_buffer,sizeof(short int)*2, 8192,fwav);
        total +=  read* sizeof(short int)*2;
        LOGI("converting ....%d", total);
        //回调给java的方法先注释掉
//        publishJavaProgress(env,obj,total);
        // 调用java代码 完成进度条的更新
        if(read!=0){
            write = lame_encode_buffer_interleaved(lame,wav_buffer,read,mp3_buffer,8192);
            //把转化后的mp3数据写到文件里
            fwrite(mp3_buffer,sizeof(unsigned char),write,fmp3);
        }
        if(read==0){
            lame_encode_flush(lame,mp3_buffer,8192);
        }
    }while(read!=0);
    LOGI("convert  finish");

    lame_close(lame);
    fclose(fwav);
    fclose(fmp3);
}


//准备写一个动态的(目前单声道和双声道都支持)
JNIEXPORT void JNICALL Java_com_storm_ffmpeg_command_lame_FLameUtils_wavmp3(
        JNIEnv *env, jobject obj, jstring jwav, jstring jmp3,jint inChannel,jint inSamplerate) {
//jint inSamplerate, jint inChannel
//    return encode(env, glf, buffer_l, buffer_r, samples, mp3buf);
    LOGI("Java_com_storm_ffmpeg_command_lame_FLameUtils_wavmp3");

    int channel = inChannel;
    int samplerate=inSamplerate;

    char* cwav = Jstring2CStr(env,jwav) ;
    char* cmp3 = Jstring2CStr(env,jmp3);

    LOGI("channel = %d", channel);
    LOGI("samplerate = %d", samplerate);
    LOGI("wav = %s", cwav);
    LOGI("mp3 = %s", cmp3);

    //1.打开 wav,MP3文件
    FILE* fwav = fopen(cwav,"rb");
    FILE* fmp3 = fopen(cmp3,"wb");

    short int wav_buffer[8192*channel];
    unsigned char mp3_buffer[8192];

    //1.初始化lame的编码器
    lame_t lame =  lame_init();
    //2. 设置lame mp3编码的采样率
//    lame_set_in_samplerate(lame , 44100);
//    lame_set_in_samplerate(lame , 8000);
    lame_set_in_samplerate(lame , samplerate);
    lame_set_num_channels(lame,channel);
    // 3. 设置MP3的编码方式
    lame_set_VBR(lame, vbr_default);
    lame_init_params(lame);
    LOGI("lame init finish");
    int read ; int write; //代表读了多少个次 和写了多少次
    int total=0; // 当前读的wav文件的byte数目
    do{
        if(flag==404){
            return;
        }
        read = fread(wav_buffer,sizeof(short int)*channel, 8192,fwav);
        total +=  read* sizeof(short int)*channel;
        LOGI("converting ....%d", total);
        //回调给java的方法先注释掉
//        publishJavaProgress(env,obj,total);
        // 调用java代码 完成进度条的更新

         if(channel==1){ //单声道
            if(read!=0){
                write = lame_encode_buffer(lame, wav_buffer, NULL, read, mp3_buffer, 8192);
                //write = lame_encode_buffer_interleaved(lame,wav_buffer,read,mp3_buffer,8192);
            }else{
                write = lame_encode_flush(lame,mp3_buffer,8192);
            }
            //把转化后的mp3数据写到文件里
            fwrite(mp3_buffer,1,write,fmp3);
         }else{ //双声道
             if(read!=0){
                  write = lame_encode_buffer_interleaved(lame,wav_buffer,read,mp3_buffer,8192);
                  //把转化后的mp3数据写到文件里
                  fwrite(mp3_buffer,sizeof(unsigned char),write,fmp3);
                }
                if(read==0){
                   lame_encode_flush(lame,mp3_buffer,8192);
                }
         }


    }while(read!=0);
    LOGI("convert  finish");

    lame_close(lame);
    fclose(fwav);
    fclose(fmp3);
}