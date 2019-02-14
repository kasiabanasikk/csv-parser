package com.example.cvsparser.service;

import com.example.cvsparser.dto.Attribute;
import com.example.cvsparser.dto.AttributeRepository;
import com.example.cvsparser.dto.AttributeResponseObject;
import com.example.cvsparser.exceptions.AttributeNotFoundException;
import com.example.cvsparser.exceptions.LanguageNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service
public class AttributeService {

    @Autowired
    AttributeRepository attributeRepository;

    Logger logger = LoggerFactory.getLogger(AttributeService.class);


    public List<Attribute> getAllAttributes() {
        try {
            return attributeRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Collections.emptyList();
    }

    public AttributeResponseObject getAttributeByLang(String code, String lang) {
        List<String> sizeList = new ArrayList<>();
        String attributeName = null;
        Attribute attribute = attributeRepository.findByCode(code);
        if (attribute != null) {
            for (Map.Entry<String, String> entry : attribute.getLabels().entrySet()) {
                if (entry.getKey().contains(lang)) {
                    attributeName = attribute.getLabels().get(entry.getKey());
                }
            }
            if(attributeName == null){
                throw new LanguageNotFoundException(String.format("Language: %s  not found", lang));
            }

            attribute.getOptionList().forEach(o -> {
                o.getLabels().keySet().forEach(key -> {
                    if (key.contains(lang)) {
                        sizeList.add(o.getLabels().get(key));
                    }
                });
        });
            return new AttributeResponseObject(attributeName, sizeList);
        } else {
            throw new AttributeNotFoundException(String.format("Attribute with code: %s not found", code));
        }
    }
}
