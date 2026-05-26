package com.GyuSoJoo.TypingFantasy.mapper;

import com.GyuSoJoo.TypingFantasy.vo.MonsterVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MonsterMapper {
    // 도감 전체 조회
    @Select("SELECT * FROM monster")
    List<MonsterVO> findAll();
}
