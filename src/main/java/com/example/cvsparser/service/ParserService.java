package com.example.cvsparser.service;

import com.example.cvsparser.dto.*;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Attr;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Component
public class ParserService {

    @Autowired
    AttributeRepository attributeRepository;

    @Autowired
    OptionRepository optionRepository;

    Logger logger = LoggerFactory.getLogger(ParserService.class);

    private List<Map<String,String>> readCsv(String fileName) {

        List<Map<String,String>> itemsList = new ArrayList<>();
        List<Option> optionList = new ArrayList<>();

        String[] labels = null;
        String[] lineArray = null;
        String line = null;
//        long id = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            while ((line = reader.readLine()) != null) {

                Map<String, String> labelMap = new HashMap<>();
                lineArray = StringEscapeUtils.unescapeHtml4(line).split(";;|;");

                if (labels == null || labels.length == 0) {
                    labels = lineArray;
                    continue;
                }

                for (int i = 0; i < lineArray.length; i++) {
                    labelMap.put(labels[i], lineArray[i]);
                }

                itemsList.add(labelMap);
//                if (myClass.getCanonicalName().equals(Attribute.class.getCanonicalName())) {
//                    Attribute attribute = new Attribute(++id, lineArray[0], labelMap);
//                    attributeList.add(attribute);
//                    attributeRepository.save(attribute);
//                } else if (myClass.getCanonicalName().equals(Option.class.getCanonicalName())) {
//                    Attribute attribute = attributeRepository.findAllByCode(lineArray[lineArray.length - 2]);
//                    if (attribute != null) {
//                        Option option = new Option(++id, lineArray[0], lineArray[lineArray.length - 2], lineArray[lineArray.length - 1], attribute, labelMap);
//                        optionList.add(option);
//                        optionRepository.save(option);
//                    }
//                }
            }

            return itemsList;
        } catch (FileNotFoundException exception) {
            logger.error(exception.getMessage(), exception);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return Collections.emptyList();
    }

    public void sendToAttributeRepository(String fileName){

        List<Map<String,String>> itemsList = readCsv(fileName);
        List<Attribute> attributeList = new ArrayList<>();

        itemsList.forEach(item ->{
            attributeList.add(new Attribute(item));
        });

        attributeRepository.saveAll(attributeList);
    }

    public void sendToOptionRepository(String fileName){

        List<Map<String,String>> itemsList = readCsv(fileName);
        List<Option> optionList = new ArrayList<>();

        itemsList.forEach(item ->{
            Attribute attribute = attributeRepository.findAllByCode(item.get("attribute"));
            optionList.add(new Option(item, attribute));
        });

        optionRepository.saveAll(optionList);
    }
}
