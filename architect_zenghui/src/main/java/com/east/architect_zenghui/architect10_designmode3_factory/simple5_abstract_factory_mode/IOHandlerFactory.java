package com.east.architect_zenghui.architect10_designmode3_factory.simple5_abstract_factory_mode;


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 工厂设计模式 - 抽象工厂
 *  @author: East
 *  @date: 2020-02-18
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class IOHandlerFactory {

    // 如果觉得有必要那么完全可以写成单例设计模式

    public static IOHandler createIOHandler(Class<? extends IOHandler> ioHandlerClazz) {
        try {
            return ioHandlerClazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取运行内存存储
     */
    public static IOHandler getMemoryIOHandler(){
        return createIOHandler(MemoryIOHandler.class);
    }

    /**
     * 获取磁盘存储
     */
    public static IOHandler getDiskIOHandler(){
        return createIOHandler(MemoryIOHandler.class);
    }

    /**
     * 获取SP存储
     */
    public static IOHandler getPreferencesIOHandler(){
        return createIOHandler(PreferencesIOHandler.class);
    }


    /**
     * 获取默认存储
     */
    public static IOHandler getDefaultIOHandler(){
        return createIOHandler(PreferencesIOHandler.class);
    }
}
