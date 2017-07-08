package com.anand.nobroker.view.presenter;

import android.util.Log;

import com.anand.nobroker.events.HttpErrorEvent;
import com.anand.nobroker.events.IOErrorEvent;
import com.anand.nobroker.events.PropertiesEvent;
import com.anand.nobroker.model.pojo.NoBrokerApi;
import com.anand.nobroker.model.pojo.Properties;
import com.anand.nobroker.model.pojo.QueryValues;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter {

    public void loadProperties(boolean b, QueryValues queryValues) {
        NoBrokerApi noBrokerApi = new NoBrokerApi();
        noBrokerApi.getPropertiesObservable(queryValues.getFilterQueryMap())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Properties>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            EventBus.getDefault().post(new HttpErrorEvent(httpException));
                        }
                        else if (e instanceof IOException){
                            IOException in = (IOException) e;
                            EventBus.getDefault().post(new IOErrorEvent(in));
                        } else {
                            Log.e("Error", "Non api error " + e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(Properties properties) {
                        EventBus.getDefault().post(new PropertiesEvent(properties));
                    }
                });
    }
}
