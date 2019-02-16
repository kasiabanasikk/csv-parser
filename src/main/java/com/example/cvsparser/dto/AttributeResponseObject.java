package com.example.cvsparser.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttributeResponseObject {

    @JsonProperty("attributeName")
    String attributeName;

    @JsonProperty("sizes")
    List<String> sizes;
}
