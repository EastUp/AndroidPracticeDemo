package com.shu_da_shi.myapplication.glide.load.model;


import com.shu_da_shi.myapplication.glide.cache.Key;
import com.shu_da_shi.myapplication.glide.load.model.data.DataFetcher;

/**
 * Model 表示的是数据的来源
 * Data 加载成功后的数据类型(inputStream, byte[])
 * @param <Model>
 * @param <Data>
 */
public interface ModelLoader<Model, Data> {

    interface ModelLoaderFactory<Model, Data>{
        ModelLoader<Model, Data> build(ModelLoaderRegistry registry);
    }

    class  LoadData<Data>{
        //缓存的key
        final Key key;

        public DataFetcher<Data> fetcher;

        public LoadData(Key key, DataFetcher<Data> fetcher) {
            this.fetcher = fetcher;
            this.key = key;
        }
    }

    /**
     * 判断处理对应model的数据
     * @return
     */
    boolean handles(Model model);

    /**
     * 创建加载的数据方式
     * @param model
     */
    LoadData<Data> buildData(Model model);
}
