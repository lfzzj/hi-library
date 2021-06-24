package com.lf.hi.hilibrary.util;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.hilibrary.util$
 * @ClassName: HiViewUtil$
 * @Author: LF
 * @CreateDate: 2021/6/24$ 10:40$
 * @Description:
 */
public class HiViewUtil {

    /**
     * 获取指定类型的子view
     *
     * @param group
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T findTypeView(@Nullable ViewGroup group, Class<T> cls) {
        if (group == null) {
            return null;
        }
        Deque<View> deque = new ArrayDeque<>();//双端 队列
        deque.add(group);
        while (!deque.isEmpty()) {//双端 队列不为空
            View node = deque.removeFirst();
            if (cls.isInstance(node)) {
                return cls.cast(node);
            } else if (node instanceof ViewGroup) {
                ViewGroup container = (ViewGroup) node;
                for (int i = 0, count = container.getChildCount(); i < count; i++) {
                    deque.add(container.getChildAt(i));
                }
            }
        }
        return null;
    }
}
