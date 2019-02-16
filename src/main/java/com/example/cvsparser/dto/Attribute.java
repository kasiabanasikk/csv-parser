package com.example.cvsparser.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.cvsparser.utils.Constants.Pattern.CODE_PATTERN;
import static com.example.cvsparser.utils.Constants.Pattern.LABEL_PATTERN;

@Slf4j
@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @JsonProperty("code")
    String code;

    @JsonProperty("label")
    @ElementCollection
    Map<String, String> labels = new HashMap<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "attributeObject")
    @JsonManagedReference
    List<Option> optionList;

    public Attribute(Long id, String code, Map<String, String> labels) {
        this.id = id;
        this.code = code;
        this.labels = labels;
    }

    public Attribute(Map<String, String> values) {
        String key;
        String value;

        for (Map.Entry<String, String> entry : values.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();

            if (key.equals(CODE_PATTERN)) {
                this.code = value;
            } else if (key.matches(LABEL_PATTERN)) {
                labels.put(key, value);
            } else {
                log.warn(String.format("Attribute header unknown: %s with value of: %s", key,
                        value));
            }
        }
    }
}
