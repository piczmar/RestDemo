package com.consulner.springboot.rest.app;

import com.consulner.springboot.rest.api.ApiDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class ApiController {

    private String message;

    public ApiController(@Value("${demo.message}") final String message) {
        this.message = message;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api")
    public
    @ResponseBody
    ApiDTO api() {
        return new ApiDTO(message);
    }

}
