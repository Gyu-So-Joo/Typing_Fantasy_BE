package com.GyuSoJoo.TypingFantasy.service;

import com.GyuSoJoo.TypingFantasy.mapper.UserMapper;
import com.GyuSoJoo.TypingFantasy.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    ObjectMapper objectMapper = new ObjectMapper();

    // 회원가입
    public boolean register(UserVO user) {
        UserVO existing = userMapper.findByName(user.getName());
        if (existing != null) {
            return false;
        }
        return userMapper.insert(user) > 0;
    }

    // 로그인
    public UserVO login(String name, String password) {
        UserVO user = userMapper.findByName(name);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // 선택된 언어 변경
    public boolean setSelectedLang(long id, String selectedLang) {
        return userMapper.setSelectedLang(id, selectedLang) > 0;
    }

    // 몬스터 id 목록 조회
    public List<Integer> findMonsterIdsById(long id) {
        return objectMapper.readValue(userMapper.findUserMonsterIds(id), new TypeReference<List<Integer>>() {});
    }
}
