package com.lf.hi.hilibrary.log;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lf.hi.hilibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: LF
 * @data on 2021/5/7 下午9:42
 * @desc TODO将log显示在界面上
 */
public class HiViewPrinter implements HiLogPrinter {
    private RecyclerView recyclerView;
    private LogViewAdapter adapter;
    private HiViewPrinterProvider viewProvider;

    public HiViewPrinter(Activity activity) {
        FrameLayout rootView = activity.findViewById(android.R.id.content);
        recyclerView = new RecyclerView(activity);
        adapter = new LogViewAdapter(LayoutInflater.from(recyclerView.getContext()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        viewProvider = new HiViewPrinterProvider(rootView, recyclerView);
    }

    /**
     * 获取viewProvider，可以控制视图对展示和隐藏
     *
     * @return
     */
    @NonNull
    public HiViewPrinterProvider getViewProvider() {
        return viewProvider;
    }

    @Override
    public void print(@NonNull HiLogConfig config, int level, String tag, @NonNull String printString) {
        //将log展示添加到recyclerview
        adapter.addItem(new HiLogMo(System.currentTimeMillis(), level, tag, printString));
        //滚动到对应到位置
        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    private static class LogViewAdapter extends RecyclerView.Adapter<LogViewHolder> {
        private LayoutInflater inflater;
        private List<HiLogMo> logs = new ArrayList<>();

        public LogViewAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        public void addItem(HiLogMo mo) {
            logs.add(mo);
            notifyItemInserted(logs.size() - 1);
        }

        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.hilog_item, parent, false);
            return new LogViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
            HiLogMo logItem = logs.get(position);
            int color = getHighlightColor(logItem.level);
            holder.tagView.setTextColor(color);
            holder.messageView.setTextColor(color);
            holder.tagView.setText(logItem.getFlattened());
            holder.messageView.setText(logItem.log);
        }

        /**
         * @param logLevel
         * @return
         */
        private int getHighlightColor(int logLevel) {
            int highLight;
            switch (logLevel) {
                case HiLogType.V:
                    highLight = 0xffbbbbbb;
                    break;
                case HiLogType.D:
                    highLight = 0xfffffff;
                    break;
                case HiLogType.I:
                    highLight = 0xf6a8759;
                    break;
                case HiLogType.W:
                    highLight = 0xffbbb529;
                    break;
                case HiLogType.E:
                    highLight = 0xffbb6b68;
                    break;
                default:
                    highLight = 0xffffff00;
                    break;
            }
            return highLight;
        }

        @Override
        public int getItemCount() {
            return logs.size();
        }
    }

    private static class LogViewHolder extends RecyclerView.ViewHolder {

        private TextView tagView;
        private TextView messageView;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            tagView = itemView.findViewById(R.id.tag);
            messageView = itemView.findViewById(R.id.message);

        }
    }
}
