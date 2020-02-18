package com.east.architect_zenghui.architect10_designmode3_factory.simple3_simple_factory_mode;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 * @description: 工厂设计模式, 简单工厂模式
 * @author: East
 * @date: 2020-02-18
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class IOHandlerFactory {

    public enum IOType {
        MEMORY, PREFERENCES, DISK
    }

    /**
     *  这种方式有问题:
     *          例如我新增一种存储方式,需要改动原来很多的代码
     */
    public static IOHandler createIOHandler(IOType ioType) {
        switch (ioType) {
            case MEMORY:
                // 直接返回一个对象，有的时候我们需要创建对象之后，要进行一系列的初始化参数
                return new MemoryIOHandler();
            case DISK:
                return new DiskIOHandler();
            case PREFERENCES:
                return new PreferencesIOHandler();
            default:
                return null;
        }
    }
}
