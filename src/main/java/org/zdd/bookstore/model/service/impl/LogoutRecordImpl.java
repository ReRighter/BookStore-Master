package org.zdd.bookstore.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zdd.bookstore.model.dao.LogoutRecordMapper;
import org.zdd.bookstore.model.entity.LogoutRecord;

import java.util.Date;

@Service
public class LogoutRecordImpl {
    @Autowired
    LogoutRecordMapper mapper;
    public  void saveRecord(LogoutRecord record){
        record.setLogoutTime(new Date());
        mapper.insert(record);
    }

}
