package com.qibaili.sniper.web.system.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author qibaili
 * @date 2018-08-14
 */
@Entity
@Table(name = "SYSTEM_LOG")
@Data
public class SystemLog {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    private String description;

    @Column(name = "LOG_TYPE")
    private String logType;

    @Column(name = "METHOD")
    private String method;

    @Column(columnDefinition = "text")
    private String params;

    @Column(name = "REQUEST_IP")
    private String requestIp;

    private Long time;

    private String username;

    private String address;

    private String browser;
}
