package com.example.cvsparser.service;

import com.example.cvsparser.dto.*;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ParserService {

    @Autowired
    AttributeRepository attributeRepository;

    @Autowired
    OptionRepository optionRepository;

    Logger logger = LoggerFactory.getLogger(ParserService.class);

    public void readCsv(String fileName, Class<?> myClass) {

        List<Option> optionList = new ArrayList<>();
        List<Attribute> attributeList = new ArrayList<>();
        String[] labels = null;
        String[] lineArray = null;
        String line = null;
        long id = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            while ((line = reader.readLine()) != null) {

                Map<String, String> labelMap = new HashMap<>();
                lineArray = StringEscapeUtils.unescapeHtml4(line).split(";;|;");

                if (labels == null || labels.length == 0) {
                    labels = lineArray;
                    continue;
                }

                for (int i = 1; i < lineArray.length; i++) {
                    labelMap.put(labels[i], lineArray[i]);
                }

                if (myClass.getCanonicalName().equals(Attribute.class.getCanonicalName())) {
                    Attribute attribute = new Attribute(++id, lineArray[0], labelMap);
                    attributeList.add(attribute);
                    attributeRepository.save(attribute);
                } else if (myClass.getCanonicalName().equals(Option.class.getCanonicalName())) {
                    Attribute attribute = attributeRepository.findAllByCode(lineArray[lineArray.length - 2]);
                    if (attribute != null) {
                        Option option = new Option(++id, lineArray[0], lineArray[lineArray.length - 2], lineArray[lineArray.length - 1], attribute, labelMap);
                        optionList.add(option);
                        optionRepository.save(option);
                    }
                }
            }
        } catch (FileNotFoundException exception) {
            logger.error(exception.getMessage(), exception);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
