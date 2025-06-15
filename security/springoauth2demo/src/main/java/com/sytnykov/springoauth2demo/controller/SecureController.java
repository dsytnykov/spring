package com.sytnykov.springoauth2demo.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecureController {

    @GetMapping("/secure")
    public String securePage(Authentication authentication) {
        if(authentication instanceof UsernamePasswordAuthenticationToken upat) {
            System.out.println(upat);
        } else if(authentication instanceof OAuth2AuthenticationToken oat) {
            System.out.println(oat);
        }
        return "secure.html";
    }
}
