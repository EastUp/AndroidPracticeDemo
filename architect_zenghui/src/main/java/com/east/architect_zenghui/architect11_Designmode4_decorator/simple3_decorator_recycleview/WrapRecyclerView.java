package com.east.architect_zenghui.architect11_Designmode4_decorator.simple3_decorator_recycleview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 增强的RecyclerView
 *  @author: East
 *  @date: 2020-02-18
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class WrapRecyclerView extends RecyclerView {

    private WrapRecyclerAdapter mWrapRecyclerAdapter;

    public WrapRecyclerView(@NonNull Context context) {
        super(context);
    }

    public WrapRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        mWrapRecyclerAdapter = new WrapRecyclerAdapter(adapter);
        super.setAdapter(mWrapRecyclerAdapter);
    }


    /**
     * 添加头部
     * @param view
     */
    public void addHeaderView(View view){
        // 必须要设置 Adapter 之后才能添加头部和底部
        if(mWrapRecyclerAdapter != null){
            mWrapRecyclerAdapter.addHeaderView(view);
        }
    }

    /**
     * 添加底部
     * @param view
     */
    public void addFooterView(View view){
        if(mWrapRecyclerAdapter != null){
            mWrapRecyclerAdapter.addFooterView(view);
        }
    }

    /**
     * 移除头部
     * @param view
     */
    public void removeHeaderView(View view){
        if(mWrapRecyclerAdapter != null){
            mWrapRecyclerAdapter.removeHeaderView(view);
        }
    }

    /**
     * 移除底部
     * @param view
     */
    public void removeFooterView(View view){
        if(mWrapRecyclerAdapter != null){
            mWrapRecyclerAdapter.removeFooterView(view);
        }
    }
}
