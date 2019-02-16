package com.example.cvsparser.service.impl;

import com.example.cvsparser.dto.Attribute;
import com.example.cvsparser.dto.AttributeRepository;
import com.example.cvsparser.dto.AttributeResponseObject;
import com.example.cvsparser.exceptions.AttributeNotFoundException;
import com.example.cvsparser.exceptions.LanguageNotFoundException;
import com.example.cvsparser.service.AttributeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@AllArgsConstructor
@Service
public class AttributeServiceImpl implements AttributeService {

    private AttributeRepository attributeRepository;

    public List<Attribute> getAllAttributes() {
        return attributeRepository.findAll();
    }

    public AttributeResponseObject getAttributeByLang(String code, String lang) {
        List<String> sizeList = new ArrayList<>();
        String attributeName = null;
        Attribute attribute = attributeRepository.findByCode(code);

        if (attribute == null) {
            throw new AttributeNotFoundException(
                    String.format("Attribute with code: %s not found", code));
        }

        for (Map.Entry<String, String> entry : attribute.getLabels().entrySet()) {
            if (entry.getKey().contains(lang)) {
                attributeName = attribute.getLabels().get(entry.getKey());
            }
        }
        if (attributeName == null) {
            throw new LanguageNotFoundException(
                    String.format("Language: %s  not found", lang));
        }

        attribute.getOptionList().forEach(o -> o.getLabels().keySet().forEach(key -> {
            if (key.contains(lang)) {
                sizeList.add(o.getLabels().get(key));
            }
        }));

        return new AttributeResponseObject(attributeName, sizeList);
    }
}
