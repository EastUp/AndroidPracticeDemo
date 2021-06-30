# CMakeList.txt: lsn4_example 的 CMake 项目，包括源和定义
# 此处特定于项目的逻辑。
#
cmake_minimum_required (VERSION 3.8)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} /MD /Zi /O2 /Ob1 /DNDEBUG /showIncludes ")

# 将源添加到此项目的可执行文件。
add_executable (lsn4example "lsn4_example.cpp" Instance.cpp Student.cpp "lsn4_example.h")

# TODO: 如有需要，请添加测试并安装目标。


# android 播放器 做一个工具 
# AvFrame* AvPacket*  做一个模板类