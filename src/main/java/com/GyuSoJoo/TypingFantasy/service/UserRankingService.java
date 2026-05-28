package com.GyuSoJoo.TypingFantasy.service;

import com.GyuSoJoo.TypingFantasy.dto.UserDTO;
import com.GyuSoJoo.TypingFantasy.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRankingService {
    @Autowired
    private UserMapper userMapper;

    // 유저 랭킹 조회
    public List<UserDTO.RankingsResponse> getUserRankingList() {
        return userMapper.findRanking();
    }
}
