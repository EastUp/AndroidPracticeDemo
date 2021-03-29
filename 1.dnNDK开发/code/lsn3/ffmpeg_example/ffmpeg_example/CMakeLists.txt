# CMakeList.txt: ffmpeg_example 的 CMake 项目，包括源和定义
# 此处特定于项目的逻辑。
#
cmake_minimum_required (VERSION 3.8)

#能够在 编码的时候 使用ffmpeg的函数
# 设置头文件的查找位置
include_directories("E:/BaiduNetdiskDownload/ffmpeg-20180813-551a029-win64-dev/include")

# 设置库的查找目录 jar
link_directories("E:/BaiduNetdiskDownload/ffmpeg-20180813-551a029-win64-dev/lib")

# 将源添加到此项目的可执行文件。
add_executable (ffmpegexample "ffmpeg_example.cpp" "ffmpeg_example.h")

#设置编译链接的库 ffmpeg
target_link_libraries( ffmpegexample  avutil avcodec avfilter  avdevice avformat postproc swresample )
#target_link_libraries(ffmpegexample avcodec avdevice avfilter avformat avutil postproc swresample swscale)

# TODO: 如有需要，请添加测试并安装目标。
