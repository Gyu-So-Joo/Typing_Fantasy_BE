package com.GyuSoJoo.TypingFantasy.service;

import com.GyuSoJoo.TypingFantasy.mapper.MonsterMapper;
import com.GyuSoJoo.TypingFantasy.vo.MonsterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonsterService {
    @Autowired
    MonsterMapper monsterMapper;

    // 몬스터 전체 조회
    public List<MonsterVO> getAllMonsters() {
        return monsterMapper.findAll();
    }

    // 몬스터 조회
    public MonsterVO getMonster(long id) {
        return monsterMapper.findById(id);
    }

    // 몬스터 추가
    public boolean addMonster(MonsterVO monster) {
        return monsterMapper.insert(monster) > 0;
    }
}
