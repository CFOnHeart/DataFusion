package com.iip.datafusion.redis.controller;

import com.iip.datafusion.redis.model.RedisConfiguration;
import com.iip.datafusion.redis.service.RedisService;
import com.iip.datafusion.util.jsonutil.Result;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zengc
 * @date 2018/3/11 16:42
 */
public class RedisController {

    @Autowired
    private RedisService redisService;

    @RequestMapping(path = "/tvs/redisData",method = RequestMethod.POST)
    @ResponseBody
    public Result getPage(@RequestBody RedisConfiguration redisConfiguration){
        JSONObject wholeJsonObj = new JSONObject();
        wholeJsonObj.put("items",redisService.getLrange(redisConfiguration.getKey(),redisConfiguration.getStart(),redisConfiguration.getEnd()).toString());
        System.out.println("getPage");
        return new Result(1,null,wholeJsonObj.toString());
    }



}
