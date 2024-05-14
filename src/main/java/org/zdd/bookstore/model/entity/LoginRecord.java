package org.zdd.bookstore.model.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "login_record")
public class LoginRecord {
    @Id
    @Column(name="record_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recordId;
    @Column(name = "login_time")
    private Date loginTime;
    @Column(name = "login_ip")
    private String loginIp;
    @Column(name="user_id")
    private int useId;

    public int getUseId() {
        return useId;
    }

    public void setUseId(int useId) {
        this.useId = useId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordID) {
        this.recordId = recordID;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
}
