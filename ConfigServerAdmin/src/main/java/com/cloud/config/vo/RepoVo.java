package com.cloud.config.vo;

/**
 * Project: ConfigServer
 * Module Desc:com.juntu.config.vo
 * User: zjprevenge
 * Date: 2016/11/21
 * Time: 15:01
 */
public class RepoVo {

    //projectID
    private Integer id;
    //服务名称
    private String serviceId;
    //file name
    private String name;
    //file path
    private String path;
    //分支
    private String branch;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
