package org.example.proyecto_log.controllers;

import netscape.javascript.JSObject;

import java.util.HashMap;

import org.example.proyecto_log.services.AlgorithmService;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlgorithmController {

    private final AlgorithmService algorithmService;

    public AlgorithmController(AlgorithmService algorithmService) {
        this.algorithmService = algorithmService;
    }


    @GetMapping("/moea/init")
    public String init(){
        return algorithmService.init();
    };

    @PostMapping("/moea/run")
    public HashMap run(){
        return algorithmService.run();
    }
}
