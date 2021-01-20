package com.east.framelibrary.skin.callback;

import com.east.framelibrary.skin.attr.SkinView;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:   换肤时的监听
 *  @author: jamin
 *  @date: 2020/5/22
 * |---------------------------------------------------------------------------------------------------------------|
 */
public interface SkinChangeListener {
    void changeSkin(boolean originalResource,SkinView skinView);
}
