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

    public List<MonsterVO> getAllMonsters() {
        return monsterMapper.findAll();
    }
}
