package com.mredrock.cyxbs.summer.utils

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.setSchedulers(
        subscribeOn: Scheduler = Schedulers.io(),
        unsubscribeOn: Scheduler = Schedulers.io(),
        observeOn: Scheduler = AndroidSchedulers.mainThread()
): Observable<T> = subscribeOn(subscribeOn).unsubscribeOn(unsubscribeOn).observeOn(observeOn)

fun <T> Observable<T>.safeSubscribe(
        onError: (Throwable) -> Unit,
        onComplete: () -> Unit,
        onNext: (T) -> Unit
): Disposable = subscribe(onNext,onError,onComplete)
