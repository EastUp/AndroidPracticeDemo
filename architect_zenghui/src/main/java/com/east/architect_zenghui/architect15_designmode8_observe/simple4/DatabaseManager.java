package com.east.architect_zenghui.architect15_designmode8_observe.simple4;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 数据库的更新
 *  @author: East
 *  @date: 2020-02-22
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class DatabaseManager {

    private Observable<Member,Observer<Member>> mObservable = new Observable<>();

    private volatile static DatabaseManager mInstance;

    public static DatabaseManager getInstance(){
        if(mInstance == null){
            synchronized (DatabaseManager.class){
                if(mInstance == null)
                    mInstance = new DatabaseManager();
            }
        }
        return mInstance;
    }

    public void insert(Member member){

        // 插入数据库,省略了....

        //通知观察者
        mObservable.notifyObservers(member);
    }

    public void register(Observer<Member> observer){
        mObservable.register(observer);
    }

    public void unregister(Observer<Member> observer){
        mObservable.unregister(observer);
    }


}
