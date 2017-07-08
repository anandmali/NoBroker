package com.anand.nobroker.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.anand.nobroker.R;
import com.anand.nobroker.events.ErrorEvent;
import com.anand.nobroker.events.PropertiesEvent;
import com.anand.nobroker.model.pojo.QueryValues;
import com.anand.nobroker.view.PaginationScrollListener;
import com.anand.nobroker.view.adapter.PropertiesAdapter;
import com.anand.nobroker.view.presenter.MainPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private PropertiesAdapter adapter;
    private int pageCount = 1;
    private MainPresenter mainPresenter = new MainPresenter();
    private Boolean isLoading = false;
    private boolean isLastPage = false;
    private long total = 21;
    private long fetched = 0;

    //@Inject
    //MainPresenter mainPresenter;

    @BindView(R.id.list)
    RecyclerView propertiesList;
    @BindView(R.id.txtLoading)
    TextView txtLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind butter
        ButterKnife.bind(this);

        //Dagger
        //DaggerInjector.get().inject(this);

        adapter = new PropertiesAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        propertiesList.setLayoutManager(layoutManager);
        propertiesList.setAdapter(adapter);

        propertiesList.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                pageCount += 1; //Increment page index to load the next one
                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return 0;
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        QueryValues queryValues = new QueryValues();

        mainPresenter.loadProperties(true, queryValues);

    }

    private void loadNextPage() {
        QueryValues queryValues = new QueryValues("2", "BHK3", "AP", "FULLY_FURNISHED");
        mainPresenter.loadProperties(true, queryValues);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode =  ThreadMode.MAIN)
    public void onEventMainThread(PropertiesEvent properties) {
        txtLoading.setVisibility(View.GONE);
        adapter.addPosts(properties.getProperteies().getData());
        total = properties.getProperteies().getOtherParams().getTotalCount();
        fetched = fetched + properties.getProperteies().getData().size();
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ErrorEvent errorEvent) {
        Log.e("Error main", errorEvent.getE().code()+"");
    }
}
