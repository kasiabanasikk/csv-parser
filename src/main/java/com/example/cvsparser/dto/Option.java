package com.example.cvsparser.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
public class Option implements Parsable{

    @Id
    Long id;
    @JsonProperty("code")
    String code;
    @JsonProperty("attribute")
    String attributeCode;
    @JsonProperty("sort_order")
    String sortOrder;

    @ManyToOne
    @JsonBackReference
    private Attribute attribute;

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
        this.attribute = attribute;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }
}
