package com.lf.hi.hilibrary.log;

/**
 * @author: LF
 * @data on 2021/4/14 下午10:53
 * @desc TODO
 */
public class HiThreadFormatter implements HiLogFormatter<Thread>{
    @Override
    public String format(Thread data) {
        return "Thread:"+data.getName();
    }
}
