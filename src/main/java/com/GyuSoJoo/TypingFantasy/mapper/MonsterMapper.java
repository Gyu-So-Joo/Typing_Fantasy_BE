package com.GyuSoJoo.TypingFantasy.mapper;

import com.GyuSoJoo.TypingFantasy.vo.MonsterVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MonsterMapper {
    // 몬스터 전체 조회
    @Select("SELECT * FROM monster")
    List<MonsterVO> findAll();

    // 몬스터 조회
    @Select("SELECT * FROM monster WHERE id = #{id}")
    MonsterVO findById(long id);

    // 몬스터 추가
    @Insert("INSERT INTO monster(name, level, detail, js_code, java_code) VALUES(#{name},#{level},#{detail},#{js_code},#{java_code})")
    int insert(MonsterVO monster);

    // 몬스터 삭제
    @Delete("DELETE FROM monster WHERE id = #{id}")
    int deleteById(long id);

}
