package com.datasoft.bkash.ea.controller;

import com.datasoft.bkash.ea.utils.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private SmtpEmailService emailService;

    @GetMapping("/get/code")
    public String getCode(){
        return "Code-1";
    }
//    http://localhost:8181/web-backend/home/get/code

    @GetMapping(value = "/mail/send")
    public ResponseEntity<?> getAll(@RequestParam String emailId, @RequestParam String subject, @RequestParam String body) {
        try {
            String [] emailID= new String[]{emailId};
            emailService.sendEmail(subject,body, emailID,  null,  null);
            return ResponseEntity.ok().body("Email Send Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(e);
        }
    }
}
