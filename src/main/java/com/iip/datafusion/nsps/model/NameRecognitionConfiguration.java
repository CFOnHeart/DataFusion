package com.iip.datafusion.nsps.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author Junnor.G
 * @Date 2018/2/1 下午4:14
 */
public class NameRecognitionConfiguration {
    //    @JsonProperty("corpusPath")
//    private String corpusPath;
//     update filepath from mysql
    @JsonProperty("corpusSourceId")
    private String corpusSourceId;
    @JsonProperty("corpusTableName")
    private String corpusTableName;
    @JsonProperty("tableName")
    private String tableName;
    @JsonProperty("dataSourceId")
    private String dataSourceId;

//    public String getCorpusPath() {
//        return corpusPath;
//    }
//
//    public void setCorpusPath(String corpusPath) {
//        this.corpusPath = corpusPath;
//    }
    public String getCorpusSourceId(){ return corpusSourceId;}

    public void setCorpusSourceId(String corpusSourceId) { this.corpusSourceId = corpusSourceId; }

    public String getCorpusTableName() { return corpusTableName; }

    public void setCorpusTableName(String corpusTableName) { this.corpusTableName = corpusTableName; }

    public String getTableName(){ return tableName; }

    public void setTableName(String tableName){ this.tableName = tableName; }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
}
