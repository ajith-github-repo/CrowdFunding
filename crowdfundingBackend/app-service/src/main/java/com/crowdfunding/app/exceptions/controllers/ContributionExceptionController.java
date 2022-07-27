package com.crowdfunding.app.exceptions.controllers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Order(value= Ordered.HIGHEST_PRECEDENCE)
public class ContributionExceptionController {
   

   
}