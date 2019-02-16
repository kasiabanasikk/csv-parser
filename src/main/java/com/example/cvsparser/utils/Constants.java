package com.example.cvsparser.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Pattern {
        public static final String LABEL_PATTERN = "^label-.*";
        public static final String CODE_PATTERN = "code";
        public static final String SORT_ORDER_PATTERN = "sort_order";
        public static final String ATTRIBUTE_PATTERN = "attribute";
    }
}
