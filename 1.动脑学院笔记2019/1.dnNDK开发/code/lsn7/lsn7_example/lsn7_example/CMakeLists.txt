# CMakeList.txt: lsn7_example 的 CMake 项目，在此处包括源代码并定义
# 项目特定的逻辑。
#
cmake_minimum_required (VERSION 3.8)

# 引入jni头文件支持
include_directories("C:/Program Files/Java/jdk1.8.0_281/include")
# windows:win32  Mac:darwin
include_directories("C:/Program Files/Java/jdk1.8.0_281/include/win32")

# 修改生成动态库  so 动态库
add_library(lsn7 SHARED lsn7_example.cpp) 

# 将源代码添加到此项目的可执行文件。
# add_executable (lsn7_example "lsn7_example.cpp" "lsn7_example.h")

# TODO: 如有需要，请添加测试并安装目标。
