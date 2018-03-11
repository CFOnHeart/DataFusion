package com.iip.datafusion.jvs.controller;

import com.iip.datafusion.jvs.service.JvsService;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JvsController {
    @Autowired
    JvsService tvsService;


    @RequestMapping(path={"/tvs/allTasks"},method = RequestMethod.POST)
    @ResponseBody
    public Result allTasks(){ return tvsService.allTasks(); }

    @RequestMapping(path={"/tvs/privateTasks"},method = RequestMethod.POST)
    @ResponseBody
    public Result privateTasks(@RequestParam String userId){ return tvsService.privateTasks(userId); }
}
