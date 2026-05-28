package com.GyuSoJoo.TypingFantasy.mapper;

import com.GyuSoJoo.TypingFantasy.vo.UserVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    // 회원가입
    @Insert("INSERT INTO user(name, password) VALUES (#{name}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(UserVO user);

    // 아이디(name)으로 유저 조회
    // 1. 회원가입 시, 중복 확인
    // 2. 회원 통계
    @Select("SELECT * FROM user WHERE name = #{name}")
    UserVO findByName(String name);

    // 로그인
    @Select("SELECT * FROM user WHERE name = #{name} AND password = #{password}")
    UserVO findUser(String name, String password);
}
