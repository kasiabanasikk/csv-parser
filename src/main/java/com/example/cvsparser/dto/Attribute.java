package com.example.cvsparser.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
public class Attribute implements Parsable{

    @Id
    Long id;
    @JsonProperty("code")
    String code;

    @JsonProperty("label")
    @ElementCollection
    Map<String, String> labels = new HashMap<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "attribute")
    @JsonManagedReference
    List<Option> optionList;

    public Attribute() {
    }

    public Attribute(Long id, String code, Map<String, String> labels) {
        this.id = id;
        this.code = code;
        this.labels = labels;
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
