package com.iip.datafusion.eems.controller;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iip.datafusion.eems.model.Entity;
import com.iip.datafusion.eems.model.UserEntity;
import com.iip.datafusion.eems.service.EntityService;
import com.iip.datafusion.eems.service.UserEntityService;
import com.iip.datafusion.util.jsonutil.JsonParse;
import com.iip.datafusion.util.jsonutil.Result;
import com.iip.datafusion.util.userutil.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Created by tsy on 2017/12/14.
 */
@Controller
public class EntityController {
    @Autowired
    EntityService entityService;
    @Autowired
    UserEntityService userEntityService;
    @Autowired
    UserManager userManager;

    @RequestMapping(path={"/entity/show"},method={RequestMethod.GET})
    @ResponseBody
    public Result getUserEnitities(){
        int userId=userManager.getUserId();
        if(userId==0){
            return new Result(0,"用户未登录",null);
        }
        List<Entity> list = entityService.getEntityByUserId(userId);

        // todo:遍历list检查每个Entity是否已连接，已连接-获得DataSourceId，未连接-空字符串

        if(list==null) {
            return new Result(0, "entity not exist for user " + userId, null);
        }
        try {
            String json = JsonParse.getMapper().writeValueAsString(list);
            return new Result(1, null, json);
        } catch (JsonProcessingException e){
            return new Result(0,"json解析失败",null);
        }
    }
    @RequestMapping(path={"/entity/delete"},method={RequestMethod.GET})
    @ResponseBody
    public Result  deleteEntity(@RequestParam(value="entityId") int entityId){
        if(!(entityService.deleteEntity(entityId) && userEntityService.deleteEntity(entityId))){
            return new Result(0,"删除实体失败",null);
        }
        return  new Result(1,"删除实体成功",null);
    }

//    @RequestMapping(path={"/entity/insert"},method={RequestMethod.POST})
//    @ResponseBody
//    //TODO:插入实体时相同用户不能同时创建两个一样的实体；
//    //TODO insertEntity的逻辑应该在成功创建实体库之后
//    public Result  insertEntity(@RequestBody Map<String,String> map){
//        Entity entity=null;
//        entity=new Entity();
//        entity.setDisplayName(map.get("displayName"));
//        entity.setTableName(map.get("tableName"));
//        entity.setDbPosition(map.get("dbPosition"));
//        entity.setEntityType(Integer.parseInt(map.get("entityType")));
//        entity.setProperties(map.get("properties"));
//        int addEntity=entityService.insertEntity(entity);
//        UserEntity newUserEntity=new UserEntity();
//        newUserEntity.setEntityId(addEntity);
//        newUserEntity.setUserId(userManager.getUserId());
//        Boolean addUserEntity=userEntityService.insertUserEntity(newUserEntity);
//        if ((addEntity>0) && addUserEntity){
//            return  new Result(1,"插入实体成功",null);
//        }
//        return  new Result(0,"插入实体失败",null);
//    }

    @RequestMapping(path={"/entity/update"},method={RequestMethod.GET})
    @ResponseBody
    public Result updateEntity(@RequestParam(value="entityId") int entityId,@RequestParam(value="property")String property,
                               @RequestParam (value="newValue")String newValue){
        if( entityService.updateEntity(entityId,property,newValue)){
            return new Result(1,"更新实体成功",null);
        }
         return new Result(0,"更新实体失败",null);
    }

    //在目标数据库创建实体库
    @RequestMapping(path={"/entity/create"},method={RequestMethod.POST})
    @ResponseBody
    public Result createEntityDB(@RequestBody Entity entity){
        // 1. 到目标数据库创建Entity表
        Result result;
        int id = userManager.getUserId();
        result = entityService.createEntityTable(entity,id);
        if(result.getStatus()==0){
            return result;
        }

        //创建成功后，insert
        int addEntity=entityService.insertEntity(entity);
        UserEntity newUserEntity=new UserEntity();
        newUserEntity.setEntityId(addEntity);
        newUserEntity.setUserId(id);
        Boolean addUserEntity=userEntityService.insertUserEntity(newUserEntity);
        if ((addEntity>0) && addUserEntity){
            result = new Result(1,"创建成功，插入实体成功",null);
        }else {
            result = new Result(0, "创建成功，但是插入实体失败", null);
        }
        return result;
    }
}
