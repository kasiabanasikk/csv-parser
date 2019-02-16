package com.example.cvsparser.controller;

import com.example.cvsparser.dto.Attribute;
import com.example.cvsparser.dto.AttributeResponseObject;
import com.example.cvsparser.service.AttributeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@AllArgsConstructor
@RestController
public class AttributeController {

    private AttributeService attributeService;

    @RequestMapping("/attributes")
    public List<Attribute> getAllAttributes() {
        return attributeService.getAllAttributes();
    }

    @RequestMapping("/attributes/{code}")
    public AttributeResponseObject getAttributeByLang(@PathVariable("code") String code,
                                                      @RequestParam(value = "lang") String lang) {
        return attributeService.getAttributeByLang(code, lang);
    }
}
