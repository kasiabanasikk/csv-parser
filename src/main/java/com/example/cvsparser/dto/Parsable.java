package com.example.cvsparser.dto;

import java.util.Map;

public interface Parsable {

    void setCode(String code);
    void setLabels(Map<String, String> labels);
}
