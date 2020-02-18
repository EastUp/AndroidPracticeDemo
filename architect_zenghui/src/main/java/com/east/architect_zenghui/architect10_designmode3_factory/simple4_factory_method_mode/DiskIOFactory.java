package com.east.architect_zenghui.architect10_designmode3_factory.simple4_factory_method_mode;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:
 *  @author: East
 *  @date: 2020-02-18
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class DiskIOFactory implements IOFactory {
    @Override
    public IOHandler createIOHandler() {
        return new DiskIOHandler();
    }
}
