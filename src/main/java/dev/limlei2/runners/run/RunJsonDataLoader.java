package dev.limlei2.runners.run;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.aot.hint.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class RunJsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RunJsonDataLoader.class);
    private final RunRepository runRepository;
    private final ObjectMapper objectMapper;

    public RunJsonDataLoader(RunRepository runRepository, ObjectMapper objectMapper) {
        this.runRepository = runRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if(runRepository.count() == 0){
            try (InputStream is = TypeReference.class.getResourceAsStream("/data/runs.json")) {
                Runs runs = objectMapper.readValue(is, Runs.class);
                log.info("Reading {} runs from JSON file and saving to database", runs.runs().size());
                runRepository.saveAll(runs.runs());
            } catch (IOException e) {
                log.error("Error loading data", e);
            }
        } else {
            log.info("Data already loaded");
        }
    }
}
