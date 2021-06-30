package com.shu_da_shi.myapplication.glide.load.model;


import com.shu_da_shi.myapplication.glide.cache.Key;
import com.shu_da_shi.myapplication.glide.load.model.data.DataFetcher;
import com.shu_da_shi.myapplication.glide.load.model.data.MultiFetcher;

import java.util.ArrayList;
import java.util.List;

public class MultiModelLoader<Model, Data> implements ModelLoader<Model, Data> {
    //代理多个modelloader
    private final List<ModelLoader<Model, Data>> modelLoaders;

    MultiModelLoader(List<ModelLoader<Model, Data>> modelLoaders) {
        this.modelLoaders = modelLoaders;
    }

    @Override
    public LoadData<Data> buildLoadData(Model model) {
        Key sourceKey = null;
        int size = modelLoaders.size();
        List<DataFetcher<Data>> fetchers = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ModelLoader<Model, Data> modelLoader = modelLoaders.get(i);
            if (modelLoader.handles(model)) {
                LoadData<Data> loadData = modelLoader.buildLoadData(model);
                if (loadData != null) {
                    sourceKey = loadData.sourceKey;
                    fetchers.add(loadData.fetcher);
                }
            }
        }
        return !fetchers.isEmpty() && sourceKey != null
                ? new LoadData<>(sourceKey, new MultiFetcher<>(fetchers)) : null;
    }

    @Override
    public boolean handles(Model model) {
        for (ModelLoader<Model, Data> modelLoader : modelLoaders) {
            if (modelLoader.handles(model)) {
                return true;
            }
        }
        return false;
    }


}
