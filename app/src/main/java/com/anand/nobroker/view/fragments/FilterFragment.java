package com.anand.nobroker.view.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anand.nobroker.R;
import com.anand.nobroker.view.Constants;
import com.anand.nobroker.view.decorator.FilterText;
import com.anand.nobroker.view.presenter.FilterPresenter;
import com.anand.nobroker.view.screen_contracts.FilterSelectionContracts;
import com.anand.nobroker.view.screen_contracts.FilterSelectionListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterFragment extends DialogFragment implements FilterSelectionContracts {

    private Dialog dialog;
    private FilterSelectionListener listener;
    private FilterPresenter presenter = new FilterPresenter();
    private String type = null;
    private String buildingType = null;
    private String furnishType = null;
    private static final String ARG_IS_FILTER_APPLIED = "ARG_FILTER_APPLIED";

    @BindView(R.id.btnFilter) Button btnFilter;
    @BindView(R.id.txtTwoBhk) FilterText txtTwoBhk;
    @BindView(R.id.txtThreeBhk) FilterText txtThreeBhk;
    @BindView(R.id.txtFourBhk) FilterText txtFourBhk;
    @BindView(R.id.txtTypeApartment) FilterText txtTypeApartment;
    @BindView(R.id.txtTypeFloor) FilterText txtTypeFloor;
    @BindView(R.id.txtTypeIndependent) FilterText txtTypeIndependent;
    @BindView(R.id.txtFullFurnish) FilterText txtFullFurnish;
    @BindView(R.id.txtSemiFurnish) FilterText txtSemiFurnish;

    public static FilterFragment newInstance(Boolean isFilterApplied) {
        FilterFragment fragment = new FilterFragment();
        Bundle arg = new Bundle();
        arg.putBoolean(ARG_IS_FILTER_APPLIED, isFilterApplied);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            try {
                listener = (FilterSelectionListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() +
                        " must implement the " + FilterFragment.class.getSimpleName());
            }
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        //Create full screen root view
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        // creating the fullscreen dialog
        dialog = new Dialog(getActivity());

        assert dialog.getWindow() != null;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(root);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        if (Build.VERSION.SDK_INT < 20)
            dialog.getWindow().getDecorView().setSystemUiVisibility(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    public Dialog getDialog() {
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.filter_fragment, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments().getBoolean(ARG_IS_FILTER_APPLIED))
            presenter.setFilterStates(this);
    }

    @OnClick({R.id.txtTwoBhk, R.id.txtThreeBhk, R.id.txtFourBhk, R.id.txtTypeIndependent, R.id.txtTypeFloor, R.id.txtTypeApartment, R.id.txtFullFurnish, R.id.txtSemiFurnish})
    public void filterClicked(TextView textView) {
        int id = textView.getId();
        presenter.filterSelections(this, id);
    }
    
    @OnClick({R.id.imgRefresh, R.id.imgClose})
    public void filterMenuOptions(ImageView imageView){
        if (imageView.getId() == R.id.imgRefresh)
            presenter.refreshFilters(this);
        if (imageView.getId() == R.id.imgClose)
            dismissDialog();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnFilter)
    public void filterSelected(Button button) {
        if (type == null && buildingType == null && furnishType == null) {
            Toast.makeText(getActivity(), "No filters are selected", Toast.LENGTH_LONG).show();
        }
        presenter.filterApplied(listener, type, buildingType, furnishType);
        dismissDialog();
    }

    @Override
    public void onTwoBhkSelected() {
        type = Constants.TYPE_BHK2;
        txtTwoBhk.selected();
        txtThreeBhk.unSelected();
        txtFourBhk.unSelected();
    }

    @Override
    public void onThreeBhkSelected() {
        type = Constants.TYPE_BHK3;
        txtTwoBhk.unSelected();
        txtThreeBhk.selected();
        txtFourBhk.unSelected();
    }

    @Override
    public void onFourBhkSelected() {
        type = Constants.TYPE_BHK4;
        txtTwoBhk.unSelected();
        txtThreeBhk.unSelected();
        txtFourBhk.selected();
    }

    @Override
    public void onApartmentSelected() {
        buildingType = Constants.TYPE_BUILDING_AP;
        txtTypeIndependent.unSelected();
        txtTypeApartment.selected();
        txtTypeFloor.unSelected();
    }

    @Override
    public void onIndependentHouseSelected() {
        buildingType = Constants.TYPE_BUILDING_IH;
        txtTypeIndependent.selected();
        txtTypeApartment.unSelected();
        txtTypeFloor.unSelected();
    }

    @Override
    public void onBuilderFloorSelected() {
        buildingType = Constants.TYPE_BUILDING_IF;
        txtTypeIndependent.unSelected();
        txtTypeApartment.unSelected();
        txtTypeFloor.selected();
    }

    @Override
    public void onFullFurnishSelected() {
        furnishType = Constants.TYPE_FURNISH_FULL;
        txtFullFurnish.selected();
        txtSemiFurnish.unSelected();
    }

    @Override
    public void onSemiFurnishSelected() {
        furnishType = Constants.TYPE_FURNISH_SEMI;
        txtFullFurnish.unSelected();
        txtSemiFurnish.selected();
    }

    @Override
    public void onRefresh() {
        type = null;
        buildingType = null;
        furnishType = null;
        txtTwoBhk.unSelected();
        txtThreeBhk.unSelected();
        txtFourBhk.unSelected();
        txtTwoBhk.unSelected();
        txtThreeBhk.unSelected();
        txtFourBhk.unSelected();
        txtTwoBhk.unSelected();
        txtThreeBhk.unSelected();
        txtFourBhk.unSelected();
        txtTypeIndependent.unSelected();
        txtTypeApartment.unSelected();
        txtTypeFloor.unSelected();
        txtTypeIndependent.unSelected();
        txtTypeApartment.unSelected();
        txtTypeFloor.unSelected();
        txtTypeIndependent.unSelected();
        txtTypeApartment.unSelected();
        txtTypeFloor.unSelected();
        txtFullFurnish.unSelected();
        txtSemiFurnish.unSelected();
        txtFullFurnish.unSelected();
        txtSemiFurnish.unSelected();
    }

    @Override
    public void setFilterStates(String type, String buildingType, String furnishType) {
        if (type != null)
            presenter.restoreFilterState(this, type);
        if (buildingType != null)
            presenter.restoreFilterState(this, buildingType);
        if (furnishType != null)
            presenter.restoreFilterState(this, furnishType);
    }

    public void dismissDialog() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }
}
