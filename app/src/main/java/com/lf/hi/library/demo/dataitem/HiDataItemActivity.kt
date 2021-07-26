package com.lf.hi.library.demo.dataitem

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lf.hi.library.R
import com.lf.hi.ui.dataitem.HiAdapter
import com.lf.hi.ui.dataitem.HiDataItem
import kotlinx.android.synthetic.main.activity_hi_data_item.*

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.demo.dataitem$
 * @ClassName:      HiDataItemActivity$
 * @Author:         LF
 * @CreateDate:     2021/7/6$ 15:27$
 * @Description:
 */
class HiDataItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_data_item)
        var hiAdapter = HiAdapter(this)
        recycler_view.layoutManager = GridLayoutManager(this, 2)
        recycler_view.adapter = hiAdapter

        var dataSets: ArrayList<HiDataItem<*,out RecyclerView.ViewHolder>> = ArrayList()
        dataSets.add(TopTabDataItem(ItemData()))
        dataSets.add(TopBanner(ItemData()))
        dataSets.add(GirdDataItem(ItemData()))

//        hiAdapter.addItems(dataSets, false)
    }
}


