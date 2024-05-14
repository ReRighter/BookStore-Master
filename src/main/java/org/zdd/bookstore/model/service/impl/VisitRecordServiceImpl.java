package org.zdd.bookstore.model.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.zdd.bookstore.model.dao.VisitRecordMapper;
import org.zdd.bookstore.model.entity.VisitRecord;
import org.zdd.bookstore.model.service.VisitRecordService;
import org.springframework.stereotype.Service;

/**
* @author Righter
* @description 针对表【visit_record】的数据库操作Service实现
* @createDate 2024-05-11 22:55:59
*/
@Service
public class VisitRecordServiceImpl implements VisitRecordService{
    @Autowired
    VisitRecordMapper mapper;
    @Override
    public void saveRecord(VisitRecord record) {
        mapper.insert(record);
    }
}




