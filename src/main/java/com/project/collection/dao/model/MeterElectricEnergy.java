package com.project.collection.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

/**
 电表历史功率表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("meter_electric_energy")
@ApiModel(value="MeterElectricEnergy对象", description="电表历史功率表")
public class MeterElectricEnergy implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "源数据id")
    private String collectionId;

    @ApiModelProperty(value = "网关序列号")
    private String gatewaySn;

    @ApiModelProperty(value = "网关上报时间")
    private Instant gatewayTime;

    @ApiModelProperty(value = "电表序列号")
    private String meterSn;

    @ApiModelProperty(value = "测点数值")
    private BigDecimal value;

    @ApiModelProperty(value = "增加数值")
    private BigDecimal upValue;

}
