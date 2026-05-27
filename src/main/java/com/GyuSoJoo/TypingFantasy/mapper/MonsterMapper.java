package com.GyuSoJoo.TypingFantasy.mapper;

import com.GyuSoJoo.TypingFantasy.vo.MonsterVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MonsterMapper {
    // 몬스터 전체 조회
    @Select("SELECT * FROM monster")
    List<MonsterVO> findAll();

    // 몬스터 조회 id
    @Select("SELECT * FROM monster WHERE id = #{id}")
    MonsterVO findById(long id);


    // 몬스터 조회 name
    @Select("SELECT * FROM monster WHERE name = #{name}")
    MonsterVO findByName(String name);

    // 몬스터 추가
    @Insert("INSERT INTO monster(name, level, detail, js_code, java_code) VALUES(#{name},#{level},#{detail},#{jsCode},#{javaCode})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(MonsterVO monster);

    // 몬스터 삭제
    @Delete("DELETE FROM monster WHERE id = #{id}")
    int deleteById(long id);

}
