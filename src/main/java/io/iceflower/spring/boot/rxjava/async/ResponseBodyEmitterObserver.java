/**
 * Copyright (c) 2015-2016 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.iceflower.spring.boot.rxjava.async;


import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;


/**
 * Subscriber that any value produced by the {@link Observable} or {@link Flowable} into the {@link ResponseBodyEmitter}.
 *
 * @author Jakub Narloch
 * @author 김영근
 */
class ResponseBodyEmitterObserver<T> extends DisposableObserver<T> implements Runnable {

    private final MediaType mediaType;

    private final ResponseBodyEmitter responseBodyEmitter;

    private boolean completed;

    public ResponseBodyEmitterObserver(MediaType mediaType, Observable<T> observable, ResponseBodyEmitter responseBodyEmitter) {

        this.mediaType = mediaType;
        this.responseBodyEmitter = responseBodyEmitter;
        this.responseBodyEmitter.onTimeout(this);
        this.responseBodyEmitter.onCompletion(this);
        observable.subscribe(this);
    }

    public ResponseBodyEmitterObserver(MediaType mediaType, Flowable<T> flowable, ResponseBodyEmitter responseBodyEmitter) {

        this.mediaType = mediaType;
        this.responseBodyEmitter = responseBodyEmitter;
        this.responseBodyEmitter.onTimeout(this);
        this.responseBodyEmitter.onCompletion(this);
        flowable.toObservable()
            .subscribe(this);
    }

    @Override
    public void onNext(T value) {

        try {
            if(!completed) {
                responseBodyEmitter.send(value, mediaType);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void onError(Throwable e) {
        responseBodyEmitter.completeWithError(e);
    }

    @Override
    public void onComplete() {
        if(!completed) {
            completed = true;
            responseBodyEmitter.complete();
        }
    }

    @Override
    public void run() {
        this.dispose();
    }
}
