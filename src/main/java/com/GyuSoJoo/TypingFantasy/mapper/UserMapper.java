package com.GyuSoJoo.TypingFantasy.mapper;

import com.GyuSoJoo.TypingFantasy.dto.UserDTO;
import com.GyuSoJoo.TypingFantasy.vo.UserVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

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

    // 유저 랭킹 조회
    @Select("SELECT name, total_score FROM user WHERE total_score > 0 ORDER BY total_score DESC, name ASC")
    List<UserDTO.RankingsResponse> findRanking();

    // 유저 언어 변경
    @Update("UPDATE user SET selected_lang = #{selectedLang} WHERE id = #{id}")
    int setSelectedLang(@Param("id") long id, @Param("selectedLang") String selectedLang);

    // 유저 monsterIds 조회
    @Select("SELECT monster_ids FROM user WHERE id = ${id}")
    String findUserMonsterIds(long id);

    // 유저 monsterIds 변경
    @Update("UPDATE user SET monster_ids = #{monsterIds} WHERE id = #{id}")
    int setUserMonsterIds(@Param("id") long id, @Param("monsterIds") String monsterIds);

    // 유저 totalScore 조회
    @Update("SELECT total_score FROM user WHERE id = #{id}")
    long findTotalScoreById(long id);

    // 유저 totalScore 변경
    @Update("UPDATE user SET total_score = #{totalScore} WHERE id = #{id}")
    int setTotalScore(@Param("id") long id, @Param("totalScore") long total_score);
}
