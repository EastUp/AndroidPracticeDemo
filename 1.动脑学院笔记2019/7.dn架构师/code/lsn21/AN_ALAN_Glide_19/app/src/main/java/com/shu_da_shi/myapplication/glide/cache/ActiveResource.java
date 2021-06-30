package com.shu_da_shi.myapplication.glide.cache;

import com.shu_da_shi.myapplication.glide.cache.recycle.Resource;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 正在使用过的图片资源
 */
public class ActiveResource {
    private Map<Key, ResourceWeakReference> weakReferenceMap = new HashMap<>();
    private ReferenceQueue<Resource> queue;
    private Thread cleanReferenceQueueTherad;
    private final Resource.ResourceListener resourceListener;
    private boolean isShutdown;

    public ActiveResource(Resource.ResourceListener resourceListener) {
        this.resourceListener = resourceListener;
    }

    /**
     * 加入活动缓存
     *
     * @param key
     * @param resource
     */
    public void activete(Key key, Resource resource) {
        resource.setResourceListener(key, resourceListener);
        weakReferenceMap.put(key, new ResourceWeakReference(key, resource, getReferenceQueue()));
    }

    /**
     * 移除活动缓存
     *
     * @return
     */
    public Resource deactivete(Key key) {
        ResourceWeakReference reference = weakReferenceMap.remove(key);
        if (reference != null) {
            return reference.get();
        }
        return null;
    }


    private ReferenceQueue<? super Resource> getReferenceQueue() {
        if (null == queue) {
            queue = new ReferenceQueue<>();
            cleanReferenceQueueTherad = new Thread() {
                @Override
                public void run() {
                    while (!isShutdown) {
                        try {
                            //被回收掉的引用
                            ResourceWeakReference ref = (ResourceWeakReference) queue.remove();
                            weakReferenceMap.remove(ref.key);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            cleanReferenceQueueTherad.start();
        }
        return queue;
    }

    void shutdown() {
        isShutdown = true;
        if (cleanReferenceQueueTherad != null) {
            //强制关闭线程
            cleanReferenceQueueTherad.interrupt();
            try {
                cleanReferenceQueueTherad.join(TimeUnit.SECONDS.toMillis(5));
                if (cleanReferenceQueueTherad.isAlive()) {
                    throw new RuntimeException("Failed to join in time");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //弱引用
    static final class ResourceWeakReference extends WeakReference<Resource> {
        final Key key;

        public ResourceWeakReference(Key key, Resource referent, ReferenceQueue<? super Resource> q) {
            super(referent, q);
            this.key = key;
        }

    }

    public Resource get(Key key){
        ResourceWeakReference resourceWeakReference = weakReferenceMap.get(key);
        if(resourceWeakReference != null){
            return resourceWeakReference.get();
        }
        return null;
    }

}
