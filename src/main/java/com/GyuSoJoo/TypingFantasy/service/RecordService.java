package com.GyuSoJoo.TypingFantasy.service;

import com.GyuSoJoo.TypingFantasy.mapper.RecordMapper;
import com.GyuSoJoo.TypingFantasy.vo.RecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordService {
    @Autowired
    RecordMapper recordMapper;

    // 레코드 추가
    public boolean addRecord(RecordVO record) {
        return recordMapper.insert(record) > 0;
    }
}
