package com.example.cvsparser.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
public class Option {

    @Transient
    @JsonIgnore
    final Logger logger = LoggerFactory.getLogger(Option.class);
    @Transient
    @JsonIgnore
    private static final String LABEL_PATTERN = "^label\\-.*";
    @Transient
    @JsonIgnore
    private static final String CODE_PATTERN = "code";
    @Transient
    @JsonIgnore
    private static final String SORT_ORDER_PATTERN = "sort_order";
    @Transient
    @JsonIgnore
    private static final String ATTRIBUTE_PATTERN = "attribute";

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    @JsonProperty("code")
    String code;
    @JsonProperty("attribute")
    String attributeCode;
    @JsonProperty("sort_order")
    String sortOrder;

    @ManyToOne
    @JsonBackReference
//    @JsonIgnore
    private Attribute attributeObject;

    @JsonProperty("label")
    @ElementCollection
    Map<String, String> labels = new HashMap<>();

    public Option() {
    }

    public Option(Long id, String code, String attributeCode, String sortOrder, Map<String, String> labels) {
        this.id = id;
        this.code = code;
        this.attributeCode = attributeCode;
        this.sortOrder = sortOrder;
        this.labels = labels;
    }

    public Option(Long id, String code, String attributeCode, String sortOrder, Attribute attribute, Map<String, String> labels) {
        this(id, code, attributeCode, sortOrder, labels);
        this.attributeObject = attribute;
    }

    public Option(Map<String, String> values, Attribute attribute){
        String key;
        String value;
        this.attributeObject = attribute;
        for (Map.Entry<String,String> entry : values.entrySet()){
            key = entry.getKey();
            value = entry.getValue();
            if (key.equals(CODE_PATTERN)){
                this.code = value;
            } else if (key.equals(ATTRIBUTE_PATTERN)){
                this.attributeCode = value;
            } else if (key.equals(SORT_ORDER_PATTERN)){
                this.sortOrder = value;
            } else if(key.matches(LABEL_PATTERN)){
                labels.put(key, value);
            } else{
                logger.warn(String.format("Option header unknown: %s with value of: %s", key, value));
            }
        }
    }
}
