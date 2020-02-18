package com.east.architect_zenghui.architect10_designmode3_factory.simple6_abstract_factory_mode_singleton;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 生成工厂类的接口
 *  @author: East
 *  @date: 2020-02-18
 * |---------------------------------------------------------------------------------------------------------------|
 */
public interface IOFactory {
    IOHandler createIOHandler(Class<? extends IOHandler> ioHandlerClazz);
}
