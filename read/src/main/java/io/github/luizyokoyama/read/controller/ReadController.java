package io.github.luizyokoyama.read.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/read")
public class ReadController {

    @GetMapping("/get")
    public ResponseEntity<String> getUserDetailsById(){
        String returnObject ="{\"palavra1\",\" palavra2\",\" palavra3\" }";

        return new ResponseEntity<>("Palavras cadastradas: " + returnObject, HttpStatus.OK);
    }

}
