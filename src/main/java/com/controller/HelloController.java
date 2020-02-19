package com.controller;


import com.alibaba.fastjson.JSON;
import com.service.RedisService;
import entity.CityEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;
import com.service.CityService;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class HelloController {

    private static final Log LOG = LogFactory.getLog(HelloController.class);

    @Resource
    private CityService cityService;

    /*redis服务类*/
    @Resource
    private RedisService redisService;
    /*http://localhost:8080/hello*/
    @RequestMapping(value="/hello",method= RequestMethod.GET)
    public String sayHello(){
        cityService.insertCityByJdbcTemplate();
        return "hello spring boot";
    }

    /*http://localhost:8080/insert*/
    @RequestMapping(value = "/insert",method = RequestMethod.GET)
    public String insert(){
        cityService.insertCityByMybatis();
        return "hello Mybatis";
    }

    @RequestMapping(value = "select/{name}")
    public String select(@PathVariable String name){
        //eg: http://localhost:8080/select/chongqing
        List<CityEntity> cityEntityList = cityService.selectCityByMybatis(name);
        return JSON.toJSONString(cityEntityList);
    }

    @RequestMapping(value = "/test3",method = RequestMethod.GET)
    @ResponseBody
    public void test3(@RequestParam(value="jsonStr") String request, HttpServletResponse response){
        //localhost:8080/test3?jsonStr=adfg
        String msg = "中文：你好；数字：123；字符：！@#；英文：hello";
        LOG.info("test3:"+request);
        try{
            responseResult(msg,response);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /*@RequestParam的demo*/
    @RequestMapping(value = "/test4",method = RequestMethod.GET)
    @ResponseBody
    public String test4(@RequestParam(value="jsonStr") String request){
        // http://localhost:8080/test4?jsonStr=ad
        LOG.info("test4:"+request);
        return request;
    }

    @RequestMapping(value = "redisSet/{key}/{v}")
    public String redisSet(@PathVariable String key,@PathVariable String v){
        //eg: http://localhost:8080/redisSet/c
        System.out.println(key+":"+v);
        redisService.set(key,v);
        return redisService.get(key).toString();
    }


    /**
     * <p>输出</p>
     * @author
     * @date 20170401
     * @see
     */
    private void responseResult(String msg, HttpServletResponse response)
            throws IOException {
        ServletOutputStream outputStream = null;
        try {
            response.setContentType("application/x-www-form-urlencoded; charset=GBK");
            byte[] bytes = msg.getBytes("GBK");
            //System.out.println("jsonStr字符串大小："+bytes.length);
            response.setContentLength(bytes.length);
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
        } finally {
            if (outputStream != null) {
                /*关闭输出流*/
                outputStream.close();
            }
        }
    }
}
