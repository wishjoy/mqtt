package com.project.jnzk.models.collection.entity;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class MeterEnergyDayStatistics {
    public String meter;
    public BigDecimal value;
}
