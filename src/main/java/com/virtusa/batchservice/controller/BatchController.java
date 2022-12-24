package com.virtusa.batchservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BatchController {

    @GetMapping("/batch/process/start")
    public ResponseEntity<Map<String, String>> batchProcessStart(){
        Map<String, String> response = new HashMap<>();
        response.put("STATUS", "BATCH_PROCESS_STARTED");
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
    }
}
