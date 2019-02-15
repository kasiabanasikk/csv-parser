package com.example.cvsparser.service;


import com.example.cvsparser.dto.Attribute;
import com.example.cvsparser.dto.AttributeResponseObject;

import java.util.List;

public interface AttributeService {

    AttributeResponseObject getAttributeByLang(String code, String lang);
    List<Attribute> getAllAttributes();

}
