package com.consulner.springboot.rest.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/demo")
public class DemoController {

  private String message;

  public DemoController(@Value("${demo.message}") final String message) {
    this.message = message;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/demo")
  public
  @ResponseBody
  DemoDTO demo() {
    return new DemoDTO(message);
  }

}
