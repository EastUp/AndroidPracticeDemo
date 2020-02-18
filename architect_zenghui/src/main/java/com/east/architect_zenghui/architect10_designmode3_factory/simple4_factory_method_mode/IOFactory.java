package com.east.architect_zenghui.architect10_designmode3_factory.simple4_factory_method_mode;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 * @description: 生成类的工厂接口  - 工厂方法模式
 * @author: East
 * @date: 2020-02-18
 * |---------------------------------------------------------------------------------------------------------------|
 */
public interface IOFactory {

    /**
     *  也有一些小问题 就随着功能的扩展，我们的 IOFactory  类会不断的增加，而且逻辑基本一样，在一定程度代码冗余度高
     */
    IOHandler createIOHandler();
}
