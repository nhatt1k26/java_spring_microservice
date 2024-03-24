package com.nhat220801.udemydemo.helloworld;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {
    private MessageSource messageSource;
    public HelloWorldController(MessageSource messageSource){
        this.messageSource = messageSource;
    }
    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "Hello world";
    }

    @GetMapping(path = "/hello-world-bean")
    public String helloWorldBean(){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message",null, "Default Message",locale);
//        return new HelloWorldBean("Hello world");
    }
    @GetMapping("hello/{id}")
    public String getId(@PathVariable Integer id){
        return id.toString();
    }
}
