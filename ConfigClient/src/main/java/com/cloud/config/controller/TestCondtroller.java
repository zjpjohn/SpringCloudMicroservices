package com.cloud.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Project: ConfigClient
 * Module Desc:com.juntu.config.controller
 * User: zjprevenge
 * Date: 2016/11/22
 * Time: 14:34
 */
@RefreshScope
@RestController
public class TestCondtroller {

    @Value("${from}")
    private String from;

    @Value("${to}")
    private String to;

    @RequestMapping("/from")
    public String from() {
        return from;
    }

    @RequestMapping("/to")
    public String to(){
        return to;
    }
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
