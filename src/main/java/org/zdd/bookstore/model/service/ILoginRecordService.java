package org.zdd.bookstore.model.service;

import org.zdd.bookstore.model.entity.LoginRecord;

public interface ILoginRecordService {
    public void saveRecord(LoginRecord l);
    public LoginRecord getRecord();

}
