package com.example.cvsparser.controller;

import com.example.cvsparser.dto.Attribute;
import com.example.cvsparser.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class AttributeController {

    @Autowired
    AttributeService attributeService;

    @RequestMapping("/attributes")
    public List<Attribute> getAllAttributes(@RequestParam(value = "lang", required = false) String lang){
        return attributeService.getAllAttributes(lang);
    }

    @RequestMapping("/attributes/{code}")
    public Attribute getAllAttributes(@PathVariable("code") String code, @RequestParam(value = "lang", required = false) String lang){
        return attributeService.getAttribute(code);
    }
}
