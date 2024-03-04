package com.project.jnzk.models.collection.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
@Data
public class MeterReport {
    public String type;
    public String sn;
    public String time;
    public List<HashMap<String,Point>> data;

}
