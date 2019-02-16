package com.example.cvsparser;

import com.example.cvsparser.service.ParserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.net.URL;


@Slf4j
@AllArgsConstructor
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    ParserService parserService;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        log.info("Loading from csv files");

        URL optionsUrl = ApplicationStartup.class.getClassLoader().getResource("options.csv");
        URL attributesUrl = ApplicationStartup.class.getClassLoader().getResource("attributes.csv");

        try {
            if (optionsUrl == null) {
                throw new FileNotFoundException("options.csv");
            } else if (attributesUrl == null) {
                throw new FileNotFoundException("attributes.csv");
            }

            String pathOptions = optionsUrl.getPath();
            String pathAttributes = attributesUrl.getPath();

            parserService.sendToAttributeRepository(pathAttributes);
            parserService.sendToOptionRepository(pathOptions);

        } catch (FileNotFoundException e) {
            log.error("File not found", e.getMessage());
        }
    }
}
