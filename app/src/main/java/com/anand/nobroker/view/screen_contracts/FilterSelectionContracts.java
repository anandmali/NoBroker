package com.anand.nobroker.view.screen_contracts;

public interface FilterSelectionContracts {
    void onTwoBhkSelected();
    void onThreeBhkSelected();
    void onFourBhkSelected();
    void onApartmentSelected();
    void onIndependentHouseSelected();
    void onBuilderFloorSelected();
    void onFullFurnishSelected();
    void onSemiFurnishSelected();
    void onRefresh();
    void setFilterStates(String type, String buildingType, String furnishType);
}
