package com.lf.hi.hilibrary.log;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author: LF
 * @data on 2021/5/7 下午9:53
 * @desc TODO
 */
public class HiLogMo {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    public long timeMillis;
    public int level;
    public String tag;
    public String log;

    public HiLogMo(long timeMillis, int level, String tag, String log) {
        this.timeMillis = timeMillis;
        this.level = level;
        this.tag = tag;
        this.log = log;
    }

    public String getFlattened() {
        return format(timeMillis) + "|" + level + "|" + tag + "|:";

    }

    public String format(long timeMillis) {
        return sdf.format(timeMillis);
    }
}
