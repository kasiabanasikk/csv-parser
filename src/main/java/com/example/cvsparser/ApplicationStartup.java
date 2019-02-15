package com.example.cvsparser;

import com.example.cvsparser.service.ParserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    ParserService parserService;

    Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        logger.info("Loading from csv files");

        String pathOptions = Paths.get(".").toAbsolutePath().normalize().toString()+"\\src" +
                "\\main\\resources\\options.csv";
        String pathAttributes = Paths.get(".").toAbsolutePath().normalize().toString()+"\\src" +
                "\\main\\resources\\attributes.csv";
        parserService.sendToAttributeRepository(pathAttributes);
        parserService.sendToOptionRepository(pathOptions);
    }
}
