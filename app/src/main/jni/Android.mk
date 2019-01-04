LOCAL_PATH := $(call my-dir)

#编译ffmpeg
include $(CLEAR_VARS)
LOCAL_MODULE := ffmpeg
LOCAL_SRC_FILES := libffmpeg.so
include $(PREBUILT_SHARED_LIBRARY)

#编译命令行
include $(CLEAR_VARS)
LOCAL_MODULE := ffcommand
LOCAL_SRC_FILES := com_storm_ffmpeg_command_ffmpeg_FFmpegRun.c ffmpeg.c ffmpeg_opt.c cmdutils.c ffmpeg_filter.c
#LOCAL_C_INCLUDES := /Users/tangyx/Documents/ffmpeg-3.3.2
LOCAL_C_INCLUDES := /Users/a111/Desktop/ffmpeg/ffmpeg-3.3.9
LOCAL_LDLIBS := -llog -lz -ldl
LOCAL_SHARED_LIBRARIES := ffmpeg

include $(BUILD_SHARED_LIBRARY)

#编译lame
include $(CLEAR_VARS)
LAME_LIBMP3_DIR := libmp3lame
LOCAL_MODULE    := mp3lame
LOCAL_SRC_FILES     := com_storm_ffmpeg_command_lame_FLameUtils.c\
        $(LAME_LIBMP3_DIR)/bitstream.c \
        $(LAME_LIBMP3_DIR)/encoder.c \
        $(LAME_LIBMP3_DIR)/fft.c \
        $(LAME_LIBMP3_DIR)/gain_analysis.c \
        $(LAME_LIBMP3_DIR)/id3tag.c \
        $(LAME_LIBMP3_DIR)/lame.c \
        $(LAME_LIBMP3_DIR)/mpglib_interface.c \
        $(LAME_LIBMP3_DIR)/newmdct.c \
        $(LAME_LIBMP3_DIR)/presets.c \
        $(LAME_LIBMP3_DIR)/psymodel.c \
        $(LAME_LIBMP3_DIR)/quantize.c \
        $(LAME_LIBMP3_DIR)/quantize_pvt.c \
        $(LAME_LIBMP3_DIR)/reservoir.c \
        $(LAME_LIBMP3_DIR)/set_get.c \
        $(LAME_LIBMP3_DIR)/tables.c \
        $(LAME_LIBMP3_DIR)/takehiro.c \
        $(LAME_LIBMP3_DIR)/util.c \
        $(LAME_LIBMP3_DIR)/vbrquantize.c \
        $(LAME_LIBMP3_DIR)/VbrTag.c \
        $(LAME_LIBMP3_DIR)/version.c \

LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)



#include $(CLEAR_VARS)
#
#    LOCAL_MODULE        := androidlame
#    LOCAL_SRC_FILES     := AndroidLame.c \
#    ./libmp3lame/bitstream.c \
#    ./libmp3lame/encoder.c \
#    ./libmp3lame/fft.c \
#    ./libmp3lame/gain_analysis.c \
#    ./libmp3lame/id3tag.c \
#    ./libmp3lame/lame.c \
#    ./libmp3lame/mpglib_interface.c \
#    ./libmp3lame/newmdct.c \
#    ./libmp3lame/presets.c \
#    ./libmp3lame/psymodel.c \
#    ./libmp3lame/quantize.c \
#    ./libmp3lame/quantize_pvt.c \
#    ./libmp3lame/reservoir.c \
#    ./libmp3lame/set_get.c \
#    ./libmp3lame/tables.c \
#    ./libmp3lame/takehiro.c \
#    ./libmp3lame/util.c \
#    ./libmp3lame/vbrquantize.c \
#    ./libmp3lame/VbrTag.c \
#    ./libmp3lame/version.c

#    LOCAL_LDLIBS := -llog
#    LOCAL_CFLAGS = -DSTDC_HEADERS

#    include $(BUILD_SHARED_LIBRARY)