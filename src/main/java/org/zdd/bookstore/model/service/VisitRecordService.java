package org.zdd.bookstore.model.service;

import org.zdd.bookstore.model.entity.VisitRecord;


/**
* @author Righter
* @description 针对表【visit_record】的数据库操作Service
* @createDate 2024-05-11 22:55:59
*/
public interface VisitRecordService  {
    void saveRecord(VisitRecord record);

}
