package com.example.cvsparser.controller;

import com.example.cvsparser.dto.Attribute;
import com.example.cvsparser.dto.AttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class AttributeController {

    @Autowired
    AttributeRepository csvService;

    @RequestMapping("/attributes")
    public String getAllAttributes(@RequestParam(value = "lang", required = false) String lang){
        Attribute attribute = csvService.findAllByCode("helmetsize");
        return attribute.getLabels().get("label-pl_PL");
    }
}
