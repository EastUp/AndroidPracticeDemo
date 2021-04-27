# CMakeList.txt: lsn6_example 的 CMake 项目，包括源和定义
# 此处特定于项目的逻辑。
#
cmake_minimum_required (VERSION 3.8)

include_directories("D:/Lance/ndk/lsn6_cpp/资料/pthreads-w32-2-9-1-release/Pre-built.2/include")
link_directories("D:/Lance/ndk/lsn6_cpp/资料/pthreads-w32-2-9-1-release/Pre-built.2/lib/x86")

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -DHAVE_STRUCT_TIMESPEC")

# 将源添加到此项目的可执行文件。
add_executable (lsn6example "lsn6_example.cpp" "lsn6_example.h")
target_link_libraries(lsn6example pthreadVC2)
# TODO: 如有需要，请添加测试并安装目标。
