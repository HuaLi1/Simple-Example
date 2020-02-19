package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
/*@SpringBootApplication(exclude={DataSourceAutoConfiguration.class},scanBasePackages = "controller")*/
@SpringBootApplication
/*
@ComponentScan(basePackages = {"com/service", "com/controller", "com/daoService"})
*/
public class DemoApplication {

/*需要注意的是，Application类所在的包必须是其他包的父包，@SpringBootApplication这个注解继承了@ComponentScan，其默认情况下只会扫描Application类所在的包及子包，*/
    public static void main(String[] args) {
        System.out.println("start");
        SpringApplication.run(DemoApplication.class, args);
        System.out.println("end");
    }
    @RequestMapping
    public String hello() {
        return "hello spring boot!";
    }

}
