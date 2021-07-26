package com.lf.hi.library.demo.coroutine;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.library.demo.coroutine$
 * @ClassName: CoroutineScene2_decompiled$
 * @Author: LF
 * @CreateDate: 2021/7/20$ 14:23$
 * @Description:
 */
public class CoroutineScene2_decompiled {
    public static final Object request2(Continuation preCallback) {
        ContinuationImpl request2Callback;

        request2Callback = new ContinuationImpl(preCallback) {

            @Override
            Object invokeSuspend(@NonNull Object resumeResult) {
                this.result = resumeResult;
                this.label |= Integer.MIN_VALUE;
                return request2(this);
            }
        };
        return null;
    }

    static abstract class ContinuationImpl<T> implements Continuation<T> {

        private final Continuation preCallback;
        int label;
        Object result;

        public ContinuationImpl(Continuation preCallback) {
            this.preCallback = preCallback;
        }

        @NotNull
        @Override
        public CoroutineContext getContext() {
            return preCallback.getContext();
        }

        @Override
        public void resumeWith(@NotNull Object resumeResult) {

        }

        abstract Object invokeSuspend(@NonNull Object resumeResult);

    }
}
