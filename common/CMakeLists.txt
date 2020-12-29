cmake_minimum_required(VERSION 3.4.1)
set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_CURRENT_LIST_DIR}/build/libs/${ANDROID_ABI})

add_library(
        jni-util
        SHARED
        ${CMAKE_CURRENT_LIST_DIR}/src/main/cpp/jni-util.cpp
)



find_library(
        log-lib
        log
)

target_link_libraries(
        jni-util
        ${log-lib}
)