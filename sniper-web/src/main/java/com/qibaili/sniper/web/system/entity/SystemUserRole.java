package com.qibaili.sniper.web.system.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author qibaili
 * @date 2018-08-13
 */
@Entity
@Table(name = "SYSTEM_USER_ROLE")
@Data
public class SystemUserRole {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    private String userId;

    private String roleId;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "CREATE_PERSON")
    private String createPerson;
}
