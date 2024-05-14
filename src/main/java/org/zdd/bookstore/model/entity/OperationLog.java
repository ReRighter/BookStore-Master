package org.zdd.bookstore.model.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "operation_log")
public class OperationLog {
    @Id
    @Column(name = "log_pk")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int logPk;
    @Column(name ="oper_type")
    private String OperType;

    public String getOperType() {
        return OperType;
    }

    public void setOperType(String operType) {
        OperType = operType;
    }

    @Column(name = "oper_time")
    private Date OperTime;

    @Column(name = "ip")
    private String Ip;
    @Column(name = "user_type")
    private String UserType;
    @Column(name = "user_name")
    private String UserName;

    public int getLogPk() {
        return logPk;
    }

    public void setLogPk(int logPk) {
        this.logPk = logPk;
    }

    public Date getOperTime() {
        return OperTime;
    }

    public void setOperTime(Date operTime) {
        OperTime = operTime;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
