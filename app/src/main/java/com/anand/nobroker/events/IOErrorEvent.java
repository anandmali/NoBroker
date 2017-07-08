package com.anand.nobroker.events;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;

public class IOErrorEvent {
    IOException e;

    public IOErrorEvent(IOException e) {
        this.e = e;
    }

    public IOException getE() {
        return e;
    }

}
