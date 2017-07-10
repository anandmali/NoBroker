package com.anand.nobroker.view.presenter;

import com.anand.nobroker.R;
import com.anand.nobroker.view.Constants;
import com.anand.nobroker.view.screen_contracts.FilterSelectionContracts;
import com.anand.nobroker.view.screen_contracts.FilterSelectionListener;

public class FilterPresenter {

    private static String type = null;
    private static String buildingType = null;
    private static String furnishType = null;

    public void filterSelections(FilterSelectionContracts contracts, int filterId) {
        switch (filterId) {
            case R.id.txtTwoBhk:
                contracts.onTwoBhkSelected();
                break;
            case R.id.txtThreeBhk:
                contracts.onThreeBhkSelected();
                break;
            case R.id.txtFourBhk:
                contracts.onFourBhkSelected();
                break;
            case R.id.txtTypeIndependent:
                contracts.onIndependentHouseSelected();
                break;
            case R.id.txtTypeFloor:
                contracts.onBuilderFloorSelected();
                break;
            case R.id.txtTypeApartment:
                contracts.onApartmentSelected();
                break;
            case R.id.txtFullFurnish:
                contracts.onFullFurnishSelected();
                break;
            case R.id.txtSemiFurnish:
                contracts.onSemiFurnishSelected();
                break;
        }
    }

    public void restoreFilterState(FilterSelectionContracts contracts, String filter) {
        switch (filter) {
            case Constants.TYPE_BHK2:
                contracts.onTwoBhkSelected();
                break;
            case Constants.TYPE_BHK3:
                contracts.onThreeBhkSelected();
                break;
            case Constants.TYPE_BHK4:
                contracts.onFourBhkSelected();
                break;
            case Constants.TYPE_BUILDING_IH:
                contracts.onIndependentHouseSelected();
                break;
            case Constants.TYPE_BUILDING_IF:
                contracts.onBuilderFloorSelected();
                break;
            case Constants.TYPE_BUILDING_AP:
                contracts.onApartmentSelected();
                break;
            case Constants.TYPE_FURNISH_FULL:
                contracts.onFullFurnishSelected();
                break;
            case Constants.TYPE_FURNISH_SEMI:
                contracts.onSemiFurnishSelected();
                break;
        }
    }

    public void filterApplied(FilterSelectionListener listener, String type, String buildingType, String furnishType) {
        FilterPresenter.type = type;
        FilterPresenter.buildingType = buildingType;
        FilterPresenter.furnishType = furnishType;
        listener.onFilterSelection(FilterPresenter.type, FilterPresenter.buildingType, FilterPresenter.furnishType);
    }

    public void refreshFilters(FilterSelectionContracts contracts) {
        contracts.onRefresh();
    }

    public void setFilterStates(FilterSelectionContracts contracts) {
        contracts.setFilterStates(type, buildingType, furnishType);
    }
}
