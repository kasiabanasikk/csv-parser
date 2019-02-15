package com.example.cvsparser.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attribute {

    @Transient
    @JsonIgnore
    final Logger logger = LoggerFactory.getLogger(Attribute.class);

    @Transient
    @JsonIgnore
    private final String LABEL_PATTERN = "^label\\-.*";
    @Transient
    @JsonIgnore
    private final String CODE_PATTERN = "code";

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    @JsonProperty("code")
    String code;

    @JsonProperty("label")
    @ElementCollection
    Map<String, String> labels = new HashMap<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "attributeObject")
    @JsonManagedReference
    List<Option> optionList;

    public Attribute() {
    }

    public Attribute(Long id, String code, Map<String, String> labels) {
        this.id = id;
        this.code = code;
        this.labels = labels;
    }

    public Attribute(Map<String, String> values){
        String key;
        String value;
        for (Map.Entry<String,String> entry : values.entrySet()){
            key = entry.getKey();
            value = entry.getValue();
            if (key.equals(CODE_PATTERN)){
                this.code = value;
            }else if(key.matches(LABEL_PATTERN)){
                labels.put(key, value);
            }else{
                logger.warn(String.format("Attribute header unknown: %s with value of: %s", key, value));
            }
        }
    }
}
