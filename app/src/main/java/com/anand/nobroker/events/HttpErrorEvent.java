package com.anand.nobroker.events;

import retrofit2.adapter.rxjava.HttpException;

public class HttpErrorEvent {
    HttpException e;

    public HttpErrorEvent(HttpException e) {
        this.e = e;
    }

    public HttpException getE() {
        return e;
    }
}
