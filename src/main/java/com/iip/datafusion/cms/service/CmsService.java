package com.iip.datafusion.cms.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iip.datafusion.cms.dao.CmsDao;
import com.iip.datafusion.cms.model.ColumnStructure;
import com.iip.datafusion.cms.model.DataBaseStructure;
import com.iip.datafusion.cms.model.PreviewStructure;
import com.iip.datafusion.cms.model.TableStructure;
import com.iip.datafusion.ums.dao.LoginTicketDao;
import com.iip.datafusion.ums.dao.UserDao;
import com.iip.datafusion.ums.model.LoginTicket;
import com.iip.datafusion.util.dbutil.DataSourceProperties;
import com.iip.datafusion.util.dbutil.DataSourceRouter;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.JsonParse;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;


import javax.sql.RowSet;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by jingwei on 2017/12/13.
 */
@org.springframework.stereotype.Service
public class CmsService {
    @Autowired
    CmsDao cmsDao;
    @Autowired
    DataSourceRouterManager dataSourceRouterManager;

    public Result  creCon(DataSourceProperties c){
        switch (c.getDriverClassName()){
            case "mysql":{
                c.setDriverClassName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://"+c.getUrl();
                c.setUrl(url);
                break;
            }
        }
        return cmsDao.addDatabase(c);
    }

//TODO 连接池中释放这个连接
    public Result delCon(String id){
//        DataSourceRouterManager.dataSourceIds.remove(id);
        return new Result(1,null,null);
    }

    //TODO 查询数据库一律传递id
    public Result  desCon(String id){
        //为了适配传递id为display
        if(!id.equals("primary") && (id.length()<4 || !id.substring(0,3).equals("db_"))) {
            List<DataSourceProperties> list = dataSourceRouterManager.getDataSourceProperties();
            for (DataSourceProperties item : list) {
                if (item.getDisplayName().equals(id))
                    id = item.getId();
            }
        }


        DataBaseStructure dataBaseStructure = cmsDao.getDatabaseStructure(id);
        if(dataBaseStructure==null){
            return new Result(0,"cannot connect to "+id,null);
        }

//        JSONObject jsonObject = JSONObject.fromObject(dataBaseStructure);

        try {
            String json = JsonParse.getMapper().writeValueAsString(dataBaseStructure);
            return new Result(1, null, json);
        }
        catch (JsonProcessingException e){
            return new Result(0,"json解析失败",null);
        }
    }

    public Result getCurrentConnection(){
        return cmsDao.getCurrentConnection();
    }

    public Result previewCon(String id, String table,String num) {
        //为了适配传递id为display
        if(!id.substring(0,3).equals("db_")) {
            List<DataSourceProperties> list = dataSourceRouterManager.getDataSourceProperties();
            for (DataSourceProperties item : list) {
                if (item.getDisplayName().equals(id))
                    id = item.getId();
            }
        }

        PreviewStructure previewStructure = cmsDao.previewCon(id,table,num);
        if(previewStructure == null){
            return new Result(0,"cannot connect to ",null);
        }
        return new Result(1,null,previewStructure.toString());
    }

}
