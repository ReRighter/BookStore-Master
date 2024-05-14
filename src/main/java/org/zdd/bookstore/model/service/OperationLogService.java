package org.zdd.bookstore.model.service;

import org.zdd.bookstore.model.entity.OperationLog;


/**
* @author Righter
* @description 针对表【operation_log】的数据库操作Service
* @createDate 2024-05-12 23:18:52
*/
public interface OperationLogService {
    public void saveLog(OperationLog log);
}
