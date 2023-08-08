package io.github.luizyokoyama.write.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class WriteController {

    @PostMapping("/post")
    public ResponseEntity<String> writeWords(@RequestBody String words){
        String returnObject ="Cadastrado: ";

        //return new ResponseEntity<>(returnObject + words, new MultiValueMapAdapter<>(Map.of("Content-Type", List.of("application/json"))), HttpStatus.OK);
        return new ResponseEntity<>(returnObject + words, HttpStatus.OK);
    }
}
