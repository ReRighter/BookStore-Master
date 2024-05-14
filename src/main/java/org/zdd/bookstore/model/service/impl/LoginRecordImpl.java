package org.zdd.bookstore.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zdd.bookstore.model.dao.LoginRecordMapper;
import org.zdd.bookstore.model.entity.LoginRecord;
import org.zdd.bookstore.model.service.ILoginRecordService;

import java.util.Date;
@Service
public class LoginRecordImpl implements ILoginRecordService {
    @Autowired
    private LoginRecordMapper loginRecordMapper;
    @Override
    public void saveRecord(LoginRecord record) {
        record.setLoginTime(new Date());
        loginRecordMapper.insert(record);
    }

    @Override
    public LoginRecord getRecord() {
        return null;
    }
}
