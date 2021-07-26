package com.lf.hi.library.demo.dataitem

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.lf.hi.library.R
import com.lf.hi.ui.dataitem.HiDataItem

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.demo.dataitem$
 * @ClassName:      TopBanner$
 * @Author:         LF
 * @CreateDate:     2021/7/6$ 15:09$
 * @Description:
 */
class TopBanner(data: ItemData):HiDataItem<ItemData,RecyclerView.ViewHolder>(data) {

    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.item_image)
    }

    override fun getItemLayoutRes(): Int {
        return R.layout.layout_list_item_banner
    }
}