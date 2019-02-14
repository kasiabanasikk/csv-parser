package com.example.cvsparser.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AttributeResponseObject {

    @JsonProperty("attributeName")
    String attributeName;
    @JsonProperty("sizes")
    List<String> sizes;

    public AttributeResponseObject(String attributeName, List<String> sizes) {
        this.attributeName = attributeName;
        this.sizes = sizes;
    }
}
