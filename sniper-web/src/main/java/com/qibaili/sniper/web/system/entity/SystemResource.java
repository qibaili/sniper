package com.qibaili.sniper.web.system.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author qibaili
 * @date 2018-06-06
 */
@Entity
@Table(name = "SYSTEM_RESOURCE")
@Data
public class SystemResource {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "PARENT_ID")
    private String parentId;

    private String name;

    private String permission;

    /**
     * 0-非必选
     * 1-必选
     * 通常"查询"权限是必选
     */
    private String required;

    /**
     * 0-页面
     * 1-按钮
     */
    private String type;

    private Integer sort;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "CREATE_PERSON")
    private String createPerson;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "UPDATE_PERSON")
    private String updatePerson;

    @Transient
    List<SystemResource> children;
}
