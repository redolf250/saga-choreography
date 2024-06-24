package com.redolf.notificationservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @GetMapping("/send")
    private ResponseEntity<?> sendNotification(@RequestParam(name = "message", required = false) String message){
        return new ResponseEntity<>("Hello, sending notification to: "+message , HttpStatus.OK);
    }
}
