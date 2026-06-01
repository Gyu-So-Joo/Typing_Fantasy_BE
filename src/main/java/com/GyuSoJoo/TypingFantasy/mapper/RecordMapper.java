package com.GyuSoJoo.TypingFantasy.mapper;

import com.GyuSoJoo.TypingFantasy.vo.RecordVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RecordMapper {
    // 레코드 추가 (게임 종료)
    @Insert("INSERT INTO record(user_id, user_name, monster_id, selected_lang, timer, accuracy, cpm, " +
            "special_char_error, case_mismatch_error, normal_text_error, score) " +
            "VALUES(#{userId}, #{userName}, #{monsterId}, #{selectedLang}, #{timer}, #{accuracy}, #{cpm}, " +
            "#{specialCharError}, #{caseMismatchError}, #{normalTextError}, #{score})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RecordVO record);

    // 레코드 조회 (전체)
    @Select("SELECT * FROM record")
    List<RecordVO> findALL();

    // 레코드 조회 (단일)
    @Select("SELECT * FROM record WHERE user_id = #{id}")
    List<RecordVO> findByUserId(long id);
}
