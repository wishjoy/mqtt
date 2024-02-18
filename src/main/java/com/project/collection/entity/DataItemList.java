package com.project.collection.entity;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class DataItemList {
    private List<DataItem>data;
    @Data
    public static class DataItem {
        private String id;
        private String quality;
        private String value;
    }
}
