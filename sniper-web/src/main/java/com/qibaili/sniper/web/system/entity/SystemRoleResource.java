package com.qibaili.sniper.web.system.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author qibaili
 * @date 2018-06-06
 */
@Entity
@Table(name = "SYSTEM_ROLE_RESOURCE")
@Data
public class SystemRoleResource {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    private String roleId;

    private String resourceId;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "CREATE_PERSON")
    private String createPerson;
}
