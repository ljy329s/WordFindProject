package com.jy.lang.wordfindproject.repository;

import com.jy.lang.wordfindproject.domain.SermonPreach;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SermonPreachRepository {

    List<SermonPreach> selectSermonPreach(String code);
}
