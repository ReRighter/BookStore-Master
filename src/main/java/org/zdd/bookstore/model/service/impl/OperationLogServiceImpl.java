package org.zdd.bookstore.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.zdd.bookstore.model.dao.OperationLogMapper;
import org.zdd.bookstore.model.entity.OperationLog;
import org.zdd.bookstore.model.service.OperationLogService;
import org.springframework.stereotype.Service;

/**
* @author Righter
* @description 针对表【operation_log】的数据库操作Service实现
* @createDate 2024-05-12 23:18:52
*/
@Service
public class OperationLogServiceImpl
    implements OperationLogService{
    @Autowired
    private OperationLogMapper mapper;
    public void saveLog(OperationLog log){
        mapper.insert(log);
    }
}




