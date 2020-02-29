package com.east.architect_zenghui.architect_22_eventbus;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 * @description: 手写EventBus的核心代码
 * @author: East
 * @date: 2020/2/29
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class EventBus {

    public static String TAG = "EventBus";
    static volatile EventBus defaultInstance;

    private static final Map<Class<?>, List<Class<?>>> eventTypesCache = new HashMap<>();
    // subscriptionsByEventType 这个集合存放的是？
    // key 是 Event 参数的类
    // value 存放的是 Subscription 的集合列表
    // Subscription 包含两个属性，一个是 subscriber 订阅者（反射执行对象），一个是 SubscriberMethod 注解方法的所有属性参数值
    private final Map<Class<?>, CopyOnWriteArrayList<Subscription>> subscriptionsByEventType;
    // typesBySubscriber 这个集合存放的是？
    // key 是所有的订阅者
    // value 是所有订阅者里面方法的参数的class
    private final Map<Object, List<Class<?>>> typesBySubscriber;

    public static EventBus getDefault() {
        if (defaultInstance == null) {
            synchronized (EventBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new EventBus();
                }
            }
        }
        return defaultInstance;
    }

    public EventBus() {
        typesBySubscriber = new HashMap<Object, List<Class<?>>>();
        subscriptionsByEventType = new HashMap<>();
    }

    public void register(Object object) {
        // 1.解析所有方法并封装成SubscriberMethod
        Class<?> objectClass = object.getClass();
        Method[] declaredMethods = objectClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            Subscribe annotation = method.getAnnotation(Subscribe.class);
            if(annotation!=null){
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) {
                    throw new RuntimeException("只允许传一个参数");
                }
                Class<?> eventType = parameterTypes[0];
                SubscriberMethod subscriberMethod = new SubscriberMethod(method, eventType,annotation.threadMode(),
                        annotation.priority(),annotation.sticky());

                //2.把SubscriberMethod封装到Subscription中并添加到subscriptionsByEventType中
                // 并把Object中的所有事件参数Class存储到typesBySubscriber中
                subscriber(object,subscriberMethod);
            }
        }
    }

    //2.把SubscriberMethod封装到Subscription中并添加到subscriptionsByEventType中
    // 并把Object中的所有事件参数Class存储到typesBySubscriber中
    private void subscriber(Object object, SubscriberMethod subscriberMethod) {
        Class<?> eventType = subscriberMethod.eventType;
        Subscription subscription = new Subscription(object,subscriberMethod);
        CopyOnWriteArrayList<Subscription> subscriptions = subscriptionsByEventType.get(eventType);
        if(subscriptions == null){
            subscriptions = new CopyOnWriteArrayList<>();
            subscriptionsByEventType.put(eventType,subscriptions);
        }

        //按照优先级添加到队列中
        int size = subscriptions.size();
        for (int i =0; i<=size;i++){
            if(i==size || subscriberMethod.priority > subscriptions.get(i).subscriberMethod.priority){
                subscriptions.add(i,subscription);
                break;
            }
        }

        List<Class<?>> classes = typesBySubscriber.get(object);
        if(classes == null){
            classes = new ArrayList<>();
            typesBySubscriber.put(object,classes);
        }

        if(!classes.contains(eventType)){
            classes.add(eventType);
        }
    }


    public void unregister(Object subscriber) {
        List<Class<?>> subscribedTypes = typesBySubscriber.get(subscriber);
        if(subscribedTypes != null){
            for (Class<?> eventType : subscribedTypes) {
                // 将订阅者的订阅信息移除
                unsubscribeByEventType(subscriber, eventType);
            }
            // 将订阅者从列表中移除
            typesBySubscriber.remove(subscriber);
        }else{
            Log.w(TAG, "Subscriber to unregister was not registered before: " + subscriber.getClass());
        }


    }

    private void unsubscribeByEventType(Object subscriber, Class<?> eventType) {
        CopyOnWriteArrayList<Subscription> subscriptions = subscriptionsByEventType.get(eventType);
        if(subscriptions!=null){
            int size = subscriptions.size();
            for(int i=0;i<size;i++){
                Subscription subscription = subscriptions.get(i);
                if(subscription.subscriber == subscriber){
                    // 将订阅信息激活状态置为FALSE
                    subscription.active = false;
                    // 将订阅信息从集合中移除
                    subscriptions.remove(i);
                    i--;
                    size--;
                }
            }
        }
    }


    public void post(Object event){
        // 遍历 subscriptionsByEventType，找到符合的方法调用方法的 method.invoke() 执行。要注意线程切换
        Class<?> eventType = event.getClass();
        // 找到符合的方法调用方法的 method.invoke() 执行
        CopyOnWriteArrayList<Subscription> subscriptions = subscriptionsByEventType.get(eventType);
        if(subscriptions!=null){
            for (Subscription subscription : subscriptions) {
                executeMethod(subscription,event);
            }
        }
    }

    private void executeMethod(Subscription subscription, Object event) {
        ThreadMode threadMode = subscription.subscriberMethod.threadMode;
        boolean isMainThread = Looper.getMainLooper() == Looper.myLooper();
        switch (threadMode){
            case POSTING:
                invokeMethod(subscription,event);
                break;
            case MAIN:
                if(isMainThread){
                    invokeMethod(subscription,event);
                }else{
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            invokeMethod(subscription, event);
                        }
                    });
                }
                break;
            case BACKGROUND:
                if(!isMainThread){
                    invokeMethod(subscription,event);
                }else {
                    AsyncPoster.enqueue(subscription,event);
                }
                break;
            case ASYNC:
                AsyncPoster.enqueue(subscription,event);
                break;
        }
    }

    private void invokeMethod(Subscription subscription, Object event) {
        try {
            subscription.subscriberMethod.method.invoke(subscription.subscriber,event);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
