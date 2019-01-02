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
LOCAL_SRC_FILES     := \
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