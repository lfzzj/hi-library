package com.lf.hi.hilibrary.log;

/**
 * @author: LF
 * @data on 2021/4/14 下午10:13
 * @desc TODO
 */
public class HiLogConfig {
    static int MAX_LEN = 512;

    static HiStackTraceFormatter HI_STACK_TRACE_FORMATTER = new HiStackTraceFormatter();
    static HiThreadFormatter HI_THREAD_FORMATTER = new HiThreadFormatter();

    //
    public jsonParser injectJsonParser() {
        return null;
    }

    public String getGlobalTag() {
        return "HiLog";
    }

    public boolean enable() {
        return true;
    }

    //日志里是否包含线程信息
    public boolean includeThread() {
        return false;
    }

    public HiLogPrinter[] printers(){
        return null;
    }

    //获取堆栈信息的深度
    public int stackTraceDepth(){
        return 5;
    }

    //序列化交给调用者来实现
    public interface jsonParser {
        String toJson(Object src);
    }

}
