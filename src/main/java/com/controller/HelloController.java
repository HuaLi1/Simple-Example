package com.controller;


import com.alibaba.fastjson.JSON;
import com.service.RedisService;
import com.service.SpringContextHolder;
import com.thread.ThreadDemo3;
import entity.CityEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;
import com.service.CityService;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

@RestController
public class HelloController {

    private static final Log LOG = LogFactory.getLog(HelloController.class);

    @Resource
    private CityService cityService;
    /*redis服务类*/
    @Resource
    private RedisService redisService;
    @Resource
    private SpringContextHolder springContextHolder;
    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;
    private Object ThreadDemo1;


    /*http://localhost:8080/hello*/
    @RequestMapping(value="/hello",method= RequestMethod.GET)
    public String sayHello(){

        //cityService.insertCityByJdbcTemplate();
        ThreadDemo3 t1 = new ThreadDemo3(springContextHolder);
        ThreadDemo3 t2 = new ThreadDemo3(springContextHolder);
        ThreadDemo3 t3 = new ThreadDemo3(springContextHolder);
        t1.start();
        t2.start();
        t3.start();
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

    @RequestMapping(value = "execute",method = RequestMethod.GET)
    public void execute(){
        //eg: http://localhost:8080/execute

        Vector<String> waybillNos = new Vector<>();

        waybillNos.add("124589878");
        waybillNos.add("000000111");
        waybillNos.add("000000000");
        waybillNos.add("000000001");
        taskExecutor.execute(new ThreadTest(waybillNos));


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

    public class ThreadTest implements Runnable{

        private Vector<String> waybillNos;
        public ThreadTest(Vector<String> waybillNos){
            this.waybillNos=waybillNos;
        }
        @Override
        public void run() {
            //处理业务
            if (waybillNos.size()<=4){
                waybillNos.add("------");
                System.out.println("小于等于4");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                waybillNos.remove("------");
                System.out.println("大于4");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            printWaybillNo(waybillNos);
        }
    }
    private void printWaybillNo(List<String> waybillNos){
        for (String waybillNo : waybillNos){
            System.out.println(waybillNo+"  ");
        }
    }
}
