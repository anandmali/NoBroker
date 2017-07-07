package com.anand.nobroker.events;

import retrofit2.adapter.rxjava.HttpException;

public class ErrorEvent {
    HttpException e;

    public ErrorEvent(HttpException e) {
        this.e = e;
    }

    public HttpException getE() {
        return e;
    }
}
