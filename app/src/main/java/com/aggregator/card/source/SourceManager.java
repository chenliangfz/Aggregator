package com.aggregator.card.source;


import com.aggregator.card.source.network.ApiService;

import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by ChenLiang on 16/10/12.
 */
@Singleton
public class SourceManager {


    private ApiService mApiService;

    public SourceManager(ApiService apiService) {
        mApiService = apiService;
    }

    /**
     * 转换器封装：
     * 1. 线程运行环境
     * 2. 重试
     *
     * @param <T>
     * @param <R>
     */
    private static class LifeTransformer<T, R> implements Observable.Transformer<T, R> {

        @Override
        public Observable<R> call(Observable<T> observable) {
            return (Observable<R>) observable
                    // 在io线程操作
                    .subscribeOn(Schedulers.io())
                    // 在main线程处理
                    .observeOn(AndroidSchedulers.mainThread())
                    // 异常重试
                    .retryWhen(createNotificationHandler());
        }

        private Func1<Observable<? extends Throwable>, Observable<?>> createNotificationHandler() {
            return new Func1<Observable<? extends Throwable>, Observable<?>>() {
                @Override
                public Observable<?> call(Observable<? extends Throwable> observable) {
                    return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                        @Override
                        public Observable<?> call(Throwable throwable) {
                            if (throwable instanceof UnknownHostException) {
                                return Observable.error(throwable);
                            }
                            return Observable.just(throwable)
                                    .zipWith(Observable.range(0, 4), new Func2<Throwable, Integer, Integer>() {
                                        @Override
                                        public Integer call(Throwable throwable, Integer i) {
                                            return i;
                                        }
                                    }).flatMap(new Func1<Integer, Observable<? extends Long>>() {
                                        @Override
                                        public Observable<? extends Long> call(Integer retryCount) {
                                            return Observable.timer((long) Math.pow(5, retryCount), TimeUnit.SECONDS);
                                        }
                                    });
                        }
                    });
                }
            };
        }
    }
}
