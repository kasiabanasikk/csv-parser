package com.example.cvsparser.service;

import com.example.cvsparser.dto.Attribute;
import com.example.cvsparser.dto.AttributeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
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

            if()
            return attributeRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Collections.emptyList();
    }



    public Attribute getAttribute(String code) {
        try {
            return attributeRepository.findAllByCode(code);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

//    public OptionEntity getOption() {
//        return optionRepository.findById(2L).get();
//    }
}
