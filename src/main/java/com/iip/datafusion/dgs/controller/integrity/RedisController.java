package com.iip.datafusion.dgs.controller.integrity;

import com.iip.datafusion.dgs.model.integrity.RedisParam;

import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author zengc
 * @date 2018/1/25 17:16
 */
@Controller
public class RedisController {

    @Autowired


    @RequestMapping(path = "/dgs/redisTest",method = RequestMethod.POST)
    @ResponseBody
    public Result getCache( ){
        return new Result();
    }
}
