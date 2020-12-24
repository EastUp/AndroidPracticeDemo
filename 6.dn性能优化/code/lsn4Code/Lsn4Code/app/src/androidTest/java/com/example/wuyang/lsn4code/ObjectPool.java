package com.example.wuyang.lsn4code;

import android.util.SparseArray;

public abstract class ObjectPool<T> {
    private SparseArray<T> freePool;
    private SparseArray<T> lentPool;
    //沲的最大值
    private int maxCapacity;

    public ObjectPool(int initialCapacity, int maxCapacity) {
        //初始化对象沲
        initalize(initialCapacity);
        this.maxCapacity=maxCapacity;
    }

    private void initalize(int initialCapacity) {
        lentPool=new SparseArray<>();
        freePool=new SparseArray<>();
        for(int i=0;i<initialCapacity;i++){
            freePool.put(i,create());
        }
    }

    /**
     * 申请对象
     * @return
     */
    public T acquire(){
        T t=null;
        synchronized (freePool){
            int freeSize=freePool.size();
            for(int i=0;i<freeSize;i++){
                int key=freePool.keyAt(i);
                t=freePool.get(key);
                if(t!=null){
                    this.lentPool.put(key,t);
                    this.freePool.remove(key);
                    return t;
                }
            }
            //如果没对象可取了
            if(t==null && lentPool.size()+freeSize<maxCapacity){
                t=create();
                lentPool.put(lentPool.size()+freeSize,t);
            }
        }
        return t;
    }

    /**
     * 回收对象
     * @return
     */
    public void release(T t){
        if(t==null){
            return;
        }
        int key=lentPool.indexOfValue(t);
        //释放前可以把这个对象交给用户处理
        restore(t);

        this.freePool.put(key,t);
        this.lentPool.remove(key);

    }

    protected  void restore(T t){

    };


    protected abstract T create();

    public ObjectPool(int maxCapacity) {
        this(maxCapacity/2,maxCapacity);
    }

}
