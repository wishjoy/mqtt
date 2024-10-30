package com.project.jnzk.models.collection.entity;


public enum DateType {
    HEART("heart","心跳"),
    REAL("real","数据上报"),
    OTHER("other","其他");
    private final String type;
    private final String info;
    DateType(String type, String info) {
        this.type = type;
        this.info = info;
    }
    public String getType() {
        return type;
    }

    public String getInfo(){
        return info;
    }

}
