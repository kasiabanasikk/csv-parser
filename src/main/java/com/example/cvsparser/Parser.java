package com.example.cvsparser;

import com.example.cvsparser.dto.AttributeRepository;
import com.example.cvsparser.dto.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Parser {

    @Autowired
    AttributeRepository attributeRepository;

    Logger logger = LoggerFactory.getLogger(Parser.class);

    public List<Attribute> readCvs(String fileName) {

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            Path path = Paths.get(fileName);
            long lineCount = Files.lines(path).count();

            String [][] array = new String[(int) lineCount][(int)lineCount];
            for (int i=0; i< (int) lineCount; i++){
                Map<String, String> labelMap = new HashMap<>();

                String [] line = reader.readLine().split(";;|;"); //STRING UTILS
                for(int j=0; j<line.length; j++) {
                    array[i][j] = line[j];
                    if (i> 0 && j > 0) {
                        labelMap.put(array[0][j], array[i][j]);
                    }
                }
                if(i>0) {
                    Attribute attribute = new Attribute((long) i, array[i][0], labelMap);
                    attributeRepository.save(attribute);
                }
            }
            System.out.println("aaa");
            } catch (FileNotFoundException exception) {
            logger.error(exception.getMessage(), exception);
            } catch (IOException e) {
            logger.error(e.getMessage(), e);
            }

        return Collections.emptyList();
    }
}
