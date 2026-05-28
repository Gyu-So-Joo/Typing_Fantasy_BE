package com.GyuSoJoo.TypingFantasy.service;

import com.GyuSoJoo.TypingFantasy.mapper.UserMapper;
import com.GyuSoJoo.TypingFantasy.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserStatsService {
    @Autowired
    private UserMapper userMapper;

    // 회원 통계
    public UserVO getUserStats(String name) {
        return userMapper.findByName(name);
    }
}
