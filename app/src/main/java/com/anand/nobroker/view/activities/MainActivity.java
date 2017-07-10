package com.anand.nobroker.view.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anand.nobroker.R;
import com.anand.nobroker.dagger.DaggerInjector;
import com.anand.nobroker.events.ErrorEvent;
import com.anand.nobroker.events.HttpErrorEvent;
import com.anand.nobroker.events.IOErrorEvent;
import com.anand.nobroker.events.PropertiesEvent;
import com.anand.nobroker.model.pojo.QueryValues;
import com.anand.nobroker.view.PaginationScrollListener;
import com.anand.nobroker.view.adapter.PropertiesAdapter;
import com.anand.nobroker.view.fragments.FilterFragment;
import com.anand.nobroker.view.presenter.MainPresenter;
import com.anand.nobroker.view.screen_contracts.FilterSelectionListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements FilterSelectionListener {

    private PropertiesAdapter adapter;
    private int pageCount = 1;
    private Boolean isLoading = false;
    private long totalProperties = 0;
    private long fetchedProperties = 0;
    private String type = null;
    private String buildingType = null;
    private String furnishType = null;
    private Boolean isFilterApplied = false;

    @Inject
    MainPresenter mainPresenter;

    @BindView(R.id.list) RecyclerView propertiesList;
    @BindView(R.id.txtLoading) TextView txtLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind butter
        ButterKnife.bind(this);

        //Dagger
        DaggerInjector.get().inject(this);

        adapter = new PropertiesAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        propertiesList.setLayoutManager(layoutManager);
        propertiesList.setAdapter(adapter);

        propertiesList.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                if (!isLoading() && fetchedProperties < totalProperties) {
                    isLoading = true;
                    pageCount += 1;
                    loadProperties();
                }
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
        loadProperties();
    }

    private void loadProperties() {
        QueryValues queryValues = new QueryValues(pageCount, type, buildingType, furnishType);
        mainPresenter.loadProperties(queryValues);
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
        loadingCompleteSetup();
        if (properties.getProperteies().getData().size() > 0) {
            adapter.addPosts(properties.getProperteies().getData());
            totalProperties = properties.getProperteies().getOtherParams().getTotalCount();
            fetchedProperties = fetchedProperties + properties.getProperteies().getData().size();
        } else {
            Toast.makeText(this, "No properties found", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HttpErrorEvent httpErrorEvent) {
        loadingCompleteSetup();
        Toast.makeText(this, "Check network connectivity", Toast.LENGTH_LONG).show();
        Log.e("Error main", httpErrorEvent.getE().code()+"");
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(IOErrorEvent errorEvent) {
        loadingCompleteSetup();
        Toast.makeText(this, "Wrong values selected", Toast.LENGTH_LONG).show();
        Log.e("Error main", errorEvent.getE()+"");
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ErrorEvent errorEvent) {
        loadingCompleteSetup();
        Toast.makeText(this, "Could not make the request", Toast.LENGTH_LONG).show();
        Log.e("Error main", "Something went wrong");
    }

    @Override
    public void onFilterSelection(String type, String buildingType, String furnishType) {
        isFilterApplied = true;
        this.type = type;
        this.buildingType = buildingType;
        this.furnishType = furnishType;
        loadProperties();
        adapter.clearList();
        txtLoading.setVisibility(View.VISIBLE);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnFilter)
    public void openFilter(FloatingActionButton floatingActionButton) {
        FragmentManager manager = getSupportFragmentManager();
        FilterFragment fragment =
                FilterFragment.newInstance(isFilterApplied);
        fragment.show(manager, FilterFragment.class.getSimpleName());
    }

    private void loadingCompleteSetup() {
        txtLoading.setVisibility(View.GONE);
        isLoading = false;
    }
}
