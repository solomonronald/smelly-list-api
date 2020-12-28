package com.smellylist.api.misc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MiscController {

    @GetMapping("/marco")
    public ResponseEntity<?> marcoPolo() {
        return new ResponseEntity<>("Polo", HttpStatus.OK);
    }
}
