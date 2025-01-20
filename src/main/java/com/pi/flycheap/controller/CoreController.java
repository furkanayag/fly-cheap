package com.pi.flycheap.controller;

import com.pi.flycheap.service.CoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
public class CoreController {

    private final CoreService coreService;

    @GetMapping
    public void checkTicketsManuel(){
        log.info("Welcome to the Fly Cheap, Enjoy your flight!");
        coreService.checkTickets();
    }
}
