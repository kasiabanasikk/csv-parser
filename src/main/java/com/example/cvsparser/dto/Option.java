package com.example.cvsparser.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.HashMap;
import java.util.Map;

import static com.example.cvsparser.utils.Constants.Pattern.*;

@Slf4j
@Data
@NoArgsConstructor
@Entity
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @JsonProperty("code")
    String code;

    @JsonProperty("attribute")
    String attributeCode;

    @JsonProperty("sort_order")
    String sortOrder;
    @JsonProperty("label")
    @ElementCollection
    Map<String, String> labels = new HashMap<>();
    @ManyToOne
    @JsonBackReference
    private Attribute attributeObject;

    private Option(Long id, String code, String attributeCode, String sortOrder, Map<String,
            String> labels) {

        this.id = id;
        this.code = code;
        this.attributeCode = attributeCode;
        this.sortOrder = sortOrder;
        this.labels = labels;
    }

    public Option(Long id, String code, String attributeCode, String sortOrder,
                  Attribute attribute, Map<String, String> labels) {

        this(id, code, attributeCode, sortOrder, labels);
        this.attributeObject = attribute;
    }

    public Option(Map<String, String> values, Attribute attribute) {
        String key;
        String value;
        this.attributeObject = attribute;

        for (Map.Entry<String, String> entry : values.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();

            if (key.equals(CODE_PATTERN)) {
                this.code = value;
            } else if (key.equals(ATTRIBUTE_PATTERN)) {
                this.attributeCode = value;
            } else if (key.equals(SORT_ORDER_PATTERN)) {
                this.sortOrder = value;
            } else if (key.matches(LABEL_PATTERN)) {
                labels.put(key, value);
            } else {
                log.warn(String.format("Option header unknown: %s with value of: %s", key, value));
            }
        }
    }
}
