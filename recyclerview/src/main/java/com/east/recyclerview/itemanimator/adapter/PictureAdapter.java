package com.east.recyclerview.itemanimator.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.east.recyclerview.R;

import java.util.List;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {

    private List<String> picList;

    public PictureAdapter() {
    }

    public PictureAdapter(List<String> picList) {
        this.picList = picList;
    }

    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_activity_rv,
                parent, false);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PictureViewHolder holder, final int position) {
    }

    @Override
    public int getItemCount() {
        /*if (picList == null || picList.size() < 1) {
            return 0;
        }
        return picList.size();*/
        return picList.size();
    }

    static class PictureViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private ImageView imageView;

        public PictureViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            initView();
        }

        private void initView() {
            imageView = mView.findViewById(R.id.iv_meizhi);
        }
    }

    /**
     * 添加测试，用于展现动画
     *
     * @param position
     */
    public void insertTest(int position) {
        picList.add(position, picList.get(picList.size() - 1));
        // 这里更新数据集不是用adapter.notifyDataSetChanged()而是
        // notifyItemInserted(position)与notifyItemRemoved(position)
        // 否则没有动画效果。
        notifyItemInserted(position);
    }

    /**
     * 删除测试，用于展现动画
     *
     * @param position
     */
    public void removeTest(int position) {
        picList.remove(picList.get(position));
        notifyItemRemoved(position);
    }
}
