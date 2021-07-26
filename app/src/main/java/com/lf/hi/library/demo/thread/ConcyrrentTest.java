package com.lf.hi.library.demo.thread;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.library.demo.thread$
 * @ClassName: MyAsyncTask$
 * @Author: LF
 * @CreateDate: 2021/7/9$ 16:17$
 * @Description:
 */
public class ConcyrrentTest {
    private static String TAG = "ConcyrrentTest";

    public static void test(Context context) {
        class MyAsyncTask extends AsyncTask<String, Integer, String> {
            @Override
            protected String doInBackground(String... params) {
                for (int i = 0; i < 10; i++) {
                    //任务执行结果传递出去
                    publishProgress(i * 10);
                }
                return params[0];
            }

            @Override
            protected void onPostExecute(String result) {
                //拿到任务执行结果
                Log.e(TAG, "onPostExecute:" + result);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                //拿到任务执行进度
                Log.e(TAG, "onProgressUpdate:" + values[0]);
            }
        }

        //适用于需要知道任务执行进度并更新UI的场景
        MyAsyncTask asyncTask = new MyAsyncTask();
        //AsyncTask所有任务默认串行执行
        asyncTask.execute("execute MyAsyncTask");
        //并发执行
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "execute MyAsyncTask");

        //以这种方式提交的任务，所有任务串行执行，及先来后到，但如果其中一条任务休眠了，或执行时间过长，后面的任务都将被阻塞
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //……
            }
        });

        //使用内置的THREAD_POOL_EXECUTOR线程池 并发执行
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                //……
            }
        });

        HandlerThread handlerThread = new HandlerThread("handler-thread");
        handlerThread.start();
        MyHandler myHandler = new MyHandler(handlerThread.getLooper());
        myHandler.sendEmptyMessage(MSG_WHAT_1);

        LooperThread looperThread = new LooperThread("looper-thread");
        looperThread.start();
        Handler handler = new Handler(looperThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.e(TAG, "handleMessage: " + msg.what);
                Log.e(TAG, "handleMessage: " +Thread.currentThread().getName());

            }
        };
        handler.sendEmptyMessage(MSG_WHAT_1);
    }

    public static class LooperThread extends Thread {
        private Looper looper;
        public LooperThread(String name) {
            super(name);
        }
        public Looper getLooper() {
            synchronized (this) {
                if (looper == null && isAlive()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return looper;
        }

        @Override
        public void run() {
            Looper.prepare();
            synchronized (this) {
                looper = Looper.myLooper();
                notify();
            }
            Looper.loop();
        }
    }


    static class MyHandler extends Handler {
        public MyHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    }

    private static final int MSG_WHAT_1 = 1;


}


