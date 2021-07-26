package com.lf.hi.library

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library$
 * @ClassName:      ActivityManager$
 * @Author:         LF
 * @CreateDate:     2021/6/29$ 15:08$
 * @Description:
 */
class ActivityManager private constructor() {
    private val activityRefs = ArrayList<WeakReference<Activity>>()
    private val frontbackCallback = ArrayList<FrontbackCallback>()
    private var activityStartCount = 0
    private var front = true

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(InnerActivityLifecyclerCallBacks())
    }

    inner class InnerActivityLifecyclerCallBacks : Application.ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStarted(activity: Activity) {
            activityStartCount++
            //activityStartCount > 0 说明应用处于可见状态，也就是前台
            //!front 之前是不是在后台
            if (!front && activityStartCount > 0) {
                front = true
                onFrontBackChanged(front)
            }
        }

        override fun onActivityDestroyed(activity: Activity) {
            for (activityRef in activityRefs) {
                if (activityRef.get() == activity) {
                    activityRefs.remove(activityRef)
                    break
                }
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityStopped(activity: Activity) {
            activityStartCount--
            if (activityStartCount <= 0 && front) {
                front = false
                onFrontBackChanged(front)
            }
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            activityRefs.add(WeakReference(activity))
        }

        override fun onActivityResumed(activity: Activity) {
        }

    }

    /**
     * 前后台切换监听
     */
    private fun onFrontBackChanged(front: Boolean) {
        for (callback in frontbackCallback) {
            callback.onChange(front)
        }
    }

    /**
     * 获取栈顶activity
     */
    val topActivity: Activity?
        get() {
            if (activityRefs.size <= 0) {
                return null
            } else {
                return activityRefs[activityRefs.size - 1].get()
            }
        }

    fun addFrontbackCallback(callback: FrontbackCallback) {
        frontbackCallback.add(callback)
    }

    fun removeFrontbackCallback(callback: FrontbackCallback) {
        frontbackCallback.remove(callback)
    }

    interface FrontbackCallback {
        fun onChange(front: Boolean)
    }

    companion object {
        val instant: ActivityManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityManager()
        }
    }
}