package com.qibaili.sniper.web.system.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author qibaili
 * @date 2018-06-06
 */
@Entity
@Table(name = "SYSTEM_USER")
@Data
public class SystemUser {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    private String name;

    private String username;

    @JSONField(serialize = false)
    private String password;

    private String email;

    private String phone;

    private String status;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "CREATE_PERSON")
    private String createPerson;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "UPDATE_PERSON")
    private String updatePerson;

    @Column(name = "LAST_PASSWORD_RESET_TIME")
    private Date lastPasswordRestTime;
}
