# 1、日志相关
1. 输出指定标签内容
   - ”-s”选项 : 设置默认的过滤器, 如 我们想要输出 “System.out” 标签的信息, 就可以使用adb logcat -s System.out 命令;
   - `adb logcat -s System.out`
2. 输出日志信息到手机文件
   - ”-f”选项 : 该选向后面跟着输入日志的文件, 使用adb logcat -f /sdcard/log.txt 命令, 注意这个**log文件是输出到手机上**，需要指定合适的路径.
   - `adb logcat -f /sdcard/log.txt`, 这个参数对对不能一直用电脑连着手机收集日志的场景非常有用，其实Android shell下也有一个相同参数的logcat命令。使用如下命令可以执行后断开PC和手机持续收集LOG。
   ```
    // pc上进入手机shell
    shell@pc$ adb shell  
    // 手机shell执行 
    shell@android$ logcat -f /sdcard/log.txt &   #这里的&符号表示后台执行，别少了。  
    shell@android$ exit     
   
   一定注意合适的时候需要停止掉以上命令，否则再次使用相同命令的时候，就会有两个logcat写同一个文件了
    停止方法:  adb shell kill -9 <logcat_pid>         
   其中logcat_pid 通过 如下命令获取

   adb shell ps | grep logcat          # linux 平台
   adb shell ps | findstr “logcat”    #Windows平台 
   ```
   - 也可以使用 `adb logcat -d -v /sdcard/mylog.txt  `
3. 输入日志信息到电脑文件
   - “>”输出 : “>” 后面跟着要输出的日志文件, 可以将 logcat 日志输出到文件中, 使用adb logcat > log 命令, 使用more log 命令查看日志信息;
   - `adb logcat > c:\mylog.txt 把日志信息重定向到一个文件中`
   - 
4. 指定 logcat 的日志输出格式
   –  ”time”格式  :  ”日期 时间 优先级 / 标签 (进程ID) : 进程名称 : 日志信息 “ , 使用 adb logcat -v time  命令;
   - `adb logcat -v time`
5. 清空日志缓存信息
   - 使用  `adb logcat -c`  命令, 可以将之前的日志信息清空, 重新开始输出日志信息;
6. 过滤项解析
   - `adb logcat *:E` : 过滤`Error`以上级别的日志;
   - `adb logcat WifiHW:D` : 过滤标签为 `WifiHW, 并且优先级 Debug(调试) 等级以上的日志;

常用的： <font color=red size = 6>显示某一级别以上的日志：adb logcat -v time *:E > c:\elog.log</font>

# 2. 调试相关

我们一般的调试方式都是先在某个地方打上断点，然后在运行中用`AS Attach debugger to Android process`进行调试的，`这种方式不适合在启动app的
地方断点调试`，如果需要在启动的地方调试则需要`Run Debug app`，然而这种方式一般都是比较慢的，尤其是大项目，因为这里包含了编译运行等工作。

## 2.1. 在启动app的地方断点调试
```
# 开启调试模式
am set-debug-app: set application <PACKAGE> to debug.  Options are:
    -w: wait for debugger when application starts
    --persistent: retain this value
# 清除调试模式    
am clear-debug-app: clear the previously set-debug-app.    
```
- `-w` 启动时等待Debugger Attacth
- `--persistent` 每次启动都开启调试模式，没有这个标志则表示一次性的
- 开启调试某个app命令：`adb shell am set-debug-app -w 包名`
- 关闭调试某个app命令: `adb shell am clear-debug-app 包名`

