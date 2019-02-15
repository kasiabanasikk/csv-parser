package com.example.cvsparser.service;


import java.util.List;
import java.util.Map;

public interface ParserService {

    List<Map<String,String>> readCsv(String fileName);
    void sendToAttributeRepository(String fileName);
    void sendToOptionRepository(String fileName);

}
