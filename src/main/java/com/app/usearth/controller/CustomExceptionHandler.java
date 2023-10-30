package com.app.usearth.controller;

import com.app.usearth.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected RedirectView handleCustomException(CustomException customException){
        return new RedirectView("/admin/visit-vehicle?dong=false");
    }
}