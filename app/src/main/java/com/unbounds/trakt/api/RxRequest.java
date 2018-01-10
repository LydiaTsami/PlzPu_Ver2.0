package com.unbounds.trakt.api;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by maclir on 2015-11-08.
 */
public abstract class RxRequest {

    protected abstract HttpRequest request();

    public final <T> Observable<T> asObservable(final Class<T> ofClass) {
        return asObservable().map(new Func1<HttpResponse, T>() {
            @Override
            public T call(final HttpResponse httpResponse) {
                if (ofClass == String.class) return (T) httpResponse.getBody();
                else return httpResponse.getModel(ofClass);
            }
        });
    }

    public final Observable<Void> asVoidObservable() {
        return asObservable().map(new Func1<HttpResponse, Void>() {
            @Override
            public Void call(final HttpResponse httpResponse) {
                return null;
            }
        });
    }

    private Observable<HttpResponse> asObservable() {
        final HttpRequest httpRequest = request();

        return Observable.create(
                new Observable.OnSubscribe<HttpResponse>() {
                    @Override
                    public void call(final Subscriber<? super HttpResponse> subscriber) {
                        final Scheduler.Worker inner = Schedulers.io().createWorker();
                        subscriber.add(inner);
                        inner.schedule(new Action0() {
                            @Override
                            public void call() {
                                try {
                                    subscriber.onNext(httpRequest.execute());
                                    subscriber.onCompleted();

                                    subscriber.add(new Subscription() {
                                        @Override
                                        public void unsubscribe() {
                                            inner.schedule(new Action0() {
                                                @Override
                                                public void call() {
                                                    inner.unsubscribe();
                                                }
                                            });
                                        }

                                        @Override
                                        public boolean isUnsubscribed() {
                                            return subscriber.isUnsubscribed();
                                        }
                                    });
                                } catch (final HttpException e) {
                                    subscriber.onError(e);
                                }
                            }
                        });
                    }
                }
        ).observeOn(AndroidSchedulers.mainThread());
    }

}
