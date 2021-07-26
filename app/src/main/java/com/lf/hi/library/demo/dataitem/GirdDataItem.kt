package com.lf.hi.library.demo.dataitem

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.lf.hi.library.R
import com.lf.hi.ui.dataitem.HiDataItem


/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.demo.dataitem$
 * @ClassName:      GirdDataItem$
 * @Author:         LF
 * @CreateDate:     2021/7/6$ 15:13$
 * @Description:
 */
class GirdDataItem(data: ItemData) : HiDataItem<ItemData, GirdDataItem.MyHolder>(data) {

    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

//    override fun onBindData(holder:MyHolder, position: Int) {
//        holder.imageView!!.setImageResource(R.drawable.image)
//    }


    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView? = null

        init {
            imageView = itemView.findViewById<ImageView>(R.id.item_image)
        }
    }



    override fun getItemLayoutRes(): Int {
        return R.layout.layout_list_item_top_tab
    }


}