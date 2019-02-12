package com.example.cvsparser.service;

import com.example.cvsparser.dto.Attribute;
import com.example.cvsparser.dto.AttributeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class AttributeService {

    @Autowired
    AttributeRepository attributeRepository;

    Logger logger = LoggerFactory.getLogger(AttributeService.class);


    public List<Attribute> getAllAttributes(String lang) {
        try {
            if(lang != null){
//                attributeRepository.findAll().stream().
            }
            return attributeRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Collections.emptyList();
    }

    public List<String> getAttribute(String code, String lang) {
        try {
            List<String> attributeList = new ArrayList<>();
            if(lang != null){
               Attribute attribute = attributeRepository.findAllByCode(code);
                attributeList.add(attribute.getLabels().get(lang));
                attribute.getOptionList().forEach(o -> attributeList.add(o.getLabels().get(lang)));
                return attributeList;
            }
//            return attributeRepository.findAllByCode(code);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
