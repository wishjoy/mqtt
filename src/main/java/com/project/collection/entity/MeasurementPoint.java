package com.project.collection.entity;


public enum MeasurementPoint {
    VOLTAGE("ywzj35070001_U", "电压"),
    CURRENT("ywzj350700011", "电流"),
    ACTIVE_POWER("ywzj35070001 P", "总有功功率"),
    REACTIVE_POWER("ywzj35070001 Q", "总无功功率"),
    POWER_FACTOR("ywzj35070001_PF", "总功率因数"),
    TOTAL_ACTIVE_ENERGY("ywzj35070001_Ep", "组合有功总电能"),
    FORWARD_ACTIVE_ENERGY("ywzj35070001_lmpEp", "正向有功总电能"),
    REVERSE_ACTIVE_ENERGY("ywzj35070001_ExpEp", "反向有功总电量"),
    COMMUNICATION_LOSS("ywzj35070001_Discon", "通讯中断"),
    GRID_FREQUENCY("ywzj35070001_Freq", "电网频率"),
    SWITCH_CONTROL("ywzj35070001_D01 Ct", "写1合闻，写写0分闸"),
    RELAY_STATUS("ywzj35070001_Relay1", "0合 (通)，1分端");

    private final String pointCode;
    private final String description;

    MeasurementPoint(String pointCode, String description) {
        this.pointCode = pointCode;
        this.description = description;
    }

    public String getPointCode() {
        return pointCode;
    }

    public String getDescription() {
        return description;
    }
}
