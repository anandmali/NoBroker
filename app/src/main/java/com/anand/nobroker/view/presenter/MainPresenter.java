package com.anand.nobroker.view.presenter;

import android.util.Log;

import com.anand.nobroker.events.ErrorEvent;
import com.anand.nobroker.events.PropertiesEvent;
import com.anand.nobroker.model.pojo.NoBrokerApi;
import com.anand.nobroker.model.pojo.Properties;
import com.anand.nobroker.model.pojo.QueryValues;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter {

//    private NoBrokerApi noBrokerApi;
//
//    @Inject
//    public MainPresenter(NoBrokerApi noBrokerApi) {
//        this.noBrokerApi = noBrokerApi;
//    }

    public void loadProperties(boolean b, QueryValues queryValues) {
//        QueryValues queryValues = new QueryValues();
        Log.e("Map", queryValues.getCommonQueryMap().size()+"");
        Log.e("Map", queryValues.getCommonQueryMap().get(QueryValues.LAT_LANG)+"");
        NoBrokerApi noBrokerApi = new NoBrokerApi();
//        noBrokerApi.setQueryData(queryValues.getCommonQueryMap());
        noBrokerApi.getPropertiesObservable(getQueryMap(b, queryValues))
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
                            EventBus.getDefault().post(new ErrorEvent(httpException));
                        }
                        else if (e instanceof IOException){
                            IOException in = (IOException) e;
                            Log.e("Error", "Non api error " + in.getMessage());
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

    private HashMap<String, String> getQueryMap(boolean b, QueryValues queryValues) {
        if (b)
            return queryValues.getCommonQueryMap();
        else
            return queryValues.getFilterQueryMap();
    }
}
