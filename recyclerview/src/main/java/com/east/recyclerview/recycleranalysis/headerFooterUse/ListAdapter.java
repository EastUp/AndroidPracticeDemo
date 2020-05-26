package com.east.recyclerview.recycleranalysis.headerFooterUse;

import android.content.Context;

import com.east.recyclerview.R;
import com.east.recyclerview.recycleranalysis.adapter.CommonRecyclerAdapter;
import com.east.recyclerview.recycleranalysis.adapter.ViewHolder;

import java.util.List;

/**
 * Created by Darren on 2016/12/28.
 * Email: 240336124@qq.com
 * Description: 利用万能通用的Adapter改造后的列表
 */
public class ListAdapter extends CommonRecyclerAdapter<String> {

    public ListAdapter(Context context, List<String> datas) {
        super(context, datas, R.layout.channel_list_item);
    }

    @Override
    public void convert(ViewHolder holder, String item) {
        holder.setText(R.id.channel_text, item);
    }
}
