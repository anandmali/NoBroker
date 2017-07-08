package com.anand.nobroker.view.presenter;

import com.anand.nobroker.R;
import com.anand.nobroker.view.Constants;
import com.anand.nobroker.view.screen_contracts.FilterSelectionContracts;
import com.anand.nobroker.view.screen_contracts.FilterSelectionListener;

public class FilterPresenter {

    private String type = null;
    private String buildingType = null;
    private String furnishType = null;

    public void filterSelections(FilterSelectionContracts contracts, int filterId) {
        switch (filterId) {
            case R.id.txtTwoBhk:
                type = Constants.TYPE_BHK2;
                contracts.onTwoBhkSelected();
                break;
            case R.id.txtThreeBhk:
                type = Constants.TYPE_BHK3;
                contracts.onThreeBhkSelected();
                break;
            case R.id.txtFourBhk:
                type = Constants.TYPE_BHK4;
                contracts.onFourBhkSelected();
                break;
            case R.id.txtTypeIndependent:
                buildingType = Constants.TYPE_BUILDING_IH;
                contracts.onIndependentHouseSelected();
                break;
            case R.id.txtTypeFloor:
                buildingType = Constants.TYPE_BUILDING_IF;
                contracts.onBuilderFloorSelected();
                break;
            case R.id.txtTypeApartment:
                buildingType = Constants.TYPE_BUILDING_AP;
                contracts.onApartmentSelected();
                break;
            case R.id.txtFullFurnish:
                furnishType = Constants.TYPE_FURNISH_FULL;
                contracts.onFullFurnishSelected();
                break;
            case R.id.txtSemiFurnish:
                furnishType = Constants.TYPE_FURNISH_SEMI;
                contracts.onSemiFurnishSelected();
                break;
        }
    }

    public void filterApplied(FilterSelectionListener listener) {
        listener.onFilterSelection(type, buildingType, furnishType);
    }

    public void refreshFilters(FilterSelectionContracts contracts) {
        type = null;
        buildingType = null;
        furnishType = null;
        contracts.onRefresh();
    }
}
