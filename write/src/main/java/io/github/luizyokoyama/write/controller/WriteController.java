package io.github.luizyokoyama.write.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/write")
public class WriteController {

    @PostMapping("/post")
    public ResponseEntity<String> getUserDetailsById(@RequestBody String words){
        String returnObject ="Cadastrado: ";

        return new ResponseEntity<>(returnObject + words, HttpStatus.OK);
    }
}
