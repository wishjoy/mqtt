package com.project.jnzk.models.collection.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Heart {

    private String sn;

    private Date time;
    private String iccid;

    private String type;

    private int csq;
}