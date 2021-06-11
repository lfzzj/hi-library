package com.lf.hi.hilibrary.log;

/**
 * @author: LF
 * @data on 2021/4/14 下午10:52
 * @desc TODO
 */
public interface HiLogFormatter<T> {
    String format(T data);
}
