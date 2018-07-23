package com.iip.datafusion.nsps.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author Junnor.G
 * @Date 2018/2/3 上午2:51
 */
public class TopicModelConfiguration {
//    @JsonProperty("corpusPath")
//    private String corpusPath;
//     update filepath from mysql
    @JsonProperty("corpusSourceId")
    private String corpusSourceId;
    @JsonProperty("corpusTableName")
    private String corpusTableName;
    @JsonProperty("topicNum")
    private int topicNum;
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

    public int getTopicNum() {
        return topicNum;
    }

    public void setTopicNum(int topicNum) {
        this.topicNum = topicNum;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
}
