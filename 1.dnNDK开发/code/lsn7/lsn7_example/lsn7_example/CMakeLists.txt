# CMakeList.txt: lsn7_example 的 CMake 项目，包括源和定义
# 此处特定于项目的逻辑。
#
cmake_minimum_required (VERSION 3.8)



include_directories("C:/Program Files/Java/jdk1.8.0_171/include")
include_directories("C:/Program Files/Java/jdk1.8.0_171/include/win32")
#生成一个库  so 动态库
add_library(lsn7 SHARED lsn7_example.cpp)



# 将源添加到此项目的可执行文件。
# add_executable (lsn7example "lsn7_example.cpp" "lsn7_example.h")



# TODO: 如有需要，请添加测试并安装目标。
