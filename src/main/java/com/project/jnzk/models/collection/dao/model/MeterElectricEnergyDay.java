package com.project.jnzk.models.collection.dao.model;

import java.math.BigDecimal;
import java.time.Instant;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用电量按日统计(MeterElectricEnergyDay)表实体类
 *
 * @author makejava
 * @since 2024-01-15 11:50:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "meter_electric_energy_day")
@ApiModel(value = "MeterElectricEnergyDay对象", description = "用电量按日统计")
public class MeterElectricEnergyDay implements Serializable {
    @ApiModelProperty(value = "记录唯一标识")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    public String id;
    @ApiModelProperty(value = "电表序列号")
    public String meterSn;
    @ApiModelProperty(value = "测点数值")
    public BigDecimal value;
    @ApiModelProperty(value = "统计日期")
    public String date;
    @ApiModelProperty(value = "入库时间")
    public Instant insertTime;
}

