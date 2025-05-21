package com.jy.lang.wordfindproject.repository;

import com.jy.lang.wordfindproject.domain.Word;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WordRepository {

    List<Word> selectWord(String code);
}
