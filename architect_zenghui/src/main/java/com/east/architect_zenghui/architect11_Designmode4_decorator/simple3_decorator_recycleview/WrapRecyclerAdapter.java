package com.east.architect_zenghui.architect11_Designmode4_decorator.simple3_decorator_recycleview;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 装饰者设计模式的 RecyclerView.Adapter,我们对其功能进行扩展,使它支持头部和底部的添加
 *  @author: East
 *  @date: 2020-02-18
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class WrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // 原来的RecyclerView.Adapter，并不支持头部和底部的添加
    private RecyclerView.Adapter mRealAdapter;
    ArrayList<View> mHeaderViews;
    ArrayList<View> mFooterViews;

    public WrapRecyclerAdapter(RecyclerView.Adapter realAdapter) {
        this.mRealAdapter = realAdapter;

        //当原来的adapter发送修改时,通知自己也修改
        mRealAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                notifyDataSetChanged();
            }
        });

        mHeaderViews = new ArrayList<>();
        mFooterViews = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        // 问题如果想知道是哪个部分，必须要知道 position 也就是位置 ，但是目前只有 viewType
        // 头部返回 头部的ViewHolder
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return createHeaderFooterViewHolder(mHeaderViews.get(position));
        }

        // mRealAdapter 返回 mRealAdapter的ViewHolder
        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mRealAdapter != null) {
            adapterCount = mRealAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mRealAdapter.onCreateViewHolder(parent,mRealAdapter.getItemViewType(adjPosition));
            }
        }

        // 底部返回 底部的ViewHolder
        // Footer (off-limits positions will throw an IndexOutOfBoundsException)
        return createHeaderFooterViewHolder(mFooterViews.get(adjPosition-adapterCount));

    }

    private RecyclerView.ViewHolder createHeaderFooterViewHolder(View view) {
        return new RecyclerView.ViewHolder(view){};
    }

    private int getHeadersCount() {
        return mHeaderViews.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        // 头部和底部是都不需要做处理的，只要 mRealAdapter 要去做处理
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return;
        }

        // mRealAdapter 返回 mRealAdapter的ViewHolder
        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mRealAdapter != null) {
            adapterCount = mRealAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                mRealAdapter.onBindViewHolder(holder,adjPosition);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        // 把位置作为 viewType
        return position;
    }

    @Override
    public int getItemCount() { // 总共返回多少条 = 底部条数+头部条数+真实的Adapter条数
        return mHeaderViews.size()+mFooterViews.size()+mRealAdapter.getItemCount();
    }

    /**
     *  添加头部
     */
    public void addHeaderView(View view){
        if(!mHeaderViews.contains(view)){
            mHeaderViews.add(view);
            notifyDataSetChanged();
        }
    }

    /**
     *  添加底部
     */
    public void addFooterView(View view){
        if(!mFooterViews.contains(view)){
            mFooterViews.add(view);
            notifyDataSetChanged();
        }

    }

    /**
     *  移除头部
     */
    public void removeHeaderView(View view){
        if(mHeaderViews.contains(view)){
            mHeaderViews.remove(view);
            notifyDataSetChanged();
        }
    }

    /**
     *  移除底部
     */
    public void removeFooterView(View view){
        if(mFooterViews.contains(view)){
            mFooterViews.remove(view);
            notifyDataSetChanged();
        }
    }
}
