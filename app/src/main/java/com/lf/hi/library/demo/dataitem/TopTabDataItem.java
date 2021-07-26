package com.lf.hi.library.demo.dataitem;

import android.widget.AbsListView;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.lf.hi.library.R;
import com.lf.hi.ui.dataitem.HiDataItem;

import org.jetbrains.annotations.NotNull;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.library.demo.dataitem$
 * @ClassName: TopTabDataItem$
 * @Author: LF
 * @CreateDate: 2021/7/6$ 14:57$
 * @Description:
 */
public class TopTabDataItem extends HiDataItem<ItemData, RecyclerView.ViewHolder> {

    public TopTabDataItem(ItemData itemData) {
        super(itemData);
    }

    @Override
    public void onBindData(@NotNull RecyclerView.ViewHolder holder, int position) {
        ImageView imageView = holder.itemView.findViewById(R.id.item_image);
        imageView.setImageResource(R.drawable.image);
    }

    @Override
    public int getItemLayoutRes() {
        return R.layout.layout_list_item_top_tab;
    }
}
