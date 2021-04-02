package com.dn_alan.myapplication;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        assertEquals(4, 2 + 2);
    }

    @Test
    public void test1() {
        /**
         * Observable --- 被观察者
         * create  ---操作符
         * ObservableEmitter  ---  发射器向观察者发送事件
         */
        Observable<String> objectObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Observable");
                emitter.onComplete();

            }
        });

        // Flowable被观察者（背压）的创建
        Flowable<Object> objectFlowable = Flowable.create(new FlowableOnSubscribe<Object>() {

            @Override
            public void subscribe(FlowableEmitter<Object> emitter) throws Exception {

            }
        }, BackpressureStrategy.BUFFER);

        //Single 被观察者
        Single.create(new SingleOnSubscribe<Object>() {

            @Override
            public void subscribe(SingleEmitter<Object> emitter) throws Exception {
            }
        }).subscribe(new SingleObserver<Object>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });

        //Completable 被观察者
        Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {

            }
        });

        //Maybe 被观察者
        Maybe.create(new MaybeOnSubscribe<Object>() {

            @Override
            public void subscribe(MaybeEmitter<Object> emitter) throws Exception {
            }
        });


        // 观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe====" + d.toString());
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext====" + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete====");
            }
        };
        //订阅
        objectObservable.subscribe(observer);


        Observable.just(1, 2, 3).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object integer) {
                System.out.println("just===" + integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    /**
     * 转换操作符
     */
    @Test
    public void text2() {

    }

    ;

    /**
     * 组合操作符
     */
    @Test
    public void text3() {
        Observable.concat(Observable.just(1, 2),
                Observable.just(5, 6),
                Observable.just(3, 4),
                Observable.just(7, 8))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println(integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    ;

    /**
     * 功能操作符
     */
    @Test
    public void text4() {
        Observable.just(1, 2, 3)
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe()");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println(integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 过滤操作符
     */
    @Test
    public void text5() {
        Observable.just(1, 2, 3)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer < 3;
                    }
                }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 条件操作符
     */
    @Test
    public void text6() {
        Observable.just(1,2,3,4)
                .all(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer < 4;
                    }
                }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                System.out.println("accept()===" + aBoolean);
            }
        });
    }
}