package com.consulner.springboot.rest.app;

public class DemoDTO {


  private final String message;

  public DemoDTO(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

}
