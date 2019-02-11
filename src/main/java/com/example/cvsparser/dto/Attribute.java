package com.example.cvsparser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Attribute {

    @Id
    Long id;
    @JsonProperty("code")
    String code;
    @JsonProperty("label")
    @ElementCollection
    Map<String, String> labels = new HashMap<>();

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "attributeEntity")
//    @JsonManagedReference
//    List<OptionEntity> optionEntityList;

    public Attribute() {
    }

    public Attribute(Long id, String code, Map<String, String> labels) {
        this.id = id;
        this.code = code;
        this.labels = labels;
    }

    public Long getId() {
        return id;
    }

    public Map<String, String> getLabels() {
        return labels;
    }


    public String getCode() {
        return code;
    }

}
