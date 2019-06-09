package com.codem.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @RequestMapping(value = "/")
    public String hello() {
        return "自动部署hello服务成功";
    }

}
