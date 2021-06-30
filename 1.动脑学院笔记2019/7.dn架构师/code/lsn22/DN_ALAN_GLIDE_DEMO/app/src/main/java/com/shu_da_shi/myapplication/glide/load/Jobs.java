package com.shu_da_shi.myapplication.glide.load;

import com.shu_da_shi.myapplication.glide.cache.Key;

import java.util.HashMap;
import java.util.Map;

final class Jobs {
    private final Map<Key, EngineJob> jobs = new HashMap<>();

    EngineJob get(Key key) {
        return jobs.get(key);
    }

    void put(Key key, EngineJob job) {
        jobs.put(key, job);
    }

    void removeIfCurrent(Key key, EngineJob expected) {
        if (expected.equals(jobs.get(key))) {
            jobs.remove(key);
        }
    }


}
