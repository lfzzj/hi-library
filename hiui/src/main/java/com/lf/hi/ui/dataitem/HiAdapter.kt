package com.lf.hi.ui.dataitem

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.ParameterizedType
import kotlin.collections.ArrayList

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.ui.dataitem$
 * @ClassName:      HiAdapter$
 * @Author:         LF
 * @CreateDate:     2021/7/6$ 11:58$
 * @Description:
 */
class HiAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext: Context
    private var mInflater: LayoutInflater? = null
    private var dataSets = ArrayList<HiDataItem<*, RecyclerView.ViewHolder>>()
    private var typeArrays = SparseArray<HiDataItem<*, RecyclerView.ViewHolder>>()

    init {
        this.mContext = context
        this.mInflater = LayoutInflater.from(context)
    }

    fun addItem(index: Int, item: HiDataItem<*, RecyclerView.ViewHolder>, notify: Boolean) {
        if (index > 0) {
            dataSets.add(index, item)
        } else {
            dataSets.add(item)
        }
        val notifyPos = if (index > 0) index else dataSets.size - 1
        if (notify) {
            notifyItemInserted(notifyPos)
        }
    }

    fun addItems(items: ArrayList<HiDataItem<*, RecyclerView.ViewHolder>>, notify: Boolean) {
        val start = dataSets.size
        for (item in items) {
            dataSets.add(item)
        }
        if (notify) {
            notifyItemRangeInserted(start, items.size)
        }
    }

    fun removeItem(index: Int): HiDataItem<*, RecyclerView.ViewHolder>? {
        if (index > 0 && index < dataSets.size) {
            val remove = dataSets.removeAt(index)
            notifyItemRemoved(index)
            return remove
        } else {
            return null
        }
    }

    fun removeItem(item: HiDataItem<*, *>) {
        val index = dataSets.indexOf(item)
        removeItem(index)
    }

    override fun getItemViewType(position: Int): Int {
        val dataItem = dataSets.get(position)
        val type = dataItem.javaClass.hashCode()
        //如果还没有包含这种类型的item，则添加进来
        if (typeArrays.indexOfKey(type) < 0) {
            typeArrays.put(type, dataItem)
        }
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val dataItem = typeArrays.get(viewType)
        var view: View? = dataItem.getItemView(parent)
        if (view == null) {
            val layoutRes = dataItem.getItemLayoutRes()
            if (layoutRes < 0) {
                RuntimeException("dataItem: ${dataItem.javaClass.name} must override getItemView or getItemLayoutRes")
            }
            view = mInflater!!.inflate(layoutRes, parent, false)
        }
        return createViewHolderInternal(dataItem.javaClass, view)
    }

    private fun createViewHolderInternal(
        javaClass: Class<HiDataItem<*, RecyclerView.ViewHolder>>,
        view: View?
    ): RecyclerView.ViewHolder {
        val superclass = javaClass.genericSuperclass
        if (superclass is ParameterizedType) {
            val arguments = superclass.actualTypeArguments
            for (argument in arguments) {
                if (argument is Class<*> && RecyclerView.ViewHolder::class.java.isAssignableFrom( //父类 isAssignableFrom 本身或者其子类
                        argument
                    )
                ) {
                    return argument.getConstructor(View::class.java)
                        .newInstance(view) as RecyclerView.ViewHolder
                }
            }
        }
        return object : RecyclerView.ViewHolder(view!!) {}
    }

    override fun getItemCount(): Int {
        return dataSets.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val hiDataItem = dataSets.get(position)
        hiDataItem.onBindData(holder, position)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (position < dataSets.size) {
                        val hiDataItem = dataSets.get(position)
                        val spanSize = hiDataItem.getSpanSize()
                        return if (spanSize <= 0) spanCount else spanSize
                    }
                    return spanCount
                }

            }
        }
    }

    fun refreshItem(hiDataItem: HiDataItem<*, *>) {
        val index = dataSets.indexOf(hiDataItem)
        notifyItemChanged(index)
    }


}