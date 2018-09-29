package com.dachen.springboot2cat.controller;

import com.dachen.springboot2cat.service.CatService;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.MessageProducer;
import com.dianping.cat.message.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class CatController {

    @Autowired
    private CatService catService;

    @RequestMapping("/test")
    public String test() {
        return catService.testMethod();
    }

    @RequestMapping("/helloWorld")
    public Object helloWorld(HttpServletRequest request, HttpServletResponse response) {
        MessageProducer cat = Cat.getProducer();
        Transaction t = cat.newTransaction("URL", "Translate/HelloWorld");
        try {
            //do your business

            t.setStatus(Message.SUCCESS);
        } catch (Exception e) {
            Cat.getProducer().logError(e);
            t.setStatus(e);
        } finally {
            t.complete();
        }

        return "ok";
    }

    @RequestMapping("/hello")
    public String hello(){

        String pageName = "helloworld";
        String serverIp = "localhost";
        double amount = 0;

        Transaction t = Cat.newTransaction("URL", pageName);

        try {
            Cat.logEvent("URL.Server", serverIp, Event.SUCCESS, "ip="+ serverIp + "&...");
            Cat.logMetricForCount("PayCount");
            Cat.logMetricForSum("PayAmont", amount);
            t.setStatus(Transaction.SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            t.setStatus(e);
        } finally {
            t.complete();
        }
        return "hello";
    }

}
