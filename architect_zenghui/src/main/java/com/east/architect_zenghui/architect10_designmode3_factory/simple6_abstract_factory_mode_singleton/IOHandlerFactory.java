package com.east.architect_zenghui.architect10_designmode3_factory.simple6_abstract_factory_mode_singleton;



/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 工厂设计模式 - 抽象工厂(+单例)
 *  @author: East
 *  @date: 2020-02-18
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class IOHandlerFactory implements IOFactory{

    private static IOHandlerFactory mInstance;

    private IOHandlerFactory(){}

    public static IOHandlerFactory getInstance(){
        if(mInstance == null){
            synchronized (IOHandlerFactory.class){
                if(mInstance == null)
                    mInstance = new IOHandlerFactory();
            }
        }
        return mInstance;
    }

    // 如果觉得有必要那么完全可以写成单例设计模式
    @Override
    public IOHandler createIOHandler(Class<? extends IOHandler> ioHandlerClazz) {
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
    public IOHandler getMemoryIOHandler(){
        return createIOHandler(MemoryIOHandler.class);
    }

    /**
     * 获取磁盘存储
     */
    public IOHandler getDiskIOHandler(){
        return createIOHandler(MemoryIOHandler.class);
    }

    /**
     * 获取SP存储
     */
    public IOHandler getPreferencesIOHandler(){
        return createIOHandler(PreferencesIOHandler.class);
    }


    /**
     * 获取默认存储
     */
    public IOHandler getDefaultIOHandler(){
        return createIOHandler(PreferencesIOHandler.class);
    }
}
