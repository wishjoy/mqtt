package com.project.jnzk.models.collection.entity;

import lombok.Data;
import java.util.List;

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
