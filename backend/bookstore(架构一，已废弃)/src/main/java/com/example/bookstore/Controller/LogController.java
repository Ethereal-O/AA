package com.example.bookstore.Controller;

import com.example.bookstore.Service.LogService;
import com.example.bookstore.Service.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.context.ReactiveWebApplicationContext;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@Controller
public class LogController {

    @Autowired
    private LogService logservice;

    @Autowired
//    WebApplicationContext applicationContext;
    ReactiveWebApplicationContext applicationContext;

//    public static List<Integer> availIds;
//
//    public static Integer MAXID=1000;
//
//    @RequestMapping("/trygetidservice")
//    public Integer trygetidservice(){
//        if (availIds==null)
//        {
//            availIds=new ArrayList<>();
//            for (int i=1;i<=MAXID;i++)
//            {
//                availIds.add(i);
//            }
//        }
//        Integer availIdSize=availIds.size();
//        if (availIdSize==0)
//            return 0;
//        Integer pos= (new Random().nextInt())%availIdSize;
//        Integer ID=availIds.get(pos);
//        availIds.remove(pos);
//        return ID;
//    }
//
//    @RequestMapping("/tryreturnidservice")
//    public Integer tryreturnidservice(@RequestParam(name = "returnid")Integer returnid){
//        if (availIds==null)
//            return 0;
//        availIds.add(returnid);
//        return 1;
//    }

    @RequestMapping("/tryloginservice")
    public Integer tryloginservice(@RequestParam(name = "username")String username, @RequestParam(name = "password")String password){
//        TimerService timerservice=applicationContext.getBean(TimerService.class);
//        System.out.println("trylogin:"+timerservice);
        Integer state=logservice.tryloginservice(username,password);
//        if (state>0)
//        {
//            timerservice.setTimer();
//        }
        return state;
    }

    @RequestMapping("/trylogoutservice")
    public Long trylogoutservice(){
//        TimerService timerservice=applicationContext.getBean(TimerService.class);
//        System.out.println("trylogout:"+timerservice);
//        boolean state=logservice.trylogoutservice();
//        if (state)
//        {
//            Long remainTime=timerservice.getRemainTime();
//            timerservice.endTimer();
//            return remainTime;
//        }
        return new Long(0);
    }

    @RequestMapping("/checkloginservice")
    public Integer checkloginservice(@RequestParam(name = "username")String username, @RequestParam(name = "password")String password){
        return logservice.checkloginservice(username,password);
    }

    @RequestMapping("/registerservice")
    public Integer registerservice(@RequestParam(name = "username")String username, @RequestParam(name = "password")String password,@RequestParam(name = "email")String email)
    {
        return logservice.registerservice(username, password,email);
    }

    @RequestMapping("/getalluserdataservice")
    public List getalluserdataservice(){return logservice.getalluserdataservice();}

    @RequestMapping("/adminchangeuserdataservice")
    public Integer adminchangeuserdataservice(@RequestParam(name="id")Integer id,@RequestParam(name="index")Integer index,@RequestParam(name="content")String content){
        return logservice.adminchangeuserdataservice(id,index,content);
    }

    @RequestMapping("admindeleteuserdataservice")
    public Integer admindeleteuserdataservice(@RequestParam(name="id")Integer id){return logservice.admindeleteuserdataservice(id);}
}