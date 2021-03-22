package com.phonevalidator.utils.specification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCriteria {

    private String key;
    private Object value;
    private SearchKeys searchKeys;

    public SearchCriteria() {
    }

    public SearchCriteria(String key, Object value, SearchKeys searchKeys) {
        this.key = key;
        this.value = value;
        this.searchKeys = searchKeys;
    }
}
