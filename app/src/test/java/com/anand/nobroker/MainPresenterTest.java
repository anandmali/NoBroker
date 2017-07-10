package com.anand.nobroker;

import com.anand.nobroker.model.pojo.NoBrokerApi;
import com.anand.nobroker.model.pojo.Properties;
import com.anand.nobroker.model.pojo.QueryValues;
import com.anand.nobroker.view.presenter.MainPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Observable.class, AndroidSchedulers.class})
public class MainPresenterTest {

    private MainPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = spy(new MainPresenter(mock(NoBrokerApi.class)));
    }

    @Test
    public void testShouldLoadProperties() {
        //create mocks
        Observable<Properties> postsObservable = (Observable<Properties>) mock(Observable.class);

        //define return values
        presenter.noBrokerApi = mock(NoBrokerApi.class);
        when(presenter.noBrokerApi.getPropertiesObservable(new QueryValues(null,null, null, null).getFilterQueryMap())).thenReturn(postsObservable);
        when(postsObservable.subscribeOn(Schedulers.io())).thenReturn(postsObservable);
        when(postsObservable.observeOn(AndroidSchedulers.mainThread())).thenReturn(postsObservable);

        //call test method
        presenter.loadProperties(new QueryValues(null, null, null, null));

        //verify if all methods in the chain are called with correct arguments
        verify(presenter.noBrokerApi).getPropertiesObservable(new QueryValues(null,null, null, null).getFilterQueryMap());
        verify(postsObservable).subscribeOn(Schedulers.io());
        verify(postsObservable).observeOn(AndroidSchedulers.mainThread());
        verify(postsObservable).subscribe(Matchers.<Subscriber<Properties>>any());
    }
}
