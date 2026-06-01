package com.GyuSoJoo.TypingFantasy.service;

import com.GyuSoJoo.TypingFantasy.mapper.RecordMapper;
import com.GyuSoJoo.TypingFantasy.vo.RecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {
    @Autowired
    RecordMapper recordMapper;

    // 레코드 추가
    public boolean addRecord(RecordVO record) {
        return recordMapper.insert(record) > 0;
    }

    // 레코드 조회 (전체)
    public List<RecordVO> getAllRecords() {
        return recordMapper.findALL();
    }

    // 레코드 조회 (단일)
    public List<RecordVO> getRecordsByUserId(long id) {
        return recordMapper.findByUserId(id);
    }

    // 레코드 조회 10개 (단일)
    public List<RecordVO> getRecordsByUserIdLimit10(long id) {
        return recordMapper.findByUserIdLimit10(id);
    }
}
