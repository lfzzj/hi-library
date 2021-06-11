package com.lf.hi.hilibrary.log;

import androidx.annotation.NonNull;

/**
 * @author: LF
 * @data on 2021/4/14 下午10:49
 * @desc TODO
 */
public interface HiLogPrinter {
    void print(@NonNull HiLogConfig config,int level,String tag,@NonNull String printString);

}
