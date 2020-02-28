package com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple1;

import com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.simple1.iterator.Iterator;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 容器的接口
 *  @author: East
 *  @date: 2020-02-25
 * |---------------------------------------------------------------------------------------------------------------|
 */
public interface Aggregate<T> {

    // Aggregate 离开 Iterator 还可以用吗？不能用 ，
    // 整体 Aggregate（不能用） 局部 Iterator（可以存在）
    Iterator<T> iterator();
}
