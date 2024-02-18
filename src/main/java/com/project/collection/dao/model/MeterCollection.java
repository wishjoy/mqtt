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
 * <p>
 * 电表数据采集表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("meter_collection")
@ApiModel(value="MeterCollection对象", description="电表数据采集表")
public class MeterCollection implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;
    @ApiModelProperty(value = "数据传输类型，real 全部、change 变化、his 断线续传")
    private String type;

    @ApiModelProperty(value = "网关序列号")
    private String gatewaySn;

    @ApiModelProperty(value = "网关上报时间")
    private Instant gatewayTime;

    @ApiModelProperty(value = "电表序列号")
    private String meterSn;

    @ApiModelProperty(value = "测点ID")
    private String pointId;

    @ApiModelProperty(value = "测点质量")
    private Integer pointQuality;

    @ApiModelProperty(value = "测点数值")
    private BigDecimal pointValue;
}




