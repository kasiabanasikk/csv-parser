package com.example.cvsparser.service.impl;

import com.example.cvsparser.dto.Attribute;
import com.example.cvsparser.dto.AttributeRepository;
import com.example.cvsparser.dto.Option;
import com.example.cvsparser.dto.OptionRepository;
import com.example.cvsparser.service.ParserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Component
public class ParserServiceImpl implements ParserService {

    private AttributeRepository attributeRepository;

    private OptionRepository optionRepository;

    public List<Map<String, String>> readCsv(String fileName) {
        List<Map<String, String>> itemsList = new ArrayList<>();

        String[] labels = null;
        String[] lineArray;
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while ((line = reader.readLine()) != null) {
                Map<String, String> labelMap = new HashMap<>();
                lineArray = StringEscapeUtils.unescapeHtml4(line).split(";");

                if (labels == null || labels.length == 0) {
                    labels = lineArray;
                    continue;
                }

                for (int i = 0; i < lineArray.length; i++) {
                    labelMap.put(labels[i], lineArray[i].replaceAll("^\"|\"$", ""));
                }

                itemsList.add(labelMap);
            }

            return itemsList;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return Collections.emptyList();
    }

    public void sendToAttributeRepository(String fileName) {
        List<Map<String, String>> itemsList = readCsv(fileName);
        List<Attribute> attributeList = new ArrayList<>();

        itemsList.forEach(item -> attributeList.add(new Attribute(item)));

        attributeRepository.saveAll(attributeList);
    }

    public void sendToOptionRepository(String fileName) {
        List<Map<String, String>> itemsList = readCsv(fileName);
        List<Option> optionList = new ArrayList<>();

        itemsList.forEach(item -> {
            Attribute attribute = attributeRepository.findByCode(item.get("attribute"));
            optionList.add(new Option(item, attribute));
        });

        optionRepository.saveAll(optionList);
    }
}
